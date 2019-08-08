package com.fmt.github.data.db

import androidx.room.Room
import com.fmt.github.AppContext
import com.fmt.github.user.db.UserDao

private const val DB_NAME = "open_github_db"

private val mAppDataBase by lazy {
    Room.databaseBuilder(AppContext, AppDataBase::class.java, DB_NAME).build()
}

object UserDaoImpl : UserDao by mAppDataBase.getUserDao()

