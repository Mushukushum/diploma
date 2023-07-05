package com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties

import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.dayduty.domain.model.repository.Repository
import java.time.LocalDate

class DeleteDayDutyUseCase(
    private val repository: Repository
    ) {
        suspend operator fun invoke(dayDuty: DayDuty){
            return repository.deleteData(dayDuty = dayDuty)
        }
    }