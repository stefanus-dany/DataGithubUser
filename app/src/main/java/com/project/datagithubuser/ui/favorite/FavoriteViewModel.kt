package com.project.datagithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.datagithubuser.database.Favorite
import com.project.datagithubuser.repository.Repository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    fun getAllFav(): LiveData<List<Favorite>> = repository.getAllFav()

}