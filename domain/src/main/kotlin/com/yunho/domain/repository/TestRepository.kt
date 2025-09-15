package com.yunho.domain.repository

import com.yunho.domain.model.Test

interface TestRepository {
    suspend fun test(): Test
}
