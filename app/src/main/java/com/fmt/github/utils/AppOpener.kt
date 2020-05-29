package com.fmt.github.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import com.fmt.github.R
import com.fmt.github.ext.warningToast
import java.util.*

object AppOpener {

    fun openInBrowser(context:Context,url:String){
        val uri = Uri.parse(url)
        var intent: Intent? = Intent(Intent.ACTION_VIEW, uri).addCategory(Intent.CATEGORY_BROWSABLE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent = createActivityChooserIntent(context, intent, uri, VIEW_IGNORE_PACKAGE)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            warningToast(R.string.no_browser_clients)
        }
    }

    private fun createActivityChooserIntent(
        context: Context, intent: Intent,
        uri: Uri, ignorPackageList: List<String>?
    ): Intent? {
        val pm = context.packageManager
        val activities = pm.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        val chooserIntents = ArrayList<Intent>()
        val ourPackageName = context.packageName

        Collections.sort(activities, ResolveInfo.DisplayNameComparator(pm))

        for (resInfo in activities) {
            val info = resInfo.activityInfo
            if (!info.enabled || !info.exported) {
                continue
            }
            if (info.packageName == ourPackageName) {
                continue
            }
            if (ignorPackageList != null && ignorPackageList.contains(info.packageName)) {
                continue
            }

            val targetIntent = Intent(intent)
            targetIntent.setPackage(info.packageName)
            targetIntent.setDataAndType(uri, intent.type)
            chooserIntents.add(targetIntent)
        }

        if (chooserIntents.isEmpty()) {
            return null
        }

        val lastIntent = chooserIntents.removeAt(chooserIntents.size - 1)
        if (chooserIntents.isEmpty()) {
            // there was only one, no need to showImage the chooser
            return lastIntent
        }

        val chooserIntent = Intent.createChooser(lastIntent, null)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            chooserIntents.toTypedArray()
        )
        return chooserIntent
    }

    private val VIEW_IGNORE_PACKAGE = listOf(
        "com.gh4a", "com.fastaccess", "com.taobao.taobao"
    )

}