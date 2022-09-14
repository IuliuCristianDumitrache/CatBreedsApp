package com.example.cat.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [(CatEntity::class)],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        //Migrations
    }
}