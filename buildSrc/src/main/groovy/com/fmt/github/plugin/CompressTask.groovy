package com.fmt.github.plugin

import com.android.build.gradle.BaseExtension
import com.tinify.Tinify
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction
import java.text.DecimalFormat
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 自定义压缩任务
 */
class CompressTask extends DefaultTask {

    CompressExt mCompressOption
    ExecutorService mExecutorService

    CompressTask() {
        mCompressOption = project.compressOption
        mExecutorService = Executors.newCachedThreadPool()
    }

    @TaskAction
    void run() {
        println("CompressTask start ...")
        //1.判断ApiKey是否为空
        if (mCompressOption.apiKey == null) {
            throw new IllegalArgumentException("TinyPng ApiKey should be set")
        }
        //2.校验ApiKey是否合法
        try {
            Tinify.setKey(mCompressOption.apiKey)
            Tinify.validate()
        } catch (Exception ignored) {
            throw new IllegalArgumentException("TinyPng ApiKey is not available")
        }

        //3.加载图片资源所在目录 -->res目录
        def androidExt = project.extensions.findByType(BaseExtension.class)
        def sourceSets = androidExt.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        def resDir = sourceSets.res.srcDirs.first()

        //4.读取已经压缩过的图片集合
        def compressedList = loadCompressedImage()

        //5.过滤res目录下的文件夹 -->只对drawable-、mipmap-做处理
        def drawablePattern = "drawable-[a-z]*"
        def mipmapPattern = "mipmap-[a-z]*"
        def validFiles = new ArrayList<File>()
        resDir.listFiles().each { dir ->
            if (dir.name.matches(drawablePattern) || dir.name.matches(mipmapPattern)) {
                dir.listFiles().each { file ->
                    if (validFile(file, compressedList)) {
                        validFiles.add(file)
                    }
                }
            }
        }
        //6.开启多线程进行图片压缩
        CountDownLatch countDownLatch = new CountDownLatch(validFiles.size())
        validFiles.each { file ->
            mExecutorService.execute(new Runnable() {
                @Override
                void run() {
                    def ret = compressImage(file)
                    compressedList.add(ret)
                    countDownLatch.countDown()
                }
            })
        }
        //7.等待所有的图片都压缩完成，再将压缩后的图片信息写入json文件中
        countDownLatch.await(10, TimeUnit.MINUTES)
        writeCompressResource(compressedList)

        println("CompressionTask finished")
    }

    //将压缩后的图片信息写入json文件中
    def writeCompressResource(List<CompressImageInfo> compressImageList) {
        def compressFile = new File(project.projectDir, "compress-image.json")
        JsonOutput jsonOutput = new JsonOutput()
        def json = jsonOutput.toJson(compressImageList)
        compressFile.write(json, "utf-8")
    }

    //压缩图片
    def compressImage(File file) {
        println("compress ${file.name} start")
        //判断是否需要覆盖原图
        def dstFile
        if (mCompressOption.suffix == null) {
            dstFile = new File(file.parent, file.name)
        } else {
            def fileName = file.name
            def prefix = fileName.substring(0, fileName.lastIndexOf("."))
            def suffix = fileName.substring(fileName.lastIndexOf("."))
            dstFile = new File(file.parent, "$prefix${mCompressOption.suffix}$suffix")
        }

        if (!dstFile.exists()) {
            dstFile.createNewFile()
        }

        //使用Tinify进行压缩
        def source = Tinify.fromFile(file.path)
        source.toFile(dstFile.path)

        println("compress ${file.name} finished")

        return new CompressImageInfo(file.name, file.path, formatSize(file.size()), formatSize(dstFile.size()), DigestUtils.md5Hex(file.newInputStream()))
    }

    //格式化文件的大小
    static def formatSize(long fileSize) {
        def df = new DecimalFormat("#.0")
        if (fileSize < 1024) {
            return "${fileSize}B"
        } else if (fileSize < 1024 * 1024) {
            return "${df.format(fileSize * 1.0f / 1024)}KB"
        } else {
            return "${df.format(fileSize * 1.0f / 1024 / 1024)}M"
        }
    }

    //获取已经压缩过的图片集合
    def loadCompressedImage() {
        def compressFile = new File(project.projectDir, "compress-image.json")
        if (!compressFile.exists()) {
            compressFile.createNewFile()
        } else {
            try {
                def ret = new JsonSlurper().parse(compressFile, "utf-8")
                if (ret instanceof List<CompressImageInfo>) {
                    return ret
                }
            } catch (Exception e) {
                throw e
            }
        }
        return new CopyOnWriteArrayList<CompressImageInfo>()
    }

    //判断图片是否合法
    def validFile(File file, List<CompressImageInfo> compressList) {
        if (file.isDirectory()) {
            return false
        }
        if (file.name.endsWith(".9")) {
            println("skip file ${file.name} which is .9")
            return false
        }
        if (file.name.endsWith(".xml")) {
            println("skip file ${file.name} which is .xml")
            return false
        }
        if (mCompressOption.suffix != null && file.name.contains(mCompressOption.suffix)) {
            println("skip file ${file.name} which has compressed")
            return false
        }
        long fileSize = file.size()
        if (fileSize < mCompressOption.limitSize) {
            println("skip file ${file.name} which file size less than ${mCompressOption.limitSize}")
            return false
        }
        def md5 = DigestUtils.md5Hex(file.newInputStream())
        def match = compressList.any { imageInfo ->
            return imageInfo.md5 == md5
        }
        if (match) {
            println("skip file ${file.name} which has compressed")
            return false
        }
        return true
    }
}