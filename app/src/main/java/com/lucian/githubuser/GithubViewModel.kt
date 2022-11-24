package com.lucian.githubuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class GithubViewModel: ViewModel() {
    // Fields.
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler() - An exception occurs: ", throwable.fillInStackTrace())
        queryState.value = QueryState.ERROR
    }

    // Fields for data binding.
    val queryState = MutableLiveData<QueryState>().apply { value = QueryState.IDLE }
}