package com.example.diploma.map.use_cases

import android.util.Log
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.map.data.repository.MapRepository

class InsertPolygonUseCase(
    private val repository: MapRepository
) {

    suspend operator fun invoke(mapCropPolygon: MapCropPolygon){
        repository.insertPolygon(mapCropPolygon)
    }
}