package com.yunho.data.di

import android.content.Context
import com.yunho.data.database.TestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun providesTestDatabase(@ApplicationContext context: Context): TestDatabase {
        return TestDatabase(context)
    }

    @Singleton
    @Provides
    fun providesTestDao(database: TestDatabase) = database.getDao()
}
