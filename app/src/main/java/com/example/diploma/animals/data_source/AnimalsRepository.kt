package com.example.diploma.animals.data_source

import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val animalsDataSource: AnimalsDataSource
) {

//    val animalTypes = animalsDataSource.animalTypes
    suspend fun getAnimalsType(): List<Animals>{
        return animalsDataSource.getAnimalsTypes()
    }

//    suspend fun getAnimals(): List<AnimalList>{
//        return animalsDataSource.getAnimals()
//    }

    suspend fun insertNewAnimalType(animals: AnimalList){
        animalsDataSource.insertNewAnimalType(animals = animals)
    }

    suspend fun addNewAnimal(animal: Animals){
        animalsDataSource.addNewAnimal(animal = animal)
    }

    suspend fun getExpandableItemsForType():List<AnimalList>{
        return animalsDataSource.getExpandableItemsForType()
    }
}