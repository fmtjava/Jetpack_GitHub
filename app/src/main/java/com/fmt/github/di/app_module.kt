package com.fmt.github.di

import com.fmt.github.data.db.userDao
import com.fmt.github.data.http.*
import com.fmt.github.home.viewmodel.HomeViewModel
import com.fmt.github.home.viewmodel.ReceivedEventViewModel
import com.fmt.github.repos.api.ReposApi
import com.fmt.github.repos.repository.ReposRepository
import com.fmt.github.repos.viewmodel.ReposViewModel
import com.fmt.github.user.api.UserApi
import com.fmt.github.user.repository.UserRepository
import com.fmt.github.user.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ReceivedEventViewModel(get()) }
    viewModel { ReposViewModel(get()) }
    viewModel { UserViewModel(get()) }
}

val reposModule = module {
    //factory 每次注入时都重新创建一个新的对象
    factory { ReposRepository(get()) }
    factory { UserRepository(get(), get()) }
}

val remoteModule = module {
    //single 单列注入
    single<ReposApi> { ReposService }

    single<UserApi> { UserService }
}

val localModule = module {
    single { userDao }
}

val appModule = listOf(viewModelModule, reposModule, remoteModule, localModule)