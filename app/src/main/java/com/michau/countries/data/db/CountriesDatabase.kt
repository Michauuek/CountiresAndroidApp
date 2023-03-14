package com.michau.countries.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ResultEntity::class, CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CountriesDatabase: RoomDatabase() {
    abstract val dao: ResultDao
    abstract val countryDao: CountryDao
}
