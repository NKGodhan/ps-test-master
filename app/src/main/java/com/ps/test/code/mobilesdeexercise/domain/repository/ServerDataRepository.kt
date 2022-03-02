package com.ps.test.code.mobilesdeexercise.domain.repository

import com.ps.test.code.mobilesdeexercise.data.local.dto.DriversDto
import com.ps.test.code.mobilesdeexercise.data.local.dto.ShipmentsDto

interface ServerDataRepository {
    suspend fun getShipmentsData(): ShipmentsDto
    suspend fun getDriversData(): DriversDto
}