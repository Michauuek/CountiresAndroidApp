package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

class ResultDbRepositoryImpl(
    private val dao: ResultDao
): ResultDbRepository {

    override suspend fun insertCountry(result: ResultEntity) {
        dao.insertResult(result)
    }

    override fun getBestResults(): Flow<List<ResultEntity>> {
        return dao.getBestResults()
    }
}