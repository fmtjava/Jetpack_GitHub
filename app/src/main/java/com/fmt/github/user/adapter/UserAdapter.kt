package com.fmt.github.user.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fmt.github.R
import com.fmt.github.ext.loadUrl
import com.fmt.github.user.model.UserModel

class UserAdapter : BaseQuickAdapter<UserModel, BaseViewHolder>(R.layout.layout_users) {

    override fun convert(helper: BaseViewHolder, item: UserModel) {
        helper.setText(R.id.tv_author,item.login)
        helper.getView<ImageView>(R.id.iv_head).loadUrl(item.avatar_url)
    }
}