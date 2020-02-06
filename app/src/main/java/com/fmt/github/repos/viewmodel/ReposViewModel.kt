package com.fmt.github.repos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.ext.yes
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.repos.repository.ReposRepository

class ReposViewModel(private val mReposRepository: ReposRepository) : BaseViewModel() {

    fun searchRepos(
        query: String,
        sort: String,
        order: String,
        page: Int
    ): LiveData<List<ReposItemModel>> = liveData {
        emit(mReposRepository.searchRepos(query, sort, order, page).items)//挂起函数
    }

    fun checkRepoStarred(owner: String, repo: String): LiveData<Boolean> = liveData {
        val response = mReposRepository.checkRepoStarred(owner, repo)
        if (response.code() == 204) {
            emit(true)
        } else if (response.code() == 404) {
            emit(false)
        }
    }

    fun starRepo(owner: String, repo: String): LiveData<Boolean> = liveData {
        val response = mReposRepository.starRepo(owner, repo)
        (response.code() == 204).yes {
            emit(true)
        }
    }

    fun unStarRepo(owner: String, repo: String): LiveData<Boolean> = liveData {
        val response = mReposRepository.unStarRepo(owner, repo)
        (response.code() == 204).yes {
            emit(true)
        }
    }

}
