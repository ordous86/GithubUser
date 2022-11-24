package com.lucian.githubuser

import android.util.Log
import com.lucian.githubuser.UserListRepository.User

/**
 * ViewModel to connect [GithubActivity] and [UserListRepository].
 */
class UserListViewModel: GithubViewModel() {
    // Fields.
    private val repository = UserListRepository()
    val dataSource by lazy {
        UserDataSource(this)
    }

    // Load user list online.
    suspend fun loadUserList(since: Int, pageSize: Int): List<User> {
        // set query state
        queryState.value = QueryState.RUNNING

        // start query
        repository.fetchUserList(since, pageSize).let { response ->
            // check response code
            queryState.value = response.code().let { code ->
                Log.d(TAG, "loadUserList() - Response code = $code")
                if (code == 200)
                    QueryState.SUCCESS
                else
                    QueryState.ERROR
            }

            // check response body
            return response.body() ?: emptyList()
        }
    }
}