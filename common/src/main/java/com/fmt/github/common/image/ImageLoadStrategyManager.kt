package com.fmt.github.common.image

import android.graphics.Bitmap

/**
 * 图片加载管理类
 */
object ImageLoadStrategyManager {

    var loaderStrategy: ILoaderStrategy? = null

    /**
     * 初始化图片加载策略
     */
    fun init(loaderStrategy: ILoaderStrategy) {
        this.loaderStrategy = loaderStrategy
    }

    //图片加载策略接口，用于隔离第三方图片加载库
    interface ILoaderStrategy {
        //加载图片到指定的View
        fun loadImage(options: ImageLoaderOptions)

        //获取Bitmap
        fun getImageBitMap(options: ImageLoaderOptions, callBack: ILoadBitmapCallBack)
    }

    interface ILoadBitmapCallBack {
        fun loaderBitmapSuccess(bitmap: Bitmap)
    }
}