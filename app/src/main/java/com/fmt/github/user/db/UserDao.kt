package com.fmt.github.user.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertAll(vararg user: User)

    @Query("select * from user")
    suspend fun getAll(): List<User>

    @Delete
    suspend fun deleteAll(vararg user: User)

}