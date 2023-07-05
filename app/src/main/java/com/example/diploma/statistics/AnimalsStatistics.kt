package com.example.diploma.statistics

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity("animals_statistics")
@Parcelize
data class AnimalsStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val date: LocalDate,
    val amount: Double,
    val quantity: Int
):Parcelable
