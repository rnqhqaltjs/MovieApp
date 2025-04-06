package com.same.alarm.network.di

import com.same.alarm.network.remote.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {
    @Provides
    @Singleton
    fun providePhotoService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)
}