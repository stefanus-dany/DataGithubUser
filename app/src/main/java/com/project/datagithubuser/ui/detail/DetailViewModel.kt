package com.project.datagithubuser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.datagithubuser.database.Favorite
import com.project.datagithubuser.repository.Repository
import com.project.datagithubuser.service.response.DetailUserResponse
import com.project.datagithubuser.service.response.UserResponse

class DetailViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    fun getDetailUser(username: String): LiveData<DetailUserResponse> =
        repository.getDetailUser(username = username)

    fun getFollowing(username: String): LiveData<List<UserResponse>> =
        repository.getFollowing(username = username)

    fun getFollowers(username: String): LiveData<List<UserResponse>> =
        repository.getFollowers(username = username)

    fun insertFav(fav: Favorite) {
        repository.insert(fav)
    }

    fun deleteFav(username: String) {
        repository.delete(username)
    }

    fun isFav(username: String): LiveData<Favorite> =
        repository.isFav(username)
}