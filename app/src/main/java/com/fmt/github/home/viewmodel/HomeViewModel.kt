package com.fmt.github.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.ext.yes
import com.fmt.github.user.repository.UserRepository

class HomeViewModel(private val mUserRepository : UserRepository) : BaseViewModel() {

    fun deleteAuthorization(id: Int): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mUserRepository.deleteAuthorization(id)
            (response.code() == 204).yes {
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

    fun deleteUser() {
        launch {
            mUserRepository.deleteLocalUser(mUserRepository.getLocalUsers()[0])
        }
    }
}
