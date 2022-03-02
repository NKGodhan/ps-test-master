package com.ps.test.code.mobilesdeexercise.di.repository

import android.content.Context
import com.google.gson.Gson
import com.ps.test.code.mobilesdeexercise.data.dataparser.JsonDataParser
import com.ps.test.code.mobilesdeexercise.data.repository.ServerDataRepositoryImp
import com.ps.test.code.mobilesdeexercise.domain.repository.ServerDataRepository
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetDriversData
import com.ps.test.code.mobilesdeexercise.domain.usecase.GetShipmentsData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerDataRepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepository(jsonDataParser: JsonDataParser): ServerDataRepository {
        return ServerDataRepositoryImp(jsonDataParser)
    }

    @Singleton
    @Provides
    fun provideJsonDataParser(gson: Gson, @ApplicationContext context: Context): JsonDataParser {
        return JsonDataParser(gson, context)
    }

    @Singleton
    @Provides
    fun provideGsonObject(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideGetShipmentsData(repository: ServerDataRepository): GetShipmentsData {
        return GetShipmentsData(repository)
    }

    @Singleton
    @Provides
    fun provideGetDriversData(repository: ServerDataRepository): GetDriversData {
        return GetDriversData(repository)
    }
}
