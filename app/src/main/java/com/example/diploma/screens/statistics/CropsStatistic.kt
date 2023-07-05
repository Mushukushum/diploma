package com.example.diploma.screens.statistics

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.animals.AnimalList
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.DropdownState
import com.example.diploma.screens.LinearChart
import com.example.diploma.screens.sumDoubleValuesByDate
import com.example.diploma.statistics.AnimalsStatistics
import com.example.diploma.statistics.CropsStatistics
import com.example.diploma.ui.theme.LARGE_PADDING
import com.example.diploma.ui.theme.Purple40
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CropsChartScreen(
    navController: NavHostController,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {

    val database = FirebaseDatabase.getInstance("https://diploma-b2962-default-rtdb.europe-west1.firebasedatabase.app/")
    val rootReference = database.getReference("/cultures")
    val cultureList = remember { mutableStateListOf<String>() }
    val cultureStats by statisticsViewModel.cropsStatisctis.collectAsState()
    val mapForStats = remember {
        mutableMapOf<String , Pair<List<LocalDate> , List<Double>>>()
    }
    val listForDates = mutableListOf<LocalDate>()
    val listForAmount = mutableListOf<Double>()
    val isLaunchedEffectFinished = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val dataSnapshot = rootReference.get().await()

        for (cultureSnapshot in dataSnapshot.children) {
            listForDates.clear()
            listForAmount.clear()
            val cultureName = cultureSnapshot.key // Get
            cultureList.add(cultureName.toString())
            for(culture in cultureStats){
                if(culture.nameOfCulture == cultureName){
                    Log.d("animal", cultureName)
                    listForDates.add(culture.date)
                    listForAmount.add(culture.amount/culture.area)
                    //mapForStats[animalName] = Pair(first = animal.amount*animal.quantity, second = animal.date)
                }
            }

            mapForStats[cultureName.toString()] = Pair(listForDates.toList(), listForAmount.toList())
            isLaunchedEffectFinished.value = true        }
    }

    //sumDoubleValuesByDate(mapForStats)

    // Define the state for the selected option
    var selectedOption by remember {
        mutableStateOf(cultureList.firstOrNull() ?: "Оберіть тип даних для діаграми")
    }

    var expanded by remember { mutableStateOf(false) }

    androidx.compose.material3.Scaffold(
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                containerColor = Purple40 ,
                onClick = {
                    navController.navigate(Screen.FormForCropsStatistics.route)
                }
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Rounded.Add ,
                    contentDescription = "Add FAB" ,
                    tint = Color.White ,
                )
            }
        }
    ) {
        Column {
            if (cultureList.isEmpty()) {
                androidx.compose.material3.Text(text = "No data available")
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp ,
                            color = Color.Black ,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                ) {
                    TextButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        androidx.compose.material3.Text(
                            text = selectedOption ,
                            fontSize = 16.sp
                        )
                    }
                    androidx.compose.material3.DropdownMenu(
                        expanded = expanded ,
                        onDismissRequest = { expanded = false }
                    ) {
                        cultureList.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                },
                                text = {
                                    Text(text = option)
                                }
                            )
                        }
                    }
                }
            }

            if (isLaunchedEffectFinished.value) {
                Log.d("From launch effect" , "")
                Log.d("map" , mapForStats.keys.toString())
                val (years , moneyReceived) = mapForStats[selectedOption] ?: Pair(
                    emptyList() ,
                    emptyList()
                )
                if (years.isNotEmpty() && moneyReceived.isNotEmpty()) {
                    LinearChart(years , moneyReceived)
                } else {
                    androidx.compose.material3.Text(text = "Немає даних")
                }
            }
        }
    }
}


@Composable
fun AddCropsStatistics(
    navController: NavHostController ,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
){
    val crops by statisticsViewModel.cropsList.collectAsState()

    val amount = remember {
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

    var cropToDatabase = MapCropPolygon(0, listOf<LatLng>() ,0.0, currentCulture = "", previousCulture = "", cropIndex = 1.0 )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp ,
                color = Color.Black ,
                shape = RoundedCornerShape(size = 4.dp)
            ) ,
        color = Color.White ,
        shape = RoundedCornerShape(size = LARGE_PADDING) ,
        contentColor = Color.White
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
                        color = Color.Black ,
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
                        text = firstFieldDropdownState.selectedItem ?: "Оберіть ділянку" ,
                        fontSize = 16.sp
                    )
                }
                DropdownMenu(
                    expanded = firstFieldDropdownState.expanded ,
                    onDismissRequest = { firstFieldDropdownState.expanded = false }
                ) {
                    crops.forEach { crop ->
                        val string = "Ділянка ${crop.id}, Площа: ${(crop.area/10000).toInt()} га, Поточна культура: ${crop.currentCulture}"
                        DropdownMenuItem(
                            onClick = {
                                cropToDatabase = crop
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
                label = { Text("Обсяг урожаю") } ,
                value = amount.value ,
                onValueChange = { amount.value = it } ,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    cropToDatabase.currentCulture?.let {
                        CropsStatistics(
                            id = 0,
                            nameOfCulture = it ,
                            amount = amount.value.text.toDouble(),
                            area = cropToDatabase.area,
                            date = LocalDate.now()
                        )
                    }?.let {
                        statisticsViewModel.insertIntoCropsTable(
                            it
                        )
                    }

                    navController.popBackStack()
                }
            )
            {
                Text(text = "Додати запис" , modifier = Modifier.padding(8.dp))
            }
        }
    }
}