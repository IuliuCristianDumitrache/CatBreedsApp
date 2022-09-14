package com.example.cat.data

import android.content.Context
import androidx.room.Room

object DatabaseHelper {
    lateinit var database: AppDatabase

    private const val DB_NAME = "CAT_DB"

    fun initDB(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DB_NAME
        )
            .build()
    }
}