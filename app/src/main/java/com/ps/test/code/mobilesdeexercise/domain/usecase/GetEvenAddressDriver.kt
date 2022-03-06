package com.ps.test.code.mobilesdeexercise.domain.usecase

import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEvenAddressDriver @Inject constructor(
    private val dataRepository: ServerDataRepository
) {
    private val ssValue = 1.5
    private var vowelCount = 0
    private var maxSSValue = 0.0
    private var maxSSValueIndex = 0
    private val regex = Regex("[aeiou]", RegexOption.IGNORE_CASE)

    operator fun invoke(): Flow<String?> = flow {
        val driverList = dataRepository.getDriversData().drivers

        driverList?.forEachIndexed { index, driverName ->
            vowelCount = regex.findAll(driverName, 0).joinToString().split(",").size
            val ssValue = ssValue * vowelCount
            if (ssValue > maxSSValue) {
                maxSSValue = ssValue
                maxSSValueIndex = index
            }
        }

        if (maxSSValue == 0.0) {
            emit(null)
        } else {
            emit(driverList?.get(maxSSValueIndex))
        }
    }
}