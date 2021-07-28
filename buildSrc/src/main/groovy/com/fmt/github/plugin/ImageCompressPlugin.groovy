package com.fmt.github.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义图片压缩插件
 */
class ImageCompressPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //注册扩展属性
        project.extensions.create("compressOption", CompressExt.class)
        //注册Task
        project.tasks.create("imageCompress", CompressTask.class).setGroup("Tiny")
    }
}