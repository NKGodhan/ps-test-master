package com.ps.test.code.mobilesdeexercise.presentation.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriversData
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetEvenAddressDriver
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetOddAddressDriver
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetShipmentsData
import com.ps.test.code.mobilesdeexercise.utils.NumberState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shipmentsData: GetShipmentsData,
    private val driversData: GetDriversData,
    private val evenAddressDriver: GetEvenAddressDriver,
    private val oddAddressDriver: GetOddAddressDriver
) : ViewModel() {
    private var shipmentLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private val _shipmentLiveData: LiveData<List<String>> = shipmentLiveData

    private var driverLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private val _driverLiveData: LiveData<List<String>> = driverLiveData

    private var assignedDriverLiveData: MutableLiveData<String> = MutableLiveData()
    val _assignedDriverLiveData: LiveData<String> = assignedDriverLiveData

    fun getShipmentData(): LiveData<List<String>> {
        shipmentsData.invoke().onEach { data ->
            shipmentLiveData.postValue(data)
        }.launchIn(viewModelScope)

        return _shipmentLiveData
    }

    fun getDriverData(): LiveData<List<String>> {
        driversData.invoke().onEach { data ->
            driverLiveData.postValue(data)
        }.launchIn(viewModelScope)

        return _driverLiveData
    }

    fun getAddressDriverAssigned(lengthState: Int) {
        when (lengthState) {
            NumberState.EVEN.state -> {
                getEvenAddressDriver()
            }

            NumberState.ODD.state -> {
                getOddAddressDriver()
            }
        }
    }

    private fun getEvenAddressDriver() {
        evenAddressDriver.invoke().onEach { driverName ->
            assignedDriverLiveData.postValue(driverName)
        }.launchIn(viewModelScope)
    }

    private fun getOddAddressDriver() {
        oddAddressDriver.invoke().onEach { driverName ->
            assignedDriverLiveData.postValue(driverName)
        }.launchIn(viewModelScope)
    }
}