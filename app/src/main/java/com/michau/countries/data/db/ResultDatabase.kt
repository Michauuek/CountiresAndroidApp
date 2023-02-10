package com.michau.countries.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ResultDatabase: RoomDatabase() {
    abstract val dao: ResultDao
}
