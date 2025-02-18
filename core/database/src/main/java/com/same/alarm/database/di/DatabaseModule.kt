package com.same.alarm.database.di

import android.content.Context
import androidx.room.Room
import com.same.alarm.database.room.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesAlarmDatabase(
        @ApplicationContext context: Context,
    ): AlarmDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                "alarm_database",
            ).build()

    @Singleton
    @Provides
    fun providesAlarmDao(alarmDatabase: AlarmDatabase) = alarmDatabase.alarmDao()
}