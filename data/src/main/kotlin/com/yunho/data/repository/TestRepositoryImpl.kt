package com.yunho.data.repository

import com.yunho.domain.model.Test
import com.yunho.domain.repository.TestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TestRepositoryImpl @Inject constructor() : TestRepository {
    override suspend fun test(): Test {
        return Test(data = "test")
    }
}
