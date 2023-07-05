package com.example.diploma.animals.data_source

import androidx.lifecycle.LiveData
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals

interface AnimalsDataSource {

//    val animalTypes: LiveData<List<Animals>>

    suspend fun getAnimalsTypes(): List<Animals>

//    suspend fun getAnimals():List<AnimalList>

    suspend fun insertNewAnimalType(animals: AnimalList)

    suspend fun addNewAnimal(animal: Animals)

    suspend fun getExpandableItemsForType():List<AnimalList>

}