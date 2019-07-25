package com.fmt.github.data.db

import androidx.room.Room
import com.fmt.github.App

object DBInstance {

    private const val DB_NAME = "open_github_db"

    val mAppDataBase by lazy {
        Room.databaseBuilder(App.mApplication, AppDataBase::class.java, DB_NAME).build()
    }

}