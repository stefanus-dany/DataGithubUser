package com.project.datagithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: Favorite)

    @Query("DELETE FROM favorite WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllFav(): LiveData<List<Favorite>>

    @Query("SELECT * from favorite WHERE username = :username")
    fun isFav(username: String): LiveData<Favorite>
}