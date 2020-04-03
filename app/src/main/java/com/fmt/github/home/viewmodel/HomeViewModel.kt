package com.fmt.github.home.viewmodel

import androidx.lifecycle.LiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.user.repository.UserRepository

class HomeViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    fun queryReceivedEvents(user: String, page: Int): LiveData<List<ReceivedEventModel>> = emit {
        mUserRepository.queryReceivedEvents(user, page)
    }

    fun deleteAuthorization(id: Int): LiveData<Boolean> = emit {
        mUserRepository.deleteAuthorization(id).code() == 204
    }

    fun deleteUser() {
        launch {
            mUserRepository.deleteLocalUser(mUserRepository.getLocalUsers()[0])
        }
    }
}
