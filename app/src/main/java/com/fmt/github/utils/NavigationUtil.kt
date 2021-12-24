package com.fmt.github.utils

import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions

object NavigationUtil {

    fun go(pageName: String, arguments: Map<String, Any> = mapOf()) {
        FlutterBoost.instance().open(
            FlutterBoostRouteOptions.Builder()
                .pageName(pageName)
                .arguments(arguments)
                .build()
        )
    }
}