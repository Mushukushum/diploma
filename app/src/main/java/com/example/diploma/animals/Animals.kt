package com.example.diploma.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diploma.util.Constants.ANIMALS_DATABASE
import com.example.diploma.util.Constants.ANIMALS_TABLE

@Entity(ANIMALS_TABLE)
data class Animals(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val icon: Int,
    val name: String,
    val quantity: Int
)