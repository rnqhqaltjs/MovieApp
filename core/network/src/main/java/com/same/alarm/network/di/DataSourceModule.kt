package com.same.alarm.network.di

import com.same.alarm.data.datasource.AlarmRemoteDataSource
import com.same.alarm.data.datasource.AuthDataSource
import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.network.datasource.AlarmRemoteDataSourceImpl
import com.same.alarm.network.datasource.AuthDataSourceImpl
import com.same.alarm.network.datasource.UserDataSourceImpl
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
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindAlarmRemoteDataSource(alarmRemoteDataSourceImpl: AlarmRemoteDataSourceImpl): AlarmRemoteDataSource

}