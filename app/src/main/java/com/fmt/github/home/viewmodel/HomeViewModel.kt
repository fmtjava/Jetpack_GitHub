package com.fmt.github.home.viewmodel

import androidx.lifecycle.LiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.home.model.ReleaseModel
import com.fmt.github.user.repository.UserRepository

class HomeViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    fun deleteUser() {
        launch {
            mUserRepository.deleteLocalUser(mUserRepository.getLocalUsers()[0])
        }
    }

    fun getReleases(): LiveData<ReleaseModel> = emit {
        mUserRepository.getReleases()[0]
    }
}
