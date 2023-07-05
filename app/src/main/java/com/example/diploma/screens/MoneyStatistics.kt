package com.example.diploma.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.animals.AnimalList
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.statistics.StatisticsViewModel
import com.example.diploma.statistics.AnimalsStatistics
import com.example.diploma.statistics.FinanceStatistics
import com.example.diploma.ui.theme.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PieChart(
    radiusOuter: Dp = 90.dp ,
    chartBarWidth: Dp = 20.dp ,
    animDuration: Int = 1000 ,
    statisticsViewModel: StatisticsViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val finances by statisticsViewModel.finances.collectAsState()

    val data = mutableMapOf<String , Double>()

    finances.forEach {
        data[it.category] = it.money
    }

    val totalSum = data.values.sum()
    val floatValue = data.map { 360f * it.value / totalSum }

    val colors = listOf(
        Purple200 ,
        Purple500 ,
        Teal200 ,
        Purple700 ,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }

    val animateSize by animateDpAsState(
        targetValue = if (animationPlayed) radiusOuter * 2f else 0.dp ,
        animationSpec = tween(
            durationMillis = animDuration ,
            delayMillis = 0 ,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f ,
        animationSpec = tween(
            durationMillis = animDuration ,
            delayMillis = 0 ,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Purple40 ,
                onClick = {
                    navController.navigate(Screen.FinanceStatistics.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add ,
                    contentDescription = "Add FAB" ,
                    tint = Color.White ,
                )
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp , bottom = 16.dp) // Add padding for spacing
                .systemBarsPadding() // Adjust for system bars
                .verticalScroll(rememberScrollState()), // Enable scrolling if needed
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier.padding(top = 16.dp) // Add top padding for spacing
            ) {
                Box(
                    modifier = Modifier.size(animateSize) ,
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(radiusOuter * 2f)
                            .rotate(animateRotation)
                    ) {
                        var lastValue = 0f
                        floatValue.forEachIndexed { index , value ->
                            drawArc(
                                color = colors[index] ,
                                startAngle = lastValue ,
                                sweepAngle = value.toFloat() ,
                                useCenter = false ,
                                style = Stroke(chartBarWidth.toPx() , cap = StrokeCap.Butt)
                            )
                            lastValue += value.toFloat()
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize() ,
                        contentAlignment = Alignment.Center
                    ) {
                        var totalEarnings = 0.0
                        data.forEach {
                            totalEarnings += it.value
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally ,
                            modifier = Modifier.padding(start = 15.dp) ,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Загальна сума" ,
                                fontWeight = FontWeight.Medium ,
                                fontSize = 22.sp ,
                                color = Color.Black
                            )
                            Text(
                                text = totalEarnings.toString() ,
                                fontWeight = FontWeight.Medium ,
                                fontSize = 22.sp ,
                                color = Color.Gray
                            )
                        }
                    }
                }

                DetailsPieChart(data = data.toMap() , colors = colors)
            }
        }
    }
}





@Composable
fun DetailsPieChart(
    data: Map<String, Double>,
    colors: List<Color>
) {
    Column(modifier = Modifier.padding(vertical = 80.dp, horizontal = 40.dp)) {
        data.forEach { (key, value) ->
            DetailsPieChartItem(data = Pair(key, value), color = colors[data.keys.indexOf(key)])
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Double> ,
    height: Dp = 45.dp ,
    color: Color
) {
    Surface(
        modifier = Modifier.padding(vertical = 10.dp),
        color = Color.Transparent
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(
                        color = color ,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AddFinancesInformation(
    statisticsViewModel:StatisticsViewModel = hiltViewModel(),
    navController: NavHostController
){
    val category = remember {
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

    val options = listOf("Прибутки", "Витрати")
    val optionValue = remember {
        mutableStateOf("")
    }

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
                        text = firstFieldDropdownState.selectedItem ?: "Оберіть вид операції" ,
                        fontSize = 16.sp
                    )
                }
                DropdownMenu(
                    expanded = firstFieldDropdownState.expanded ,
                    onDismissRequest = { firstFieldDropdownState.expanded = false }
                ) {
                    options.forEach { option ->
                        optionValue.value = option

                        DropdownMenuItem(
                            onClick = {
                                firstFieldDropdownState.selectedItem = option
                                firstFieldDropdownState.expanded = false
                                isFirstFieldSelected = true
                                firstSelectedValue = option
                            },
                            text = {
                                Text(option)
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                label = {Text("Категорія витрат") } ,
                value = category.value ,
                onValueChange = { category.value = it } ,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) ,
                shape = RoundedCornerShape(8.dp)
            )
            OutlinedTextField(
                label = { Text("Сума") } ,
                value = quantity.value ,
                onValueChange = { quantity.value = it } ,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) ,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if(optionValue.value == "Витрати") {
                        statisticsViewModel.insertFinanceStatistics(
                            FinanceStatistics(
                                id = 0 ,
                                type = firstSelectedValue.toString() ,
                                money = quantity.value.text.toDouble()*-1 ,
                                category = category.value.text
                            )
                        )
                    }else if(optionValue.value == "Прибутки"){
                        statisticsViewModel.insertFinanceStatistics(
                            FinanceStatistics(
                                id = 0 ,
                                type = firstSelectedValue.toString() ,
                                money = quantity.value.text.toDouble() ,
                                category = category.value.text
                            )
                        )
                    }
                    navController.popBackStack()
                }
            )
            {
                Text(
                    text = "Додати запис" ,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

