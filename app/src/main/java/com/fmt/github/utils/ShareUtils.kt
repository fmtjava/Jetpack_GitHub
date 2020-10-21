package com.fmt.github.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.fmt.github.R
import com.fmt.github.ext.successToast
import java.io.File

object ShareUtils {

    /**
     * 分享图片
     */
    fun sharePic(context: Context, file: File) {
        val picUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } else {
            Uri.fromFile(file)
        }
        var shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND //设置分享行为
        shareIntent.type = "image/*" //设置分享内容的类型
        shareIntent.putExtra(Intent.EXTRA_STREAM, picUri)
        shareIntent = Intent.createChooser(shareIntent, "")
        context.startActivity(shareIntent)
    }

    /**
     * 分享文本
     */
    fun shareText(context: Context, text: String) {
        var shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND //设置分享行为
        shareIntent.type = "text/plain" //设置分享内容的类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent = Intent.createChooser(shareIntent, "")
        context.startActivity(shareIntent)
    }

    /**
     * 在浏览器中打开网络链接
     */
    fun openUrl(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    /**
     * 复制链接
     */
    fun copyClipboard(context: Context, text: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", text)
        cm.setPrimaryClip(clipData)
        successToast(R.string.copied_to_pasteboard)
    }

}