package com.ps.test.code.mobilesdeexercise.domain.usecase

import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import com.ps.test.code.mobilesdeexercise.utils.getFactors
import com.ps.test.code.mobilesdeexercise.utils.isLengthEvenOrOdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/***
 * Use-case class to invoke Driver data associated with address'
 * This will be used to show drivers data associated with address'
 * @param dataRepository data repository which gets data out of Data source
 */
class GetAddressAssociatedDriver @Inject constructor(
    private val dataRepository: ServerDataRepository
) {
    private val evenRegex = Regex("[aeiou]", RegexOption.IGNORE_CASE)
    private val oddRegex = Regex("[^aeiou]", RegexOption.IGNORE_CASE)
    private val associatedDriverList: ArrayList<String> = arrayListOf()

    /**
     * Operator function to invoke driver data by Suitability Score (SS value); including length factor logic
     */
    operator fun invoke(): Flow<List<String>?> = flow {
        val addressList = dataRepository.getShipmentsData().shipments
        val driverList = dataRepository.getDriversData().drivers as MutableList

        addressList?.forEach { address ->
            val addressLengthFactorList = address.length.getFactors()
            var ssValue: Double
            var letterCount: Int
            var maxSSValue = 0.0
            var maxSSValueIndex = 0
            var reg: Regex

            address.isLengthEvenOrOdd().let { isEven ->
                ssValue = if (isEven) 1.5 else 1.0
                reg = if (isEven) evenRegex else oddRegex
            }

            driverList.forEachIndexed { index, driverName ->
                val driverLengthFactorList = driverName.length.getFactors()
                addressLengthFactorList.retainAll(driverLengthFactorList)
                val hasCommonFactor = addressLengthFactorList.size > 0

                letterCount = reg.findAll(driverName, 0).joinToString().split(",").size

                var ssVal = ssValue * letterCount
                if (hasCommonFactor) {
                    ssVal += (ssVal * 0.5)
                }
                if (ssVal > maxSSValue) {
                    maxSSValue = ssVal
                    maxSSValueIndex = index
                }
            }

            if (maxSSValue > 0.0) {
                associatedDriverList.add(driverList[maxSSValueIndex])
                driverList.removeAt(maxSSValueIndex)
            }
        }

        emit(associatedDriverList)
    }
}