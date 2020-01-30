package com.fmt.github.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.config.Configs
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.model.db.User
import com.fmt.github.user.model.*
import com.fmt.github.user.repository.UserRepository

class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    val mUserInfoModel = MutableLiveData<UserInfoModel>()

    fun createOrGetAuthorization(): MutableLiveData<AuthorizationRespModel> {
        val authorizationReqModel = AuthorizationReqModel(
            Configs.CLIENT_SECRET,
            Configs.SCOPE,
            Configs.NOTE, Configs.NOTE_URL
        )
        val mutableLiveData = MutableLiveData<AuthorizationRespModel>()
        launch {
            mutableLiveData.value =
                mUserRepository.createOrGetAuthorization(
                    authorizationReqModel,
                    Configs.CLIENT_ID,
                    Configs.FINGERPRINT
                )
        }
        return mutableLiveData
    }

    fun getUser(): MutableLiveData<UserModel> {
        val mutableLiveData = MutableLiveData<UserModel>()
        launch {
            mutableLiveData.value = mUserRepository.getUser()
        }
        return mutableLiveData
    }

    fun saveLocalUser(user: User) {
        launch {
            mUserRepository.saveLocalUser(user)
        }
    }

    fun searchUsers(
        query: String,
        sort: String,
        order: String,
        page: Int
    ): MutableLiveData<List<UserModel>> {
        val mutableLiveData = MutableLiveData<List<UserModel>>()
        launch {
            mutableLiveData.value = mUserRepository.searchUsers(query, sort, order, page).items
        }
        return mutableLiveData
    }

    fun getUserInfo(user: String): MutableLiveData<UserInfoModel> {
        launch {
            mUserInfoModel.value = mUserRepository.getUserInfo(user)
        }
        return mUserInfoModel
    }

    fun getUserPublicRepos(user: String, page: Int): MutableLiveData<List<ReposItemModel>> {
        val mutableLiveData = MutableLiveData<List<ReposItemModel>>()
        launch {
            mutableLiveData.value = mUserRepository.getUserPublicRepos(user, page)
        }
        return mutableLiveData
    }

    fun getStarredRepos(user: String, page: Int): MutableLiveData<List<ReposItemModel>> {
        val mutableLiveData = MutableLiveData<List<ReposItemModel>>()
        launch {
            mutableLiveData.value = mUserRepository.getStarredRepos(user, page)
        }
        return mutableLiveData
    }
}
