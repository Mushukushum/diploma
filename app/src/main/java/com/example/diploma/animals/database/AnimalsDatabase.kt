package com.example.diploma.animals.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
import com.example.diploma.dayduty.data.Converter

@Database(entities=[Animals::class, AnimalList::class], version = 3)
@TypeConverters(Converter::class)
abstract class AnimalsDatabase:RoomDatabase() {

    abstract fun animalsDao():AnimalsDao

}