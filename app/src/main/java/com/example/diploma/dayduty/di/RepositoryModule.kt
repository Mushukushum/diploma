package com.example.diploma.dayduty.di

import com.example.diploma.dayduty.domain.model.repository.Repository
import com.example.diploma.dayduty.domain.use_cases.UseCases
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.DeleteDayDutyUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.GetSelectedDayDutiesUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.InsertDayDutyUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.UpdateDayDutyUseCase
import com.example.diploma.map.data.repository.MapRepository
import com.example.diploma.map.use_cases.GetPolygonsUseCase
import com.example.diploma.map.use_cases.InsertPolygonUseCase
import com.example.diploma.map.use_cases.MapUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getDayDutiesUseCase = GetSelectedDayDutiesUseCase(repository) ,
            insertDayDutyUseCase = InsertDayDutyUseCase(repository) ,
            updateDayDutyUseCase = UpdateDayDutyUseCase(repository) ,
            deleteDayDutyUseCase = DeleteDayDutyUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMapUseCases(repository: MapRepository): MapUseCases {
        return MapUseCases(
            getPolygonsUseCase = GetPolygonsUseCase(repository) ,
            insertPolygonUseCase = InsertPolygonUseCase(repository)
        )
    }
}