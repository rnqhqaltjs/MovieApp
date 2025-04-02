package com.same.alarm.network.di

import com.same.alarm.data.datasource.LoginDataSource
import com.same.alarm.network.datasource.LoginDataSourceImpl
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
    abstract fun bindLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource

}