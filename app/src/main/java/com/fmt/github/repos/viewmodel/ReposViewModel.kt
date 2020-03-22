package com.fmt.github.repos.viewmodel

import androidx.lifecycle.LiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.repos.repository.ReposRepository

class ReposViewModel(private val mReposRepository: ReposRepository) : BaseViewModel() {

    fun searchRepos(
        query: String,
        sort: String,
        order: String,
        page: Int
    ): LiveData<List<ReposItemModel>> = emit {
        mReposRepository.searchRepos(query, sort, order, page).items
    }

    fun checkRepoStarred(owner: String, repo: String): LiveData<Boolean> = emit {
        mReposRepository.checkRepoStarred(owner, repo).code() == 204
    }

    fun starRepo(owner: String, repo: String): LiveData<Boolean> = emit {
        mReposRepository.starRepo(owner, repo).code() == 204
    }

    fun unStarRepo(owner: String, repo: String): LiveData<Boolean> = emit {
        mReposRepository.unStarRepo(owner, repo).code() == 204
    }
}
