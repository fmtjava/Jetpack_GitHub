package com.fmt.github.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.ext.yes
import com.fmt.github.user.repository.UserRepository

class HomeViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    fun deleteAuthorization(id: Int): LiveData<Boolean> = liveData {
        val response = mUserRepository.deleteAuthorization(id)
        (response.code() == 204).yes {
            emit(true)
        }
    }

    fun deleteUser() {
        launch {
            mUserRepository.deleteLocalUser(mUserRepository.getLocalUsers()[0])
        }
    }
}
