package com.ps.test.code.mobilesdeexercise.data.repository

import com.ps.test.code.mobilesdeexercise.data.dataparser.JsonDataParser
import com.ps.test.code.mobilesdeexercise.data.local.dto.DriversDto
import com.ps.test.code.mobilesdeexercise.data.local.dto.ShipmentsDto
import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import javax.inject.Inject

class ServerDataRepositoryImp @Inject constructor(private val jsonDataParser: JsonDataParser) :
    ServerDataRepository {
    override suspend fun getShipmentsData(): ShipmentsDto {
        return jsonDataParser.getShipmentsData()
    }

    override suspend fun getDriversData(): DriversDto {
        return jsonDataParser.getDriversData()
    }
}