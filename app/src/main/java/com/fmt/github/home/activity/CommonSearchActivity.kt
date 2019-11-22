package com.fmt.github.home.activity

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityCommonSearchBinding
import com.fmt.github.ext.hideKeyboard
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.warningToast
import com.fmt.github.ext.yes
import com.fmt.github.home.model.SearchModel
import com.fmt.github.repos.fragment.ReposFragment
import com.fmt.github.user.fragment.UserFragment
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_common_search.*

class CommonSearchActivity : BaseDataBindActivity<ActivityCommonSearchBinding>() {

    companion object {
        const val FROM_SEARCH_REPOS = "from_search_repos"
    }

    private var mSearchReposModel = SearchModel()

    private val mReposFragment by lazy { ReposFragment() }

    private val mUsersFragment by lazy { UserFragment() }

    private var mIsSearchRepos = true

    private var mSort: String = ""

    private lateinit var mOrder: String

    override fun getLayoutId(): Int = R.layout.activity_common_search

    override fun initView() {

        mOrder = getString(R.string.desc)

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

        mSortTv.setOnClickListener {
            showPopupView()
        }

        mSearchEt.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(currentFocus)
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
            showSortReposPopupView()
        }.otherwise {
            showSortUsesPopupView()
        }

    }

    private fun showSortReposPopupView() {
        XPopup.Builder(this)
            .asBottomList(
                getString(R.string.sort_options), arrayOf(
                    getString(R.string.best_match), getString(R.string.most_stars),
                    getString(R.string.fewest_stars), getString(R.string.most_forks),
                    getString(R.string.fewest_forks), getString(R.string.recently_updated),
                    getString(R.string.least_recently_updated)
                )
            ) { position, _ ->
                run {
                    when (position) {
                        0 -> {
                            mSort = ""
                            mOrder = getString(R.string.desc)
                        }
                        1 -> {
                            mSort = getString(R.string.stars)
                            mOrder = getString(R.string.desc)
                        }
                        2 -> {
                            mSort = getString(R.string.stars)
                            mOrder = getString(R.string.asc)
                        }

                        3 -> {
                            mSort = getString(R.string.forks)
                            mOrder = getString(R.string.desc)
                        }
                        4 -> {
                            mSort = getString(R.string.forks)
                            mOrder = getString(R.string.asc)
                        }
                        5 -> {
                            mSort = getString(R.string.updated)
                            mOrder = getString(R.string.desc)
                        }
                        6 -> {
                            mSort = getString(R.string.updated)
                            mOrder = getString(R.string.asc)
                        }
                    }
                    searchReposOrUsers()
                }

            }.show()
    }

    private fun showSortUsesPopupView() {
        XPopup.Builder(this)
            .asBottomList(
                getString(R.string.sort_options), arrayOf(
                    getString(R.string.best_match), getString(R.string.most_followers),
                    getString(R.string.fewest_followers), getString(R.string.most_recently_joined),
                    getString(R.string.least_recently_joined), getString(R.string.most_repositories),
                    getString(R.string.fewest_repositories)
                )
            ) { position, _ ->
                run {
                    when (position) {
                        0 -> {
                            mSort = ""
                            mOrder = getString(R.string.desc)
                        }
                        1 -> {
                            mSort = getString(R.string.followers)
                            mOrder = getString(R.string.desc)
                        }
                        2 -> {
                            mSort = getString(R.string.followers)
                            mOrder = getString(R.string.asc)
                        }

                        3 -> {
                            mSort = getString(R.string.joined)
                            mOrder = getString(R.string.desc)
                        }
                        4 -> {
                            mSort = getString(R.string.joined)
                            mOrder = getString(R.string.asc)
                        }
                        5 -> {
                            mSort = getString(R.string.repositories)
                            mOrder = getString(R.string.desc)
                        }
                        6 -> {
                            mSort = getString(R.string.repositories)
                            mOrder = getString(R.string.asc)
                        }
                    }
                    searchReposOrUsers()
                }

            }.show()
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
            this.isNullOrEmpty().yes {
                warningToast(R.string.please_enter_search_keywords)
            }.otherwise {
                mSortTv.visibility = View.VISIBLE
                mIsSearchRepos.yes {
                    mReposFragment.searchReposByKey(this, mSort, mOrder)
                }.otherwise {
                    mUsersFragment.searchUsersByKey(this, mSort, mOrder)
                }
            }
        }
    }
}