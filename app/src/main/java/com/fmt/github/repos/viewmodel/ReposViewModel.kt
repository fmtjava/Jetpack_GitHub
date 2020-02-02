package com.fmt.github.repos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    ): LiveData<List<ReposItemModel>> {
        val mutableLiveData = MutableLiveData<List<ReposItemModel>>()
        launch {
            mutableLiveData.value = mReposRepository.searchRepos(query, sort, order, page).items
        }
        return mutableLiveData
    }

    fun checkRepoStarred(owner: String, repo: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mReposRepository.checkRepoStarred(owner, repo)
            if (response.code() == 204) {
                mutableLiveData.value = true
            } else if (response.code() == 404) {
                mutableLiveData.value = false
            }
        }
        return mutableLiveData
    }

    fun starRepo(owner: String, repo: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mReposRepository.starRepo(owner, repo)
            (response.code() == 204).yes {
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

    fun unStarRepo(owner: String, repo: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mReposRepository.unStarRepo(owner, repo)
            (response.code() == 204).yes {
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

}
