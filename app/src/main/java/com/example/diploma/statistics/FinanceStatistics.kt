package com.example.diploma.statistics

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("finance_statistics")
data class FinanceStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val money: Double,
    val type: String
)
