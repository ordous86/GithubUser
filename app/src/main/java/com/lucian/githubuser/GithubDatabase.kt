package com.lucian.githubuser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database for github user information.
 */
@Database(entities = [UserDetailEntity::class], version = 1)
abstract class GithubDatabase: RoomDatabase() {
    // Companion.
    companion object {
        // Fields.
        @Volatile
        private var instance: GithubDatabase? = null

        // Invoke database instance.
        operator fun invoke(context: Context) = instance ?: synchronized(GithubDatabase::class) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        // Build database.
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, GithubDatabase::class.java, DATABASE_NAME).build()
    }

    // Get DAO to perform CRUD for user detail.
    abstract fun getUserDetailDao(): UserDetailDao

    // TODO: Add more DAOs for other data.
}