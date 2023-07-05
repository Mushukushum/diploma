package com.example.diploma.map.data.repository

import com.example.diploma.map.data.MapCropPolygon
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val mapDataSource: MapDataSource
) {

    suspend fun getPolygons(): List<MapCropPolygon>{
        return mapDataSource.getPolygons()
    }

    suspend fun insertPolygon(mapCropPolygon: MapCropPolygon){
        mapDataSource.insertPolygon(mapCropPolygon)
    }

}