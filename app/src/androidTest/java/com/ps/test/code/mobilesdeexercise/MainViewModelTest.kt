package com.ps.test.code.mobilesdeexercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetAddressAssociatedDriver
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriverAssignedAddress
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriversData
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetShipmentsData
import com.ps.test.code.mobilesdeexercise.presentation.main.viewmodels.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Inject
    private lateinit var repository: ServerDataRepository

    @Inject
    private lateinit var driversData: GetDriversData

    @Inject
    private lateinit var shipmentsData: GetShipmentsData

    @Inject
    private lateinit var addressAssociatedDriver: GetAddressAssociatedDriver

    @Inject
    private lateinit var driverAssignedAddress: GetDriverAssignedAddress

    @Inject
    private var driverList: List<String>? = null

    @Before
    fun setup() {
        hiltRule.inject()

        viewModel = MainViewModel(
            shipmentsData,
            driversData,
            addressAssociatedDriver,
            driverAssignedAddress
        )
    }

    @Test
    fun testDriverListData() {
        runBlocking {
            val flow = flow {
                delay(10)
                emit(repository.getDriversData().drivers)
            }

            flow.onEach { list ->
                driverList = list
            }

            assert(driverList?.size!! > 0)
        }
    }
}