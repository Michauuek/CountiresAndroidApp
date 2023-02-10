package com.michau.countries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Insert
    suspend fun insertResult(result: ResultEntity)
    @Query("SELECT * FROM resultentity ORDER BY points LIMIT 5")
    fun getBestResults(): Flow<List<ResultEntity>>
}