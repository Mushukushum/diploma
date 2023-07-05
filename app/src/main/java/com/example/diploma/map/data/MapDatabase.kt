package com.example.diploma.map.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [MapCropPolygon::class], version = 4)
@TypeConverters(MapConverter::class)
abstract class MapDatabase: RoomDatabase() {

    abstract fun mapDao() : MapDao
}