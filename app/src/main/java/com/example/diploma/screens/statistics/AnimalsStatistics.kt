package com.example.diploma.screens.statistics

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.animals.AnimalList
import com.example.diploma.animals.Animals
import com.example.diploma.screens.DropdownState
import com.example.diploma.statistics.AnimalsStatistics
import com.example.diploma.ui.theme.LARGE_PADDING
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun AnimalStatistics(
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
){



}
@Composable
fun FormForAnimalStatistics(
    navController: NavHostController,
    statisticsViewModel: StatisticsViewModel = hiltViewModel(),

) {

    statisticsViewModel.insertIntoAnimalsTable(AnimalsStatistics(
        id = 0,
        name = "кози",
        date = LocalDate.parse("2023-06-01"),
        amount = 25.0,
        quantity = 4

    ))

    statisticsViewModel.insertIntoAnimalsTable(AnimalsStatistics(
        id = 0,
        name = "кози",
        date = LocalDate.parse("2023-05-30"),
        amount = 12.0,
        quantity = 10

    ))


    val animals by statisticsViewModel.animalsList.collectAsState()

    val mass = remember {
        mutableStateOf(TextFieldValue())
    }
    val quantity = remember {
        mutableStateOf(TextFieldValue())
    }

    // Remember the state of the dropdown selections
    val firstFieldDropdownState = remember { DropdownState() }

    // Track the selection state of each dropdown
    var isFirstFieldSelected by remember { mutableStateOf(false) }

    // Store the selected values in mutable state variables
    var firstSelectedValue by remember { mutableStateOf<String?>(null) }

    var animal = AnimalList(0,"",0, LocalDate.now() )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp ,
                color = Black ,
                shape = RoundedCornerShape(size = 4.dp)
            ) ,
        color = White ,
        shape = RoundedCornerShape(size = LARGE_PADDING) ,
        contentColor = White
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp ,
                        color = Black ,
                        shape = RoundedCornerShape(size = 4.dp)
                    )
            )
            {
                TextButton(
                    onClick = {
                        firstFieldDropdownState.expanded = !firstFieldDropdownState.expanded
                    }
                ) {
                    Text(
                        text = firstFieldDropdownState.selectedItem ?: "Оберіть назву тварини" ,
                        fontSize = 16.sp
                    )
                }
                DropdownMenu(
                    expanded = firstFieldDropdownState.expanded ,
                    onDismissRequest = { firstFieldDropdownState.expanded = false }
                ) {
                    animals.forEach { option ->

                        Log.d("animal", animal.toString())
                        val string = "${option.name}, Кількість: ${option.quantity}, Вік: ${
                            ChronoUnit.DAYS.between(
                                option.date ,
                                LocalDate.now()
                            )
                        }"
                        DropdownMenuItem(
                            onClick = {
                                animal = option
                                firstFieldDropdownState.selectedItem = string
                                firstFieldDropdownState.expanded = false
                                isFirstFieldSelected = true
                                firstSelectedValue = string
                            },
                            text = {
                                Text(text = string)
                            }
                        )
                    }
                }
            }

                OutlinedTextField(
                    label = { Text("Маса тварини") } ,
                    value = mass.value ,
                    onValueChange = { mass.value = it } ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                    shape = RoundedCornerShape(8.dp)
                )
            OutlinedTextField(
                label = { Text("Кількість") } ,
                value = quantity.value ,
                onValueChange = { quantity.value = it } ,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    statisticsViewModel.insertIntoAnimalsTable(
                        AnimalsStatistics(
                            id = 0,
                            name = animal.name,
                            date = LocalDate.now(),
                            amount = mass.value.text.toDouble(),
                            quantity = quantity.value.text.toInt()

                    ))

                    navController.popBackStack()
                }
            )
            {
                Text(text = "Додати запис" , modifier = Modifier.padding(8.dp))
            }
        }
    }
}


