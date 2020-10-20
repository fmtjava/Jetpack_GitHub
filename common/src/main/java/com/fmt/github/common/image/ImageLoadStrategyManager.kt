package com.fmt.github.common.image

/**
 * 图片加载管理类
 */
object ImageLoadStrategyManager {

    var loaderStrategy: ILoaderStrategy? = null

    fun init(loaderStrategy: ILoaderStrategy) {
        this.loaderStrategy = loaderStrategy
    }

    //图片加载策略接口，用于隔离第三方图片加载库
    interface ILoaderStrategy {
        fun loadImage(options: ImageLoader.LoaderOptions)
    }

}