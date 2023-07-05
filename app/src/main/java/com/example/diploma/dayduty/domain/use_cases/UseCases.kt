package com.example.diploma.dayduty.domain.use_cases

import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.DeleteDayDutyUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.GetSelectedDayDutiesUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.InsertDayDutyUseCase
import com.example.diploma.dayduty.domain.use_cases.get_selected_day_duties.UpdateDayDutyUseCase

data class UseCases(
    val getDayDutiesUseCase: GetSelectedDayDutiesUseCase ,
    val insertDayDutyUseCase: InsertDayDutyUseCase ,
    val updateDayDutyUseCase: UpdateDayDutyUseCase ,
    val deleteDayDutyUseCase: DeleteDayDutyUseCase
)