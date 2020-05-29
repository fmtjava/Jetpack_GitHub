package com.fmt.github.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.config.Configs
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.model.db.User
import com.fmt.github.user.model.*
import com.fmt.github.user.repository.UserRepository

class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    //提供给xml文件进行绑定
    val mUserInfoModel = MutableLiveData<UserInfoModel>()

    fun createOrGetAuthorization(): LiveData<AuthorizationRespModel> = emit {
        mUserRepository.createOrGetAuthorization(
            AuthorizationReqModel(
                Configs.CLIENT_SECRET,
                Configs.SCOPE,
                Configs.NOTE, Configs.NOTE_URL
            ),
            Configs.CLIENT_ID,
            Configs.FINGERPRINT
        )
    }

    fun getAccessToken(code: String, state: String): LiveData<OauthTokenModel> = emit {
        val url =
            "${Configs.GITHUB_BASE_URL}login/oauth/access_token?code=$code&state=$state&client_id=${Configs.CLIENT_ID}&client_secret=${Configs.CLIENT_SECRET}"
        mUserRepository.getAccessToken(url)
    }

    fun getUser(): LiveData<UserModel> = emit {
        mUserRepository.getUser()
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
    ): LiveData<List<UserModel>> = emit {
        mUserRepository.searchUsers(query, sort, order, page).items
    }

    fun getUserInfo(user: String) {
        launch {
            mUserInfoModel.value = mUserRepository.getUserInfo(user)
        }
    }

    fun getUserPublicRepos(user: String, page: Int): LiveData<List<ReposItemModel>> = emit {
        mUserRepository.getUserPublicRepos(user, page)
    }

    fun getStarredRepos(user: String, page: Int): LiveData<List<ReposItemModel>> = emit {
        mUserRepository.getStarredRepos(user, page)
    }
}
