package com.lucian.githubuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * Factory to build [GithubViewModel].
 */
@Suppress("UNCHECKED_CAST")
class GithubViewModelFactory: ViewModelProvider.Factory {
    // Create view model.
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        // user list view model
        modelClass.isAssignableFrom(UserListViewModel::class.java) -> UserListViewModel() as T

        // user detail view model
        modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> UserDetailViewModel() as T

        // otherwise
        else -> throw IllegalArgumentException("Unknown ViewModel")
    }
}