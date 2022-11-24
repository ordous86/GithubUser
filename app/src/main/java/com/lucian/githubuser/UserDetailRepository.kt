package com.lucian.githubuser

import com.google.gson.annotations.SerializedName

/**
 * Repository of github user detail.
 */
class UserDetailRepository {
    // Define data structure for user detail.
    data class UserDetail(
        @SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("bio")
        val bio: String,

        @SerializedName("blog")
        val blog: String,

        @SerializedName("site_admin")
        val isAdmin: Boolean,

        @SerializedName("location")
        val location: String,

        @SerializedName("login")
        val login: String,

        @SerializedName("name")
        val name: String
    )

    // Called to fetch user detail online.
    suspend fun fetchUserDetail(name: String) =
        WebService.api.fetchUserDetail(name)
}