package com.example.diploma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diploma.animals.AnimalList
import com.example.diploma.screens.*
import com.example.diploma.screens.animals.AnimalsDetails
import com.example.diploma.screens.animals.AnimalsScreen
import com.example.diploma.screens.animals.InsertNewAnimal
import com.example.diploma.screens.day_duties.InsertNewDayDuty
import com.example.diploma.screens.day_duties.ListOfDayDutiesScreen
import com.example.diploma.screens.day_duties.Plants
import com.example.diploma.screens.statistics.AddCropsStatistics
import com.example.diploma.screens.statistics.FormForAnimalStatistics
import java.time.LocalDate

@Composable
fun SetupNavGraph(navController:NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Map.route
    ){
        composable(route = Screen.Calendar.route){
            Calendar(navController = navController)
        }
        composable(
            route = Screen.DayDuties.route,
            arguments = listOf(navArgument("date"){
                type = NavType.StringType
            })
        ) {
            ListOfDayDutiesScreen(navController = navController)
        }
        composable(
            route = Screen.AnimalDetails.route,
            arguments = listOf(navArgument("item"){
                type = NavType.StringType
            })
        ){
            it.arguments?.getString("item")?.let { string ->
                val animal = string.split(",")
                AnimalsDetails(AnimalList(id = animal[0].toInt(), name = animal[1], quantity = animal[2].toInt(), date = LocalDate.parse(animal[3])))
            }
        }
        composable(
            route = Screen.InsertNewDayDuty.route
        ){
            InsertNewDayDuty(navController = navController)
        }
        composable(
            route = Screen.Map.route
        ){
            MapScreen()
        }
        composable(
            route = Screen.Statistics.route
        ){
            StatisticsTabLayout(navController = navController)
        }
        composable(
            route = Screen.Plants.route
        ){
            Plants()
        }
        composable(
            route = Screen.Animals.route
        ){
            AnimalsScreen(navController = navController)
        }
        composable(
            route = Screen.Farm.route
        ){
            Household(navController = navController)
        }
        composable(
            route = Screen.AddNewAnimalType.route,
            arguments = listOf(navArgument("item"){
                type = NavType.StringType
            })
        ){
            it.arguments?.getString("item")?.let { list ->
                val animal = list.split(",")
                InsertNewAnimal(navController = navController, listOfAnimals = animal)
            }
        }
        composable(route = Screen.FormForAnimalStatistics.route){
            FormForAnimalStatistics(navController = navController)
        }
        composable(route = Screen.FinanceStatistics.route){
            AddFinancesInformation(navController = navController)
        }
        composable(route = Screen.FormForCropsStatistics.route){
            AddCropsStatistics(navController = navController)
        }
    }
}