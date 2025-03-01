package com.same.alarm.di

import android.content.Context
import com.same.alarm.alarm.AlarmHelperImpl
import com.same.alarm.data.datasource.AlarmHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAlarmHelper(alarmHelperImpl: AlarmHelperImpl): AlarmHelper
}

@Module
@InstallIn(SingletonComponent::class)
object AppContextModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context
}