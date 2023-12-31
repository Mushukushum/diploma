package com.example.diploma.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.map.data.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel@Inject constructor(
    private val repository: MapRepository
): ViewModel() {

    private var polygon = listOf<MapCropPolygon>()

    private val _crops = MutableStateFlow<List<MapCropPolygon>>(emptyList())
    val crops: StateFlow<List<MapCropPolygon>> = _crops

    init{
        viewModelScope.launch (Dispatchers.IO){
        val crop = repository.getPolygons()
        _crops.emit(crop)
        }
    }

    fun getPolygons():List<MapCropPolygon>{

        viewModelScope.launch(Dispatchers.IO) {
            polygon = repository.getPolygons()
        }
        return polygon
    }
    fun insertPolygons(mapCropPolygon: MapCropPolygon){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPolygon(mapCropPolygon)
        }
    }

}