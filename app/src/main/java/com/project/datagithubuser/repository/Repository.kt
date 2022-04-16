package com.project.datagithubuser.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.datagithubuser.BuildConfig
import com.project.datagithubuser.database.Favorite
import com.project.datagithubuser.database.FavoriteDao
import com.project.datagithubuser.database.MainDatabase
import com.project.datagithubuser.helper.Helper.TAG
import com.project.datagithubuser.service.api.RetrofitServer
import com.project.datagithubuser.service.response.DetailUserResponse
import com.project.datagithubuser.service.response.SearchUsersResponse
import com.project.datagithubuser.service.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val favDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MainDatabase.getDatabase(application)
        favDao = db.favDao()
    }

    fun getAllFav(): LiveData<List<Favorite>> = favDao.getAllFav()

    fun isFav(username: String): LiveData<Favorite> = favDao.isFav(username)

    fun insert(fav: Favorite) {
        executorService.execute { favDao.insert(fav) }
    }

    fun delete(username: String) {
        executorService.execute { favDao.delete(username) }
    }

    fun getAllUsers(): LiveData<List<UserResponse>> {
        val mutableData = MutableLiveData<List<UserResponse>>()
        executorService.execute {
            val api = RetrofitServer().getInstance()
            api.getAllUsers(BuildConfig.GITHUB_TOKEN2)
                .enqueue(object : Callback<List<UserResponse>> {
                    override fun onResponse(
                        call: Call<List<UserResponse>>,
                        response: Response<List<UserResponse>>
                    ) {
                        if (response.isSuccessful) {
                            mutableData.value = response.body()
                        } else {
                            Log.e(TAG, "onResponse: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                        Log.e(TAG, "onResponse: ${t.message}")
                    }
                })
        }
        return mutableData
    }

    fun getSearchUsers(username: String): LiveData<List<UserResponse>> {
        val mutableData = MutableLiveData<List<UserResponse>>()
        executorService.execute {
            val api = RetrofitServer().getInstance()
            api.getSearchUsers(
                authToken = BuildConfig.GITHUB_TOKEN,
                username = username
            ).enqueue(object : Callback<SearchUsersResponse> {
                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    Log.e(TAG, "onResponse: ${t.message}")
                }
            })
        }
        return mutableData
    }

    fun getDetailUser(username: String): LiveData<DetailUserResponse> {
        val mutableData = MutableLiveData<DetailUserResponse>()
        executorService.execute {
            val api = RetrofitServer().getInstance()
            api.getDetailUser(
                authToken = BuildConfig.GITHUB_TOKEN,
                username = username
            ).enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = response.body()
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onResponse: ${t.message}")
                }

            })
        }
        return mutableData
    }

    fun getFollowing(username: String): LiveData<List<UserResponse>> {
        val mutableData = MutableLiveData<List<UserResponse>>()
        val api = RetrofitServer().getInstance()
        api.getFollowing(
            authToken = BuildConfig.GITHUB_TOKEN,
            username = username
        ).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    mutableData.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e(TAG, "onResponse: ${t.message}")
            }
        })
        return mutableData
    }

    fun getFollowers(username: String): LiveData<List<UserResponse>> {
        val mutableData = MutableLiveData<List<UserResponse>>()
        val api = RetrofitServer().getInstance()
        api.getFollowers(
            authToken = BuildConfig.GITHUB_TOKEN,
            username = username
        ).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    mutableData.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e(TAG, "onResponse: ${t.message}")
            }
        })
        return mutableData
    }
}