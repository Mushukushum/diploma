package com.example.diploma.dayduty.domain.model.repository

import com.example.diploma.dayduty.domain.model.DayDuty
import java.time.LocalDate

interface DataSource {

    suspend fun getSelectedDayDuties(date:LocalDate):List<DayDuty>

    suspend fun insertDayDuty(dayDuty: DayDuty)

    suspend fun updateData(dayDuty: DayDuty)

    suspend fun deleteData(dayDuty: DayDuty)


}