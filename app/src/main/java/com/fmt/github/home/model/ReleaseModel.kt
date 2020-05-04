package com.fmt.github.home.model

data class ReleaseModel(val tag_name: String, val assets: List<AssetsModel>)

data class AssetsModel(val browser_download_url: String)