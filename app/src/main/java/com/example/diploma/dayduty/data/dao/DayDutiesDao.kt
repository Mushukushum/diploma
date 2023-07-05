package com.example.diploma.dayduty.data.dao

import androidx.room.*
import com.example.diploma.dayduty.domain.model.DayDuty

@Dao
interface DayDutiesDao {

//    @Query("SELECT * FROM day_duties WHERE date=:date")
//    fun getSelectedDayDuties(date:LocalDate):ListOfDayDuties

    @Query("SELECT * FROM day_duties WHERE localdate=:localDate")
    fun getDayWithDuties(localDate: String): List<DayDuty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dayDuty: DayDuty)

    @Update
    suspend fun updateData(dayDuty: DayDuty)

    @Delete
    suspend fun deleteData(dayDuty: DayDuty)
}