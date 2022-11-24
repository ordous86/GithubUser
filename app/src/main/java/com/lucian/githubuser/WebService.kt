package com.lucian.githubuser

import com.lucian.githubuser.UserListRepository.User
import com.lucian.githubuser.UserDetailRepository.UserDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object WebService {
    // Fields.
    val api: WebApi by lazy {
        Retrofit.Builder()
            .baseUrl(SOURCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebApi::class.java)
    }

    // Define interface for web APIs.
    interface WebApi {
        @GET("users")
        suspend fun fetchUserList(@Query("since") since: Int, @Query("per_page") pageSize: Int): Response<List<User>>

        @GET("/users/{username}")
        suspend fun fetchUserDetail(@Path("username") name: String): Response<UserDetail>
    }
}