package com.fmt.github.repos.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.repos.model.ReposListModel
import com.fmt.github.repos.repository.ReposRepository

class ReposViewModel : BaseViewModel() {

    private val mReposRepository by lazy { ReposRepository() }

    fun searchRepos(query: String, page: Int): MutableLiveData<ReposListModel> {
        val mutableLiveData = MutableLiveData<ReposListModel>()
        launch {
            mutableLiveData.value = mReposRepository.searchRepos(query, page)
        }
        return mutableLiveData
    }

    fun checkRepoStarred(owner: String, repo: String): MutableLiveData<Boolean> {
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

    fun starRepo(owner: String, repo: String): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mReposRepository.starRepo(owner, repo)
            if (response.code() == 204) {
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

    fun unStarRepo(owner: String, repo: String): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mReposRepository.unStarRepo(owner, repo)
            if (response.code() == 204) {
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

}