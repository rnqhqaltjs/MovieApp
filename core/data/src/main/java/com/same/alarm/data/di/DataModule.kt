package com.same.alarm.data.di

import com.same.alarm.data.repository.AlarmRepositoryImpl
import com.same.alarm.data.repository.AuthRepositoryImpl
import com.same.alarm.data.repository.UserRepositoryImpl
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Singleton
    @Binds
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}

