package com.example.diploma.map.data.repository

import com.example.diploma.map.data.MapCropPolygon

interface MapDataSource {

    suspend fun getPolygons(): List<MapCropPolygon>
    suspend fun insertPolygon(mapCropPolygon: MapCropPolygon)
}