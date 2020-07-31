package com.fmt.github.user.activity

import android.app.Activity
import android.os.Bundle
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityPhotoPreviewBinding
import com.fmt.github.ext.startActivity
import kotlinx.android.synthetic.main.activity_photo_preview.*

class PhotoPreviewActivity : BaseDataBindActivity<ActivityPhotoPreviewBinding>() {

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
        mDataBind.url = intent.getStringExtra(IMAGE_URL)
        mPhotoView.setOnClickListener {
            finish()
        }
    }
}