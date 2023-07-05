package com.example.diploma.screens.animals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AnimalsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {

    val name: String? = savedStateHandle.get<String>("name")

}