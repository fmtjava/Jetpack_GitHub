package com.fmt.github.user.activity

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.common.image.ImageLoadStrategyManager
import com.fmt.github.common.image.ImageLoaderOptions
import com.fmt.github.databinding.ActivityPhotoPreviewBinding
import com.fmt.github.ext.startActivity
import com.fmt.github.utils.FileUtils
import com.fmt.github.utils.ShareUtils
import com.lxj.xpopup.XPopup
import java.io.File
import java.io.FileOutputStream

/**
 * 图片预览
 */
class PhotoPreviewActivity : BaseDataBindActivity<ActivityPhotoPreviewBinding>() {

    private lateinit var mImageUrl: String

    companion object {
        private const val IMAGE_URL = "image_url"
        const val IMG_DOWNLOAD_DIR = "img_download"

        fun go2PhotoPreviewActivity(activity: Activity, url: String) {
            Bundle().run {
                putString(IMAGE_URL, url)
                activity.startActivity<PhotoPreviewActivity>(this)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_photo_preview

    override fun initView() {
        mImageUrl = intent.getStringExtra(IMAGE_URL)
        mDataBind.url = mImageUrl
        mDataBind.mPhotoView.setOnLongClickListener {
            showBottomDialog()
            false
        }
        mDataBind.mPhotoView.setOnClickListener {
            finish()
        }
    }

    private fun showBottomDialog() {
        XPopup.Builder(this)
            .asBottomList(
                getString(R.string.options), arrayOf(
                    getString(R.string.share_picture),
                    getString(R.string.save_picture)
                )
            ) { position, _ ->
                run {
                    when (position) {
                        0 -> {
                            share()
                        }
                        1 -> {
                            savePic {
                                FileUtils.saveFile(it)
                            }
                        }
                    }
                }

            }.show()
    }

    private fun savePic(handle: (Bitmap) -> Unit) {
        ImageLoaderOptions().setContext(this).setImageUrl(mImageUrl)
            .getImageBitmap(object : ImageLoadStrategyManager.ILoadBitmapCallBack{
                override fun loaderBitmapSuccess(bitmap: Bitmap) {
                    handle(bitmap)
                }
            })
    }

    private fun share() {
        val file = File(getExternalFilesDir(IMG_DOWNLOAD_DIR), "${IMAGE_URL}.jpg")
        if (!file.exists()) {
            savePic { bitmap ->
                FileOutputStream(file).use { os ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
                }
                ShareUtils.sharePic(this, file)
            }
        } else {
            ShareUtils.sharePic(this, file)
        }
    }
}