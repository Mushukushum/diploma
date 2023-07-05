package com.example.diploma.animals.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
import com.example.diploma.animals.database.AnimalsDatabase

class AnimalsDataSourceImpl(animalsDatabase: AnimalsDatabase): AnimalsDataSource {

    private val animalsDao = animalsDatabase.animalsDao()

    //override val animalTypes: LiveData<List<Animals>> = animalsDao.getAnimalTypes()

    override suspend fun getAnimalsTypes(): List<Animals> {
        Log.d("dsi", animalsDao.getAnimalTypes().toString())
        return animalsDao.getAnimalTypes()
    }

//    override suspend fun getAnimals(): List<AnimalList> {
//        return animalsDao.getAnimals()
//    }

    override suspend fun insertNewAnimalType(animals: AnimalList) {
        animalsDao.addNewAnimalType(animals = animals)
    }
    override suspend fun addNewAnimal(animal: Animals) {
        animalsDao.addNewAnimal(animal = animal)
    }

    override suspend fun getExpandableItemsForType():List<AnimalList> {
        return animalsDao.getAnimals()
    }
}