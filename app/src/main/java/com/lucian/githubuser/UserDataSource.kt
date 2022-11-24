package com.lucian.githubuser

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lucian.githubuser.UserListRepository.User

/**
 * Data source for github user.
 */
class UserDataSource(private val viewModel: UserListViewModel): PagingSource<Int, User>() {
    // Fields.
    private var dataCount = 0

    override fun getRefreshKey(state: PagingState<Int, User>) = state.anchorPosition?.let {
        state.closestPageToPosition(it)?.data?.last()?.id?.toInt()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        // check data count
        if (dataCount >= PAGE_SIZE_MAX) {
            Log.w(TAG, "load() - Data count is up to limit")
            return LoadResult.Page(emptyList(), null, null)
        }

        // start query
        val since = params.key ?: 0
        val userList = viewModel.loadUserList(since, PAGE_SIZE)

        // check data count
        dataCount += userList.size

        // check next key
        val nextKey = if (userList.isEmpty())
            null
        else
            userList.last().id.toInt()

        // complete
        Log.d(TAG, "load() - User list size = ${userList.size}, next key = $nextKey")
        return LoadResult.Page(userList, null, nextKey)
    }
}