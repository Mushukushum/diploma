package com.example.diploma.statistics

import javax.inject.Inject

class StatisticsRepository @Inject constructor(
    private val statisticsDataSource: StatisticsDataSource
) {

    suspend fun getAllAnimalInfo(): List<AnimalsStatistics> {
        return statisticsDataSource.getAllAnimalInfo()
    }

    suspend fun insertAnimalData(animalsStatistics: AnimalsStatistics) {
        statisticsDataSource.insertAnimalData(animalsStatistics)
    }

    suspend fun getAllStatistics(): List<FinanceStatistics> {
        return statisticsDataSource.getAllStatistics()
    }

    suspend fun insertFinanceStatistics(financeStatistics: FinanceStatistics) {
        statisticsDataSource.insertFinanceStatistics(financeStatistics = financeStatistics)
    }

    suspend fun getAllCropsInfo(): List<CropsStatistics>{
        return statisticsDataSource.getAllCropsInfo()
    }

    suspend fun insertCropsData(cropsStatistics: CropsStatistics) {
        statisticsDataSource.insertCropsData(cropsStatistics = cropsStatistics)
    }


}