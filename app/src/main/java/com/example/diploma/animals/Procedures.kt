package com.example.diploma.animals

import androidx.room.PrimaryKey

data class Procedures(
    @PrimaryKey(autoGenerate = true)val id: Int,
    val days: Int,
    val nameOfProcedure: String
)
