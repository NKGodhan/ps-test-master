package com.ps.test.code.mobilesdeexercise.domain.usecase

import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/***
 * Use-case Class to invoke shipment/address data
 * @param dataRepository data repository which gets data out of Data source
 */
class GetShipmentsData @Inject constructor(private val dataRepository: ServerDataRepository) {
    /**
     * Operator function to invoke list of all shipment address
     */
    operator fun invoke(): Flow<List<String>?> = flow {
        emit(dataRepository.getShipmentsData().shipments)
    }
}