package com.example.diploma.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.diploma.navigation.Screen
import com.mabn.calendarlibrary.ExpandableCalendar


@Composable
fun Calendar(navController: NavHostController) {
    ExpandableCalendar(onDayClick = {navController.navigate(Screen.DayDuties.passDate(it.toString()))})
}
