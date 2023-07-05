package com.example.diploma.dayduty.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diploma.util.Constants.DAY_DUTIES_TABLE
import kotlinx.parcelize.Parcelize
import java.time.LocalDate


@Entity(DAY_DUTIES_TABLE)
@Parcelize
data class DayDuty(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var title:String ,
    val localDate:LocalDate,
    var description:String
):Parcelable
