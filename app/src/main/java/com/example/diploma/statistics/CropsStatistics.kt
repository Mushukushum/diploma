package com.example.diploma.statistics

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("crops_statistics")
data class CropsStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val nameOfCulture: String ,
    val area: Double ,
    val date: LocalDate,
    val amount: Double
    )