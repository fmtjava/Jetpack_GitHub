package com.fmt.github.plugin

class CompressImageInfo{
    String name
    String path
    String fileSize
    String compressedSize
    String md5

    CompressImageInfo(String name, String path, String fileSize, String compressedSize, String md5) {
        this.name = name
        this.path = path
        this.fileSize = fileSize
        this.compressedSize = compressedSize
        this.md5 = md5
    }
}