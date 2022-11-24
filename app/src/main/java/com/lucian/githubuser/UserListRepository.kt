package com.lucian.githubuser

import com.google.gson.annotations.SerializedName

/**
 * Repository of github user list.
 */
class UserListRepository {
    // Define data structure for user list item.
    data class User(
        @SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("id")
        val id: String,

        @SerializedName("site_admin")
        val isAdmin: Boolean,

        @SerializedName("login")
        val login: String
    )

    // Called to fetch user list online.
    suspend fun fetchUserList(since: Int, pageSize: Int) =
        WebService.api.fetchUserList(since, pageSize)
}