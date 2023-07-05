package com.example.diploma.animals.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
@Dao
interface AnimalsDao {

    @Query("SELECT * FROM animals_types_table")
    fun getAnimals(): List<AnimalList>

    @Query("SELECT * FROM animals_table")
    fun getAnimalTypes(): List<Animals>

    @Insert
    suspend fun addNewAnimalType(animals: AnimalList)

    @Insert
    suspend fun addNewAnimal(animal: Animals)

}