package com.example.diploma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TopBar(navController: NavHostController){

    val bottomNavItems = listOf(
        Screen.Calendar,
        Screen.Map,
        Screen.Statistics,
        Screen.Farm
    )

//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route


}