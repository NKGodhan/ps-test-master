package com.ps.test.code.mobilesdeexercise.presentation.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.test.code.mobilesdeexercise.data.local.dto.DriversDto
import com.ps.test.code.mobilesdeexercise.data.local.dto.ShipmentsDto
import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val serverDataRepository: ServerDataRepository) :
    ViewModel() {
    private var shipmentLiveData: MutableLiveData<ShipmentsDto> = MutableLiveData()
    private val _shipmentLiveData: LiveData<ShipmentsDto> = shipmentLiveData
    private var driverLiveData: MutableLiveData<DriversDto> = MutableLiveData()
    private val _driverLiveData: LiveData<DriversDto> = driverLiveData

    fun getShipmentData(): LiveData<ShipmentsDto> {
        viewModelScope.launch(Dispatchers.IO) {
            serverDataRepository.getShipmentsData().let {
                shipmentLiveData.postValue(it)
            }
        }

        return _shipmentLiveData
    }

    fun getDriverData(): LiveData<DriversDto> {
        viewModelScope.launch(Dispatchers.IO) {
            serverDataRepository.getDriversData().let {
                driverLiveData.postValue(it)
            }
        }

        return _driverLiveData
    }
}