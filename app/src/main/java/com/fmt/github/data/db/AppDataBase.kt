package com.fmt.github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fmt.github.user.db.User
import com.fmt.github.user.db.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun getUserDao(): UserDao
}