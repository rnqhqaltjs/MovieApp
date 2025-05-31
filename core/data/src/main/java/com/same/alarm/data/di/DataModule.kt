package com.same.alarm.data.di

import android.app.AlarmManager
import android.content.Context
import com.same.alarm.data.repository.AlarmHelperImpl
import com.same.alarm.data.repository.AlarmRepositoryImpl
import com.same.alarm.data.repository.AuthRepositoryImpl
import com.same.alarm.data.repository.RingPlayerImpl
import com.same.alarm.data.repository.UserRepositoryImpl
import com.same.alarm.data.repository.VibrationPlayerImpl
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.domain.repository.VibrationPlayer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
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

    @Singleton
    @Binds
    abstract fun bindVibrationPlayer(vibrationPlayerImpl: VibrationPlayerImpl): VibrationPlayer
}

