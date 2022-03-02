package com.ps.test.code.mobilesdeexercise.domain.usecase

import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDriversData @Inject constructor(private val dataRepository: ServerDataRepository) {
    operator fun invoke(): Flow<List<String>?> = flow {
        emit(dataRepository.getDriversData().drivers)
    }
}