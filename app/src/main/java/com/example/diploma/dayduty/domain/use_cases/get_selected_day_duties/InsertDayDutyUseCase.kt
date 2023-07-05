package com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties

import android.util.Log
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.dayduty.domain.model.repository.Repository

class InsertDayDutyUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(dayDuty: DayDuty){
        repository.insertDayDuty(dayDuty)
        Log.d("Тест з usecase", "")
    }
}