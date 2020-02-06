package com.fmt.github.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.config.Configs
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.model.db.User
import com.fmt.github.user.model.*
import com.fmt.github.user.repository.UserRepository

class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    val mUserInfoModel = MutableLiveData<UserInfoModel>()

    fun createOrGetAuthorization(): LiveData<AuthorizationRespModel> {
        val authorizationReqModel = AuthorizationReqModel(
            Configs.CLIENT_SECRET,
            Configs.SCOPE,
            Configs.NOTE, Configs.NOTE_URL
        )
        return liveData {
            emit(
                mUserRepository.createOrGetAuthorization(
                    authorizationReqModel,
                    Configs.CLIENT_ID,
                    Configs.FINGERPRINT
                )
            )
        }
    }

    fun getUser(): LiveData<UserModel> = liveData {
        emit(mUserRepository.getUser())
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
    ): LiveData<List<UserModel>> = liveData {
        emit(mUserRepository.searchUsers(query, sort, order, page).items)
    }

    fun getUserInfo(user: String): MutableLiveData<UserInfoModel> {
        launch {
            mUserInfoModel.value = mUserRepository.getUserInfo(user)
        }
        return mUserInfoModel
    }

    fun getUserPublicRepos(user: String, page: Int): LiveData<List<ReposItemModel>> = liveData {
        emit(mUserRepository.getUserPublicRepos(user, page))
    }

    fun getStarredRepos(user: String, page: Int): LiveData<List<ReposItemModel>> = liveData {
        emit(mUserRepository.getStarredRepos(user, page))
    }
}
