package com.example.diploma.statistics

import androidx.room.Insert
import com.example.diploma.map.data.MapCropPolygon

interface StatisticsDataSource {

    suspend fun getAllAnimalInfo(): List<AnimalsStatistics>

    suspend fun insertAnimalData(animalsStatistics: AnimalsStatistics)

    suspend fun getAllStatistics(): List<FinanceStatistics>

    suspend fun insertFinanceStatistics(financeStatistics: FinanceStatistics)

    suspend fun getAllCropsInfo(): List<CropsStatistics>

    suspend fun insertCropsData(cropsStatistics: CropsStatistics)
}