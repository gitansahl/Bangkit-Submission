package com.gitan.jetpacksubmission.di

import com.gitan.jetpacksubmission.data.CastRepository

object Injection {
    fun provideRepository(): CastRepository {
        return CastRepository.getInstance()
    }
}