package com.ps.test.code.mobilesdeexercise.domain.usecase

import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/***
 * Use-case class to invoke Address data associated with drivers
 * This will be used to show assigned address' to drivers
 * @param dataRepository data repository which gets data out of Data source
 */
class GetDriverAssignedAddress @Inject constructor(
    private val dataRepository: ServerDataRepository
) {
    /**
     * Operator function to invoke assigned address' by driver's list
     */
    operator fun invoke(associatedDriverList: List<String>?): Flow<List<String>?> = flow {
        val driverList = dataRepository.getDriversData().drivers
        val addressList = dataRepository.getShipmentsData().shipments
        val associatedAddressList: ArrayList<String> = arrayListOf()

        driverList?.forEach { driverName ->
            associatedDriverList?.apply {
                associatedAddressList.add(addressList?.get(this.indexOf(driverName)) ?: "")
            }
        }

        emit(associatedAddressList)
    }
}