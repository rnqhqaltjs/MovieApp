package com.same.alarm.database.di

import com.same.alarm.data.datasource.AlarmLocalDataSource
import com.same.alarm.database.datasource.AlarmLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAlarmDataSource(alarmDataSourceImpl: AlarmLocalDataSourceImpl): AlarmLocalDataSource

}