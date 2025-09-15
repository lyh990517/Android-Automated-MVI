package com.yunho.domain.usecase

import com.yunho.domain.repository.TestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke() = runCatching {
        testRepository.test()
    }
}
