package com.lucian.githubuser

import androidx.room.*

/**
 * Define CRUD for user detail data.
 */
@Dao
interface UserDetailDao {
    @Delete
    fun delete(item: UserDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: UserDetailEntity): Long

    @Insert
    fun insertAll(item: List<UserDetailEntity>)

    @Query("SELECT * FROM $TABLE_NAME WHERE login LIKE :login")
    fun getByLogin(login: String): UserDetailEntity?

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<UserDetailEntity>

    @Update
    fun update(item: UserDetailEntity)
}