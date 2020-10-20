package com.fmt.github.tasks

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.fmt.github.common.image.ImageLoadStrategyManager
import com.fmt.github.common.image.ImageLoader
import com.fmt.launch.starter.task.MainTask

class InitImageLoaderTask : MainTask() {

    override fun run() {
        ImageLoadStrategyManager.init(GlideLoadStrategy())
    }

    class GlideLoadStrategy : ImageLoadStrategyManager.ILoaderStrategy {

        override fun loadImage(options: ImageLoader.LoaderOptions) {

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
    }
}
