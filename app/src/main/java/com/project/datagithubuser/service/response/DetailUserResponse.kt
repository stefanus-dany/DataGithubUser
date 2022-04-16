package com.project.datagithubuser.service.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val repository: String,

    @field:SerializedName("followers")
    val followers: String,

    @field:SerializedName("following")
    val following: String
)