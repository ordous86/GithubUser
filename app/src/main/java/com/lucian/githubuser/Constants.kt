package com.lucian.githubuser

/**
 * Define constants for global access.
 */
interface Constants

const val DATABASE_NAME = "github_user.db"
const val DB_DATA_EXPIRED_TIME = 60 * 60 * 24 * 7 * 1000
const val PAGE_SIZE = 20
const val PAGE_SIZE_MAX = 100
const val SOURCE_URL = "https://api.github.com/"
const val TABLE_NAME = "user_detail"
const val TAG = "GithubUserApp"

enum class QueryState {
    IDLE,
    ERROR,
    RUNNING,
    SUCCESS
}

enum class QueryStrategy {
    CACHE,
    DATABASE,
    ONLINE
}