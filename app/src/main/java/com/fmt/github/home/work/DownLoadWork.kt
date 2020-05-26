package com.fmt.github.home.work

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.work.*
import com.fmt.github.AppContext
import com.fmt.github.R
import com.fmt.github.data.http.DownloadService
import com.fmt.github.ext.errorToast
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * WorkManager下载Apk进行版本更新，注意：Github中Releases的Apk下载有点太慢了，经常会报超时异常
 */
class DownLoadWork(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private var CHANNEL_ID: String = "down_apk"
    private lateinit var mBuilder: NotificationCompat.Builder

    private val mNotificationManager: NotificationManager by lazy {
        applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
    }

    companion object {
        private const val DOWN_LOAD_URL = "down_load_url"
        const val DOWN_LOAD_DIR = "apk_download"
        const val DOWN_LOAD_NAME = "app-release.apk"
        const val CHANNEL_NAME = "版本更新"
        const val PACKAGE_ARCHIVE = "application/vnd.android.package-archive"
        const val NOTIFY_ID = 1
        const val INIT_VALUE = 0
        const val MAX_PROGRESS = 100

        fun startDownLoadWork(context: Context, downLoadUrl: String) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()
            val data = Data.Builder()
                .putString(DOWN_LOAD_URL, downLoadUrl)
                .build()
            val request = OneTimeWorkRequest.Builder(DownLoadWork::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
            WorkManager.getInstance(context)
                .beginUniqueWork(DOWN_LOAD_DIR, ExistingWorkPolicy.REPLACE, request)
                .enqueue()
        }
    }

    override suspend fun doWork(): Result =
        (TextUtils.isEmpty(inputData.getString(DOWN_LOAD_URL))).yes {
            Result.failure()
        }.otherwise {
            try {
                initNotification()
                val responseBody =
                    DownloadService.download(inputData.getString(DOWN_LOAD_URL).toString())
                startDown(responseBody.byteStream(), responseBody.contentLength())
                Result.success()
            } catch (e: Exception) {
                mNotificationManager.cancel(NOTIFY_ID)
                withContext(Dispatchers.Main) {
                    errorToast(e.message.toString())
                }
                Result.failure()
            }
        }

    private fun initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//Android O以上显示通知需要通知渠道
            createNotificationChannel()
        } else {
            CHANNEL_ID = ""
        }

        mBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(applicationContext.getString(R.string.downloading))
            .setSmallIcon(R.mipmap.ic_github)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)//防止通知进度更新时总是不停的响
            .setProgress(MAX_PROGRESS, INIT_VALUE, false)

        mNotificationManager.notify(NOTIFY_ID, mBuilder.build())
    }

    //适配Android8.0(通知渠道NotificationChannel)
    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        mNotificationManager.createNotificationChannel(channel)
    }

    private fun startDown(inputStream: InputStream, totalLength: Long) {
        val downLoadDir = AppContext.getExternalFilesDir(DOWN_LOAD_DIR)//Android Q文件存储机制推荐使用沙盒模式
        val downLoadFile = File(downLoadDir, DOWN_LOAD_NAME)
        val outputStream = FileOutputStream(downLoadFile)

        var currentLength: Long = INIT_VALUE.toLong()

        val byteData = ByteArray(1024)
        var len: Int
        while (true) {
            len = inputStream.read(byteData)
            if (len == -1)
                break
            currentLength += len
            val progress = (MAX_PROGRESS * currentLength / totalLength)//计算下载进度
            mBuilder.setProgress(MAX_PROGRESS, progress.toInt(), false)//更新通知进度
            mNotificationManager.notify(NOTIFY_ID, mBuilder.build())//重新进行通知
            outputStream.write(byteData, INIT_VALUE, len)//写入对应的下载文件中
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
        mNotificationManager.cancel(NOTIFY_ID)
        installApk(downLoadFile)
    }

    //适配Android7.0以上安装Apk
    private fun installApk(apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW).also { it.flags = Intent.FLAG_ACTIVITY_NEW_TASK }

        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N).yes {
            // 参数2 清单文件中provider节点里面的authorities
            // 参数3  共享的文件,即apk包的file类
            val apkUri = FileProvider.getUriForFile(
                applicationContext,
                applicationContext.applicationInfo.packageName + ".provider", apkFile
            )
            //对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, PACKAGE_ARCHIVE)
        }.otherwise {
            intent.setDataAndType(
                Uri.fromFile(apkFile),
                PACKAGE_ARCHIVE
            )
        }.also {
            applicationContext.startActivity(it)
        }
    }

}