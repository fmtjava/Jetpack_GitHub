package com.fmt.github.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.user.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val mUserRepository by lazy { UserRepository() }

    fun deleteAuthorization(id: Int): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        launch {
            val response = mUserRepository.deleteAuthorization(id)
            if (response.code() == 204) {
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