package com.fmt.github.user.repository

import com.fmt.github.data.db.UserDaoImpl
import com.fmt.github.data.http.UserService
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
        UserService.createOrGetAuthorization(authorizationReqModel, client_id, fingerprint)

    suspend fun deleteAuthorization(id: Int) = UserService.deleteAuthorization(id)

    suspend fun getUser(): UserModel = UserService.getUser()

    suspend fun searchUsers(query: String, page: Int): UserListModel =
        UserService.searchUsers(query, page)

    suspend fun getUserInfo(user: String): UserInfoModel =
        UserService.getUserInfo(user)

    suspend fun getUserPublicRepos(user: String, page: Int): List<ReposItemModel> =
        UserService.getUserPublicRepos(user, page)

    suspend fun getStarredRepos(user: String, page: Int): List<ReposItemModel> =
        UserService.getStarredRepos(user, page)

    suspend fun saveLocalUser(user: User) = UserDaoImpl.insertAll(user)

    suspend fun getLocalUsers(): List<User> = UserDaoImpl.getAll()

    suspend fun deleteLocalUser(user: User) = UserDaoImpl.deleteAll(user)

}