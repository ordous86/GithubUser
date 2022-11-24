package com.lucian.githubuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel to connect [GithubActivity] and [UserDetailRepository].
 */
class UserDetailViewModel: GithubViewModel() {
    // Fields.
    private val repository = UserDetailRepository()
    private val userDetailMap = HashMap<String, UserDetailEntity>()

    // Fields for data binding.
    val queryStrategy = MutableLiveData<QueryStrategy>()
    val userDetail = MutableLiveData<UserDetailEntity>()

    // Load user detail from cache.
    fun loadCache(login: String) {
        userDetailMap[login]?.also {
            val cacheTime = it.timestamp?.toLong() ?: 0
            if (System.currentTimeMillis() - cacheTime > DB_DATA_EXPIRED_TIME) {
                Log.w(TAG, "loadCache() - Cache is expired")
                queryStrategy.value = QueryStrategy.ONLINE
            } else {
                Log.d(TAG, "loadCache() - Found cache")
                queryState.value = QueryState.SUCCESS
                userDetail.value = it
            }
        } ?: also {
            Log.w(TAG, "loadCache() - Cache not found, query from DB")
            queryStrategy.value = QueryStrategy.DATABASE
        }
    }

    // Load user detail from database.
    fun loadDatabase(login: String, db: GithubDatabase) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            db.getUserDetailDao().getByLogin(login)?.also {
                val cacheTime = it.timestamp?.toLong() ?: 0
                if (System.currentTimeMillis() - cacheTime > DB_DATA_EXPIRED_TIME) {
                    Log.w(TAG, "loadDatabase() - DB data is expired")
                    queryStrategy.postValue(QueryStrategy.ONLINE)
                } else {
                    Log.d(TAG, "loadDatabase() - Found DB data")
                    queryState.postValue(QueryState.SUCCESS)
                    userDetail.postValue(it)
                    withContext(Dispatchers.Main + coroutineExceptionHandler) {
                        userDetailMap[login] = it
                    }
                }
            } ?: also {
                Log.w(TAG, "loadDatabase() - DB data not found, query online")
                queryStrategy.postValue(QueryStrategy.ONLINE)
            }
        }
    }

    // Load user detail online.
    fun loadOnline(login: String, db: GithubDatabase) {
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            repository.fetchUserDetail(login).also { response ->
                // check response code
                queryState.value = response.code().let { code ->
                    Log.d(TAG, "loadOnline() - Response code = $code")
                    if (code == 200)
                        QueryState.SUCCESS
                    else
                        QueryState.ERROR
                }

                // check response body
                response.body()?.also { body ->
                    UserDetailEntity(
                        body.avatarUrl,
                        body.bio,
                        body.blog,
                        body.isAdmin,
                        body.location,
                        login,
                        body.name,
                        System.currentTimeMillis().toString()).also { entity ->
                        userDetail.value = entity
                        userDetailMap[login] = entity
                        withContext(Dispatchers.IO + coroutineExceptionHandler) {
                            db.getUserDetailDao().insert(entity)
                        }
                    }
                }
            }
        }
    }
}