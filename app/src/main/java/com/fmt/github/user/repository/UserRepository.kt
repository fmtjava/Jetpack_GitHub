package com.fmt.github.user.repository

import com.fmt.github.data.db.DBInstance
import com.fmt.github.data.http.RetrofitClient
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.db.User
import com.fmt.github.user.model.AuthorizationReqModel
import com.fmt.github.user.model.UserInfoModel
import com.fmt.github.user.model.UserListModel
import com.fmt.github.user.model.UserModel

class UserRepository {

    suspend fun createOrGetAuthorization(
        authorizationReqModel: AuthorizationReqModel,
        client_id: String, fingerprint: String
    ) =
        RetrofitClient.mUserService.createOrGetAuthorization(authorizationReqModel, client_id, fingerprint)

    suspend fun deleteAuthorization(id: Int) = RetrofitClient.mUserService.deleteAuthorization(id)

    suspend fun getUser(): UserModel = RetrofitClient.mUserService.getUser()

    suspend fun searchUsers(query: String, page: Int): UserListModel =
        RetrofitClient.mUserService.searchUsers(query, page)

    suspend fun getUserInfo(user: String): UserInfoModel =
        RetrofitClient.mUserService.getUserInfo(user)

    suspend fun getUserPublicRepos(user: String, page: Int): List<ReposItemModel> =
        RetrofitClient.mUserService.getUserPublicRepos(user, page)

    suspend fun getStarredRepos(user: String, page: Int): List<ReposItemModel> =
        RetrofitClient.mUserService.getStarredRepos(user, page)

    suspend fun saveLocalUser(user: User) = DBInstance.mAppDataBase.getUserDao().insertAll(user)

    suspend fun getLocalUsers(): List<User> = DBInstance.mAppDataBase.getUserDao().getAll()

    suspend fun deleteLocalUser(user: User) = DBInstance.mAppDataBase.getUserDao().deleteAll(user)

}