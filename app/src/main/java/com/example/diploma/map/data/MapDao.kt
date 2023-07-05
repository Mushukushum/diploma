package com.example.diploma.map.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.android.gms.maps.model.LatLng

@Dao
interface MapDao {

    @Query("SELECT * FROM map_crop")
    suspend fun getPolygons(): List<MapCropPolygon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPolygon(coordinates: MapCropPolygon)

}