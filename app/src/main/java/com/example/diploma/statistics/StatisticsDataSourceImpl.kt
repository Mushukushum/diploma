package com.example.diploma.statistics

import com.example.diploma.map.data.MapCropPolygon

class StatisticsDataSourceImpl(statisticsDatabase: StatisticsDatabase):StatisticsDataSource {

    private val statisticsDao = statisticsDatabase.statisticsDao()

    override suspend fun getAllAnimalInfo(): List<AnimalsStatistics> {
        return statisticsDao.getAllAnimalInfo()
    }

    override suspend fun insertAnimalData(animalsStatistics: AnimalsStatistics) {
        statisticsDao.insertAnimalData(animalsStatistics)
    }

    override suspend fun getAllStatistics(): List<FinanceStatistics> {
        return statisticsDao.getAllStatistics()
    }

    override suspend fun insertFinanceStatistics(financeStatistics: FinanceStatistics) {
        statisticsDao.insertFinanceStatistics(financeStatistics = financeStatistics)
    }

    override suspend fun insertCropsData(cropsStatistics: CropsStatistics) {
        statisticsDao.insertCropsData(cropsStatistics)
    }

    override suspend fun getAllCropsInfo(): List<CropsStatistics> {
        return statisticsDao.getAllCropsInfo()
    }
}