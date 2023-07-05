package com.example.diploma.statistics.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diploma.statistics.AnimalsStatistics
import com.example.diploma.statistics.CropsStatistics
import com.example.diploma.statistics.FinanceStatistics

@Dao
interface StatisticsDao {


    @Query("SELECT * FROM animals_statistics")
    fun getAllAnimalInfo(): List<AnimalsStatistics>

    @Insert
    fun insertAnimalData(animalsStatistics: AnimalsStatistics)

    @Query("SELECT * FROM finance_statistics")
    fun getAllStatistics(): List<FinanceStatistics>

    @Insert
    fun insertFinanceStatistics(financeStatistics: FinanceStatistics)

    @Query("SELECT * FROM crops_statistics")
    fun getAllCropsInfo(): List<CropsStatistics>

    @Insert
    fun insertCropsData(cropsStatistics: CropsStatistics)

}