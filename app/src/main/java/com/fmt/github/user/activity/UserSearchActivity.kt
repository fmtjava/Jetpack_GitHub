package com.fmt.github.user.activity

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.fmt.github.R
import com.fmt.github.base.activity.BaseActivity
import com.fmt.github.ext.hideKeyboard
import com.fmt.github.user.fragment.UserFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_repos_search.*

class UserSearchActivity : BaseActivity() {

    private val mUsersFragment by lazy { UserFragment() }

    override fun getLayoutId(): Int = R.layout.activity_user_search

    override fun initView() {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.frameLayout, mUsersFragment)
            commit()
        }

        iv_back.setOnClickListener {
            finish()
        }

        mSearchEt.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(currentFocus)
                searchUser()
                true
            } else {
                false
            }
        }
    }

    private fun searchUser() {
        val searchKey = mSearchEt.text.toString().trim()
        if (searchKey.isNullOrEmpty()) {
            Toasty.warning(this, R.string.please_enter_search_keywords, Toast.LENGTH_SHORT, true).show();
            return
        }
        mUsersFragment.searchUsersByKey(searchKey)
    }


}