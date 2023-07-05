package com.example.diploma.screens.animals

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
import com.example.diploma.animals.data_source.AnimalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val repository: AnimalsRepository
): ViewModel() {

    private val _animalsList = MutableStateFlow<List<AnimalList>>(emptyList())
    val animalsList:StateFlow<List<AnimalList>> = _animalsList

    private val itemIdsList = MutableStateFlow(listOf<Int>())

    private val expandableItemsMap = mutableStateMapOf<Int, List<AnimalList>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val animalTypes = repository.getExpandableItemsForType()
            _animalsList.emit(animalTypes)
        }
    }
    suspend fun getAnimals(): List<AnimalList>{
        return animalsList.first()
    }

    fun insertNewAnimal(animals:AnimalList){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNewAnimalType(animals = animals)
        }
    }
}