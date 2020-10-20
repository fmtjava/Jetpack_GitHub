package com.fmt.github.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.fmt.github.AppContext
import com.fmt.github.R
import com.fmt.github.ext.successToast
import java.io.*

object FileUtils {

    /**
     * 保存图片到相册，适配Android Q以上
     */
    fun saveFile(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val fileName = "${System.currentTimeMillis()}.jpg"
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/JPEG")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/")
            val contentResolver: ContentResolver = AppContext.contentResolver
            val uri: Uri? =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                contentResolver.openOutputStream(it).use { os ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
                    successToast(R.string.save_picture_success)
                }
            }
        } else {
            val picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val file = File(picDir, "${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { os ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
            }
            if (file.exists()) {
                // 发送广播，通知刷新图库显示
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val uri = Uri.fromFile(file)
                intent.data = uri
                AppContext.sendBroadcast(intent)
                successToast(R.string.save_picture_success)
            }
        }
    }
}