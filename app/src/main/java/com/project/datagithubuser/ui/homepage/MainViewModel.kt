package com.project.datagithubuser.ui.homepage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.datagithubuser.repository.Repository
import com.project.datagithubuser.service.response.UserResponse

class MainViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    fun getAllUsers(): LiveData<List<UserResponse>> = repository.getAllUsers()

    fun getSearchUsers(username: String): LiveData<List<UserResponse>> =
        repository.getSearchUsers(username = username)
}