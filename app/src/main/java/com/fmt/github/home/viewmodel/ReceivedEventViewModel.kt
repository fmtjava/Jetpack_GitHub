package com.fmt.github.home.viewmodel

import com.fmt.github.base.viewmodel.BaseLPagingViewModel
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.user.repository.UserRepository

class ReceivedEventViewModel(private val mUserRepository: UserRepository) :
    BaseLPagingViewModel<ReceivedEventModel>() {

    var user = ""

    override suspend fun getDataList(page: Int): List<ReceivedEventModel> =
        mUserRepository.queryReceivedEvents(user, page)
}