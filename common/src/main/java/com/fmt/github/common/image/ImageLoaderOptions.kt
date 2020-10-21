package com.fmt.github.common.image

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull

/**
 * 图片加载参数
 */
class ImageLoaderOptions {

    var context: Context? = null
    var url: String? = null
    var placeholderId: Int? = null
    var targetView: View? = null
    //其它属性可请自行拓展

    fun setImageUrl(@NonNull url: String): ImageLoaderOptions {
        this.url = url
        return this
    }

    fun setPlaceholderId(@NonNull @DrawableRes placeholderId: Int): ImageLoaderOptions {
        this.placeholderId = placeholderId
        return this
    }

    fun setTargetView(@NonNull targetView: View): ImageLoaderOptions {
        this.targetView = targetView
        return this
    }

    fun setContext(@NonNull context: Context): ImageLoaderOptions {
        this.context = context
        return this
    }

    /**
     * 加载图片
     */
    fun load() {
        checkIsInitImageLoadStrategy()
        ImageLoadStrategyManager.loaderStrategy!!.loadImage(this)
    }

    /**
     * 获取BiMap
     */
    fun getImageBitmap(callback: ImageLoadStrategyManager.ILoadBitmapCallBack) {
        checkIsInitImageLoadStrategy()
        ImageLoadStrategyManager.loaderStrategy!!.getImageBitMap(this, callback)
    }

    private fun checkIsInitImageLoadStrategy(){
        check(ImageLoadStrategyManager.loaderStrategy != null) {
            "You must priority init loaderStrategy field in ImageLoadStrategyManager"
        }
    }
}

