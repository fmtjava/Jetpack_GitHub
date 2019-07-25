package com.fmt.github.repos.activity

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.fmt.github.R
import com.fmt.github.base.activity.BaseActivity
import com.fmt.github.ext.hideKeyboard
import com.fmt.github.repos.fragment.ReposFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_repos_search.*

class ReposSearchActivity : BaseActivity() {

    private val mReposFragment by lazy { ReposFragment() }

    override fun getLayoutId(): Int = R.layout.activity_repos_search

    override fun initView() {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.frameLayout, mReposFragment)
            commit()
        }

        iv_back.setOnClickListener {
            finish()
        }

        mSearchEt.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(currentFocus)
                searchRepos()
                true
            } else {
                false
            }
        }
    }

    private fun searchRepos() {
        val searchKey = mSearchEt.text.toString().trim()
        if (searchKey.isNullOrEmpty()) {
            Toasty.warning(this, R.string.please_enter_search_keywords, Toast.LENGTH_SHORT, true).show();
            return
        }
        mReposFragment.searchReposByKey(searchKey)
    }
}