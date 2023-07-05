package com.example.diploma.navigation

import com.example.diploma.R
import com.example.diploma.animals.AnimalList
import com.example.diploma.util.toJson

sealed class Screen(val route: String , val name:String, var icon:Int){
    object Calendar: Screen("calendar_screen", "Calendar", R.drawable.calendar)
    object DayDuties: Screen("day_duties/{date}", "Day_duties", R.drawable.day_duty){
        //Change parameter type if will be needed.
        //I don`t know which type I will use
        fun passDate(date: String):String{
            return "day_duties/$date"
        }
    }
    object InsertNewDayDuty: Screen("insert_new_day_duty/{date}", "Insert new day duty", R.drawable.day_duty){
        fun passCurrentDay(date: String): String {
            return "insert_new_day_duty/$date"
        }
    }
    object Map: Screen("map", "Map", R.drawable.map)

    object Statistics: Screen("statistics", "Statistics", R.drawable.stats)
    object Plants: Screen("plants", "Список полів", R.drawable.plants)
    object Animals: Screen("animals", "Animals", R.drawable.animals)
    object Farm: Screen("household", "Farm", R.drawable.plants)

    object AnimalDetails: Screen("animal_details/{item}", "Animal Details", R.drawable.animals){
        fun passAnimal(item: AnimalList):String{
            val itemString = "${item.id},${item.name},${item.quantity},${item.date}"
            return "animal_details/$itemString"
        }
    }
    object AddNewAnimalType: Screen("add_new_animal_type/{item}", "Add new animal type", R.drawable.animals){

        fun passAnimals(item: List<String>): String{
            val itemString = item.joinToString(",")
            return "add_new_animal_type/$itemString"
        }
    }

    object FormForAnimalStatistics: Screen("add_new_animal_stastistics", "Add new animal statistic", R.drawable.stats)
    object FinanceStatistics: Screen("finance_statistics", "Finance statistics", R.drawable.stats)

    object FormForCropsStatistics: Screen("add_new_crops_statistics", "Add new crops statistics", R.drawable.stats)
}
