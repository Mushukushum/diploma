package com.example.diploma.statistics

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diploma.dayduty.data.Converter
import com.example.diploma.map.data.MapConverter
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.map.data.MapDao
import com.example.diploma.statistics.dao.StatisticsDao


@Database(entities = [AnimalsStatistics::class, CropsStatistics::class, FinanceStatistics::class], version = 5)
@TypeConverters(Converter::class)
abstract class StatisticsDatabase: RoomDatabase() {

    abstract fun statisticsDao() : StatisticsDao
}