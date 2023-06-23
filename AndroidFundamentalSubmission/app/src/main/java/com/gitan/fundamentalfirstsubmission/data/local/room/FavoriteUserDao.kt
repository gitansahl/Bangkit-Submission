package com.gitan.fundamentalfirstsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gitan.fundamentalfirstsubmission.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Insert
    suspend fun insertFavoriteUser(user: FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :id)")
    fun isUserFavorited(id: String): LiveData<Boolean>

    @Query("DELETE FROM favorite_user WHERE username = :id")
    suspend fun deleteFavoriteUser(id: String)

    @Update
    suspend fun updateFavoriteUser(favoriteUser: FavoriteUser)
}