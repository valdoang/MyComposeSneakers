package com.dicoding.mycomposesneakers.di

import com.dicoding.mycomposesneakers.data.SneakersRepository

object Injection {
    fun provideRepository(): SneakersRepository {
        return SneakersRepository.getInstance()
    }
}