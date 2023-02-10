package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

interface ResultDbRepository {
    suspend fun insertCountry(country: ResultEntity)
    fun getBestResults(): Flow<List<ResultEntity>>
}