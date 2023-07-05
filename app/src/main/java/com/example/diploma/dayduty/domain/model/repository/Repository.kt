package com.example.diploma.dayduty.domain.model.repository

import android.util.Log
import com.example.diploma.dayduty.domain.model.DayDuty
import java.time.LocalDate
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun getSelectedDayDuties(localDate: LocalDate):List<DayDuty>{
        Log.d("Тест з репозиторія", "")
        return dataSource.getSelectedDayDuties(localDate)
    }

    suspend fun insertDayDuty(dayDuty: DayDuty){
        dataSource.insertDayDuty(dayDuty)

    }

    suspend fun updateData(dayDuty: DayDuty){
        dataSource.updateData(dayDuty)
    }

    suspend fun deleteData(dayDuty: DayDuty){
        dataSource.deleteData(dayDuty)
    }
}