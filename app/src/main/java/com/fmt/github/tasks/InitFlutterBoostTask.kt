package com.fmt.github.tasks

import android.content.Context
import android.content.Intent
import com.fmt.github.config.Configs
import com.fmt.github.mApplication
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.user.activity.UserInfoActivity
import com.rousetime.android_startup.AndroidStartup
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostDelegate
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import com.idlefish.flutterboost.containers.FlutterBoostActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine

class InitFlutterBoostTask : AndroidStartup<Unit>() {

    companion object {
        const val USER_INFO_PAGE = "userInfoPage";
        const val REPOS_DETAIL_PAGE = "reposDetailPage";
    }

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = true

    override fun create(context: Context) {
        //统一处理Flutter与Native的跳转逻辑
        FlutterBoost.instance().setup(mApplication, object : FlutterBoostDelegate {
            override fun pushNativeRoute(options: FlutterBoostRouteOptions) {
                if (REPOS_DETAIL_PAGE == options.pageName()) {
                    val intent = Intent(
                        FlutterBoost.instance().currentActivity(),
                        ReposDetailActivity::class.java
                    ).apply {
                        putExtra(
                            ReposDetailActivity.WEB_URL,
                            "${Configs.GITHUB_BASE_URL}${options.arguments()[ReposDetailActivity.WEB_URL]}"
                        )
                        putExtra(
                            ReposDetailActivity.REPO,
                            options.arguments()[ReposDetailActivity.REPO].toString()
                        )
                        putExtra(
                            ReposDetailActivity.OWNER,
                            options.arguments()[ReposDetailActivity.OWNER].toString()
                        )
                    }
                    FlutterBoost.instance().currentActivity()
                        .startActivityForResult(intent, options.requestCode())
                } else if (USER_INFO_PAGE == options.pageName()) {
                    val intent = Intent(
                        FlutterBoost.instance().currentActivity(),
                        UserInfoActivity::class.java
                    ).apply {
                        putExtra(
                            UserInfoActivity.USER_NAME,
                            options.arguments()[UserInfoActivity.USER_NAME].toString()
                        )
                        putExtra(
                            UserInfoActivity.USER_AVATAR,
                            options.arguments()[UserInfoActivity.USER_AVATAR].toString()
                        )
                    }
                    FlutterBoost.instance().currentActivity()
                        .startActivityForResult(intent, options.requestCode())
                }
            }

            override fun pushFlutterRoute(options: FlutterBoostRouteOptions) {
                val intent: Intent =
                    FlutterBoostActivity.CachedEngineIntentBuilder(FlutterBoostActivity::class.java)
                        .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                        .destroyEngineWithActivity(false)
                        .uniqueId(options.uniqueId())
                        .url(options.pageName())
                        .urlParams(options.arguments())
                        .build(FlutterBoost.instance().currentActivity())
                FlutterBoost.instance().currentActivity().startActivity(intent)
            }
        }) { engine: FlutterEngine ->

        }
    }
}