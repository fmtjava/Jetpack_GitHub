package com.fmt.github.home.activity

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityCommonSearchBinding
import com.fmt.github.ext.*
import com.fmt.github.home.model.SearchModel
import com.fmt.github.repos.fragment.ReposFragment
import com.fmt.github.user.fragment.UserFragment

class CommonSearchActivity : BaseDataBindActivity<ActivityCommonSearchBinding>() {

    companion object {
        const val FROM_SEARCH_REPOS = "from_search_repos"
    }

    private var mIsSearchRepos = true//类型自动推导，无需写声明类型即:Boolean

    private val mSearchReposModel by lazy { SearchModel() }

    private val mReposFragment by lazy { ReposFragment() }
    private val mUsersFragment by lazy { UserFragment() }

    private var mSort = ""
    private lateinit var mOrder: String

    override fun getLayoutId(): Int = R.layout.activity_common_search

    override fun initView() {

        mOrder = getString(R.string.desc)

        mIsSearchRepos = intent.getBooleanExtra(FROM_SEARCH_REPOS, true).apply {
            this.yes {
                setContentFragment(mReposFragment)
                mDataBind.mSearchEt.hint = getString(R.string.search_repos)
            }.otherwise {
                setContentFragment(mUsersFragment)
                mDataBind.mSearchEt.hint = getString(R.string.search_users)
            }
        }

        mDataBind.ivBack.setOnClickListener {
            finish()
        }

        mDataBind.mSortTv.setOnClickListener {
            showPopupView()
        }

        mDataBind.mSearchEt.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                currentFocus?.let { hideKeyboard(it) }
                mSort = ""
                mOrder = getString(R.string.desc)
                searchReposOrUsers()
                true
            } else {
                false
            }
        }
    }

    private fun showPopupView() {
        mIsSearchRepos.yes {
            createSortReposPopup(this).observe(this, {
                searchReposOrUsersBySortOption(it)
            })
        }.otherwise {
            createSortUsesPopup(this).observe(this, {
                searchReposOrUsersBySortOption(it)
            })
        }
    }

    private fun searchReposOrUsersBySortOption(sortOption: SortOption) {
        mSort = sortOption.sort
        mOrder = sortOption.order
        searchReposOrUsers()
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

    private fun searchReposOrUsers() {
        mSearchReposModel.searchKey.get()?.apply {
            this.isEmpty().no {
                mDataBind.mSortTv.visibility = View.VISIBLE
                mIsSearchRepos.yes {
                    mReposFragment.searchReposByKey(this, mSort, mOrder)
                }.otherwise {
                    mUsersFragment.searchUsersByKey(this, mSort, mOrder)
                }
            }.otherwise {
                warningToast(R.string.please_enter_search_keywords)
            }
        }
    }
}