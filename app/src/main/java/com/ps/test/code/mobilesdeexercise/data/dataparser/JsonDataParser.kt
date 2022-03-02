package com.ps.test.code.mobilesdeexercise.data.dataparser

import android.content.Context
import com.google.gson.Gson
import com.ps.test.code.mobilesdeexercise.data.local.dto.DriversDto
import com.ps.test.code.mobilesdeexercise.data.local.dto.ShipmentsDto
import com.ps.test.code.mobilesdeexercise.utils.Utilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonDataParser @Inject constructor(
    private val gson: Gson,
    @ApplicationContext private val context: Context
) {
    fun getShipmentsData(): ShipmentsDto {
        return gson.fromJson(
            Utilities.getJsonFromAssets(context, "server_metadata.json"),
            ShipmentsDto::class.java
        )
    }

    fun getDriversData(): DriversDto {
        return gson.fromJson(
            Utilities.getJsonFromAssets(context, "server_metadata.json"),
            DriversDto::class.java
        )
    }
}