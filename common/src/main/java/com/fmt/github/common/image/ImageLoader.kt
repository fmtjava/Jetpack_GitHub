package com.fmt.github.common.image

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import java.lang.IllegalStateException

/**
 * 图片加载器
 */
class ImageLoader(private val options: LoaderOptions) {

    fun load() {
        if (ImageLoadStrategyManager.loaderStrategy == null) {
            throw IllegalStateException("You must priority init loaderStrategy field in ImageLoadStrategyManager")
        }
        ImageLoadStrategyManager.loaderStrategy!!.loadImage(options)
    }

    //图片加载参数
    class LoaderOptions {

        var url: String? = null
        var placeholderId: Int? = null
        var targetView: View? = null
        //其它属性可请自行拓展

        fun setImageUrl(@NonNull url: String): LoaderOptions {
            this.url = url
            return this
        }

        fun setPlaceholderId(@NonNull @DrawableRes placeholderId: Int): LoaderOptions {
            this.placeholderId = placeholderId
            return this
        }

        fun setTargetView(@NonNull targetView: View): LoaderOptions {
            this.targetView = targetView
            return this
        }

        fun build(): ImageLoader {
            return ImageLoader(this)
        }
    }

}