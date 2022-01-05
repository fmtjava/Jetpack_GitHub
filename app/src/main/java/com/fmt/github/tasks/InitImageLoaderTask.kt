package com.fmt.github.tasks

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.fmt.github.AppContext
import com.fmt.github.R
import com.fmt.github.common.image.ImageLoadStrategyManager
import com.fmt.github.common.image.ImageLoaderOptions
import com.rousetime.android_startup.AndroidStartup

class InitImageLoaderTask : AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = false

    override fun create(context: Context): Unit = ImageLoadStrategyManager.init(GlideLoadStrategy())

    /**
     * Glide加载图片策略
     */
    class GlideLoadStrategy : ImageLoadStrategyManager.ILoaderStrategy {

        override fun loadImage(options: ImageLoaderOptions) {

            var requestOptions = RequestOptions()
            if (options.placeholderId != null) {
                requestOptions = requestOptions.placeholder(options.placeholderId!!)
            }

            options.targetView?.let { view ->
                if (view is ImageView) {
                    Glide.with(view.context).load(options.url).apply(requestOptions).into(view)
                } else {
                    Glide.with(view.context).load(options.url).apply(requestOptions).into(object :
                        CustomViewTarget<View, Drawable>(view) {

                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            view.background = resource
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                        }

                        override fun onResourceCleared(placeholder: Drawable?) {
                        }
                    })
                }
            }
        }

        override fun getImageBitMap(
            options: ImageLoaderOptions,
            callBack: ImageLoadStrategyManager.ILoadBitmapCallBack
        ) {
            require(options.context != null) {
                AppContext.getString(R.string.context_not_null_tip)
            }

            require(options.url != null) {
                AppContext.getString(R.string.url_not_null_tips)
            }

            Glide.with(options.context!!).asBitmap().load(options.url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        callBack.loaderBitmapSuccess(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

}
