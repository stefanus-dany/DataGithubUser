package com.project.datagithubuser.service.response

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>
)
