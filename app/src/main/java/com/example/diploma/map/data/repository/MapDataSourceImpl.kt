package com.example.diploma.map.data.repository

import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.map.data.MapDatabase

class MapDataSourceImpl(mapDatabase: MapDatabase):MapDataSource {

    private val mapDao = mapDatabase.mapDao()

    override suspend fun getPolygons(): List<MapCropPolygon> {
        return mapDao.getPolygons()
    }

    override suspend fun insertPolygon(mapCropPolygon: MapCropPolygon) {
        return mapDao.insertPolygon(mapCropPolygon)
    }
}