package com.lucian.githubuser

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Define table and columns for user detail.
 */
@Entity(tableName = TABLE_NAME, primaryKeys = ["login"])
data class UserDetailEntity (
    @ColumnInfo(name = "avatar_url", typeAffinity = ColumnInfo.TEXT)
    val avatarUrl: String?,

    @ColumnInfo(name = "bio", typeAffinity = ColumnInfo.TEXT)
    val bio: String?,

    @ColumnInfo(name = "blog", typeAffinity = ColumnInfo.TEXT)
    val blog: String?,

    @ColumnInfo(name = "is_admin", typeAffinity = ColumnInfo.INTEGER)
    val isAdmin: Boolean?,

    @ColumnInfo(name = "location", typeAffinity = ColumnInfo.TEXT)
    val location: String?,

    @ColumnInfo(name = "login", typeAffinity = ColumnInfo.TEXT)
    val login: String,

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val name: String?,

    @ColumnInfo(name = "timestamp", typeAffinity = ColumnInfo.TEXT)
    val timestamp: String?,
)