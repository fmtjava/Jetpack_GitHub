package com.fmt.github.home.activity

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityCommonSearchBinding
import com.fmt.github.ext.hideKeyboard
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.warningToast
import com.fmt.github.ext.yes
import com.fmt.github.repos.fragment.ReposFragment
import com.fmt.github.home.model.SearchModel
import com.fmt.github.user.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_common_search.*

class CommonSearchActivity : BaseDataBindActivity<ActivityCommonSearchBinding>() {

    companion object {
        const val FROM_SEARCH_REPOS = "from_search_repos"
    }

    var mSearchReposModel = SearchModel()

    private val mReposFragment by lazy { ReposFragment() }

    private val mUsersFragment by lazy { UserFragment() }

    var mIsSearchRepos = true

    override fun getLayoutId(): Int = R.layout.activity_common_search

    override fun initView() {

        mIsSearchRepos = intent.getBooleanExtra(FROM_SEARCH_REPOS, true).apply {
            this.yes {
                setContentFragment(mReposFragment)
                mSearchEt.hint = getString(R.string.search_repos)
            }.otherwise {
                setContentFragment(mUsersFragment)
                mSearchEt.hint = getString(R.string.search_users)
            }
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

    private fun setContentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

    override fun initData() {
        mDataBind.searchModel = mSearchReposModel
    }

    private fun searchRepos() {
        mSearchReposModel.searchKey.get()?.apply {
            this.isNullOrEmpty().yes {
                warningToast(R.string.please_enter_search_keywords)
            }.otherwise {
                mIsSearchRepos.yes {
                    mReposFragment.searchReposByKey(this)
                }.otherwise {
                    mUsersFragment.searchUsersByKey(this)
                }
            }
        }
    }
}