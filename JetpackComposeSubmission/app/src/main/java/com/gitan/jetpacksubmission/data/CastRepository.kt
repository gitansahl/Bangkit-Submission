package com.gitan.jetpacksubmission.data

import com.gitan.jetpacksubmission.model.Cast
import com.gitan.jetpacksubmission.model.CastData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow

class CastRepository {
    fun getAllCasts(): Flow<List<Cast>> {
        return flowOf(CastData.casts)
    }

    fun getCastById(castId: Long): Cast {
        return CastData.casts.first {
            it.id == castId
        }
    }

    companion object {
        @Volatile
        private var instance: CastRepository? = null

        fun getInstance(): CastRepository =
            instance ?: synchronized(this) {
                CastRepository().apply {
                    instance = this
                }
            }
    }
}