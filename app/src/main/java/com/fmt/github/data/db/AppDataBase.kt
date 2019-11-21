package com.fmt.github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fmt.github.user.model.db.User
import com.fmt.github.user.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun getUserDao(): UserDao
}