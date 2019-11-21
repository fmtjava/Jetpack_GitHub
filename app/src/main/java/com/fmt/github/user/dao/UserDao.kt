package com.fmt.github.user.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fmt.github.user.model.db.User

@Dao
interface UserDao {

    @Query("select * from user")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insertAll(vararg user: User)

    @Delete
    suspend fun deleteAll(vararg user: User)

}