package com.fmt.github.data.db

import androidx.room.Room
import com.fmt.github.App
import com.fmt.github.AppContext

object DBInstance {

    private const val DB_NAME = "open_github_db"

    val mAppDataBase by lazy {
        Room.databaseBuilder(AppContext, AppDataBase::class.java, DB_NAME).build()
    }

}