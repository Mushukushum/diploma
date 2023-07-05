package com.example.diploma.dayduty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diploma.dayduty.data.dao.DayDutiesDao
import com.example.diploma.dayduty.domain.model.Day
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.map.data.MapConverter
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.map.data.MapDao

@Database(entities = [DayDuty::class], version = 3)
@TypeConverters(Converter::class)
abstract class DayDutiesDatabase: RoomDatabase(){

    abstract fun dayDutiesDao(): DayDutiesDao


//    companion object{
//
//        @Volatile
//        private var INSTANCE: DayDutiesDatabase? = null
//
//        fun getDatabase(context: Context): DayDutiesDatabase{
//            val tempInstance = INSTANCE
//            if(tempInstance!=null){
//                return tempInstance
//            }
//
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    DayDutiesDatabase::class.java,
//                    "day_duties"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}