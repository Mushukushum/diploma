package com.example.diploma.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.time.LocalDate

@Entity("animals_types_table")
data class AnimalList(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String ,
    val quantity: Int,
    val date: LocalDate
)
