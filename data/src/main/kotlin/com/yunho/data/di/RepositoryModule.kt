package com.yunho.data.di

import com.yunho.data.repository.TestRepositoryImpl
import com.yunho.domain.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindsTestRepository(impl: TestRepositoryImpl): TestRepository
}
