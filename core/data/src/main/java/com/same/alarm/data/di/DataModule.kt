package com.same.alarm.data.di

import android.content.Context
import com.same.alarm.data.repository.AlarmHelperImpl
import com.same.alarm.data.repository.AlarmRepositoryImpl
import com.same.alarm.data.repository.AuthRepositoryImpl
import com.same.alarm.data.repository.RingPlayerImpl
import com.same.alarm.data.repository.UserRepositoryImpl
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Binds
    abstract fun bindAlarmHelper(alarmHelperImpl: AlarmHelperImpl): AlarmHelper

    @Singleton
    @Binds
    abstract fun bindRingPlayer(ringPlayerImpl: RingPlayerImpl): RingPlayer
}

@Module
@InstallIn(SingletonComponent::class)
object AppContextModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context
}