package com.fmt.github.user.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityPhotoPreviewBinding
import com.fmt.github.ext.startActivity
import com.fmt.github.utils.FileUtils
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_photo_preview.*
import java.io.File
import java.io.FileOutputStream

class PhotoPreviewActivity : BaseDataBindActivity<ActivityPhotoPreviewBinding>() {

    lateinit var mImageUrl: String

    companion object {
        private const val IMAGE_URL = "image_url"

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
        mPhotoView.setOnLongClickListener {
            showBottomDialog()
            false
        }
        mPhotoView.setOnClickListener {
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
        Glide.with(this@PhotoPreviewActivity).asBitmap().load(mImageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    handle(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun share() {
        val file = File(getExternalFilesDir("img_download"), "${IMAGE_URL}.jpg")
        if (!file.exists()) {
            savePic { bitmap ->
                FileOutputStream(file).use { os ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
                }
                sharePic(file)
            }
        } else {
            sharePic(file)
        }
    }

    private fun sharePic(file: File) {
        val picUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this, "${packageName}.provider", file)
        } else {
            Uri.fromFile(file)
        }
        var shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND //设置分享行为
        shareIntent.type = "image/*" //设置分享内容的类型
        shareIntent.putExtra(Intent.EXTRA_STREAM, picUri)
        shareIntent = Intent.createChooser(shareIntent, "")
        startActivity(shareIntent)
    }
}