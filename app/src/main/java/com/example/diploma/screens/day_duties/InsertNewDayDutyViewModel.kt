package com.example.diploma.screens.day_duties

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.dayduty.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertNewDayDutyViewModel @Inject constructor(
    private val useCases: UseCases ,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var selectedDay: String? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val date = savedStateHandle.get<String>("current_day")
            selectedDay = date
        }
    }

    fun insertDayDuty(dayDuty: DayDuty) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.insertDayDutyUseCase(dayDuty)
            Log.d("Тест з функції додавання запису", "")
        }

    }
}