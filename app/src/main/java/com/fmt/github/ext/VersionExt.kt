package com.fmt.github.ext

import com.fmt.github.AppContext

fun getVersionName(): String {
    return AppContext.packageManager.getPackageInfo(AppContext.packageName, 0).versionName
}