package com.example.diploma.screens.day_duties

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.dayduty.domain.model.repository.Repository
import com.example.diploma.dayduty.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDutiesViewModel @Inject constructor(
    private val useCases: UseCases ,
    savedStateHandle: SavedStateHandle ,
    private val repository: Repository
): ViewModel() {
    var _selectedDayDuties = mutableStateListOf<DayDuty>()
    val currentDate: String? = savedStateHandle.get<String>("date")
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedDayDuties = repository.getSelectedDayDuties(LocalDate.parse(currentDate)).toMutableStateList()
        }
    }
    fun getSelectedDayDuties(): SnapshotStateList<DayDuty> {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedDayDuties =
                repository.getSelectedDayDuties(LocalDate.parse(currentDate)).toMutableStateList()
        }
        return _selectedDayDuties
    }

    fun insertDayDuty(dayDuty: DayDuty) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.insertDayDutyUseCase(dayDuty)
            _selectedDayDuties.add(dayDuty)
        }
    }

    fun updateDayDuty(dayDuty: DayDuty){
       viewModelScope.launch(Dispatchers.IO) {
         useCases.updateDayDutyUseCase(dayDuty)
      }
   }
    fun deleteDayDuty(dayDuty: DayDuty){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteDayDutyUseCase(dayDuty)
        }
    }
}