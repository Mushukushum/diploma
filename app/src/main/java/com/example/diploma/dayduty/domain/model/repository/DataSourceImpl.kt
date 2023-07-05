package com.example.diploma.dayduty.domain.model.repository

import android.util.Log
import com.example.diploma.dayduty.data.DayDutiesDatabase
import com.example.diploma.dayduty.domain.model.DayDuty
import java.time.LocalDate

class DataSourceImpl(dayDutiesDatabase: DayDutiesDatabase): DataSource {

    private val dayDutiesDao = dayDutiesDatabase.dayDutiesDao()

    override suspend fun getSelectedDayDuties(date: LocalDate): List<DayDuty> {
        Log.d("Тест з datasource", "")
        return dayDutiesDao.getDayWithDuties(date.toString())
    }

    override suspend fun insertDayDuty(dayDuty: DayDuty) {
        dayDutiesDao.insertData(dayDuty)

    }

    override suspend fun updateData(dayDuty: DayDuty) {
        dayDutiesDao.updateData(dayDuty)
    }

    override suspend fun deleteData(dayDuty: DayDuty) {
        dayDutiesDao.deleteData(dayDuty)
    }
}