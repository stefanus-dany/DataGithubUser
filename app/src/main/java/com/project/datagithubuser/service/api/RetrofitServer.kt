package com.project.datagithubuser.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServer {

    private fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): APIRequestData {
        return getRetrofitClient().create(APIRequestData::class.java)
    }

    companion object {
        private const val baseURL = "https://api.github.com/"
    }

}