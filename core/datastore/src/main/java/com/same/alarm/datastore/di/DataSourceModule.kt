package com.same.alarm.datastore.di

import com.same.alarm.data.datasource.TokenStorage
import com.same.alarm.datastore.TokenStorageImpl
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
    abstract fun bindLoginDataStore(tokenStorageImpl: TokenStorageImpl): TokenStorage

}