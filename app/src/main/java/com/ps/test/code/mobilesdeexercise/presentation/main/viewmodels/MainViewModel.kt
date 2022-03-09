package com.ps.test.code.mobilesdeexercise.presentation.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetAddressAssociatedDriver
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriverAssignedAddress
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriversData
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetShipmentsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shipmentsData: GetShipmentsData,
    private val driversData: GetDriversData,
    private val addressAssociatedDriver: GetAddressAssociatedDriver,
    private val driverAssignedAddress: GetDriverAssignedAddress
) : ViewModel() {
    private var shipmentLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private val _shipmentLiveData: LiveData<List<String>> = shipmentLiveData

    private var driverLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private val _driverLiveData: LiveData<List<String>> = driverLiveData

    private var associatedAddressLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val _associatedAddressLiveData: LiveData<List<String>> = associatedAddressLiveData

    private var associatedDriversLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private val _associatedDriversLiveData: LiveData<List<String>> = associatedDriversLiveData

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

    fun getAddressAssociatedDriversList(): LiveData<List<String>> {
        addressAssociatedDriver.invoke().onEach { driversList ->
            associatedDriversLiveData.postValue(driversList)
        }.launchIn(viewModelScope)

        return _associatedDriversLiveData
    }

    fun subscribeAddressObserver() {
        getDriverAssociatedAddressList()
    }

    private fun getDriverAssociatedAddressList() {
        driverAssignedAddress.invoke(_associatedDriversLiveData.value).onEach { addressList ->
            associatedAddressLiveData.postValue(addressList)
        }.launchIn(viewModelScope)
    }
}