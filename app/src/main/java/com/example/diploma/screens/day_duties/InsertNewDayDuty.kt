package com.example.diploma.screens.day_duties

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.navigation.Screen
import com.example.diploma.ui.theme.Purple40
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewDayDuty(
    navController: NavHostController ,
    dayDutyViewModel: DayDutiesViewModel= hiltViewModel()
){
    Log.d("Date: ", dayDutyViewModel.currentDate.toString())
    DayDutiesItem(navController = navController)
}