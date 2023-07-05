package com.example.diploma.dayduty.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Day(
    @PrimaryKey(autoGenerate = false) var date:LocalDate,
    var dayDutyId: Int
)
