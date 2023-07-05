package com.example.diploma.screens
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.statistics.StatisticsViewModel
import com.example.diploma.ui.theme.Purple40
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChartScreen(
    navController: NavHostController,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {

    val database = FirebaseDatabase.getInstance("https://diploma-b2962-default-rtdb.europe-west1.firebasedatabase.app/")
    val rootReference = database.getReference("/animals")
    val animalList = remember { mutableStateListOf<String>() }
    val animalsStats by statisticsViewModel.animalsStatisctis.collectAsState()
    val mapForStats = remember {
        mutableMapOf<String , Pair<List<LocalDate> , List<Double>>>()
    }
    val listForDates = mutableListOf<LocalDate>()
    val listForAmount = mutableListOf<Double>()
    val isLaunchedEffectFinished = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val dataSnapshot = rootReference.get().await()

        for (animalSnapshot in dataSnapshot.children) {
            listForDates.clear()
            listForAmount.clear()
            val animalName = animalSnapshot.key // Get
            animalList.add(animalName.toString())
            for(animal in animalsStats){
                if(animal.name == animalName){
                    Log.d("animal", animalName)
                    listForDates.add(animal.date)
                    listForAmount.add(animal.amount*animal.quantity)
                    //mapForStats[animalName] = Pair(first = animal.amount*animal.quantity, second = animal.date)
                }
            }

            mapForStats[animalName.toString()] = Pair(listForDates.toList(), listForAmount.toList())
            isLaunchedEffectFinished.value = true        }
    }

    sumDoubleValuesByDate(mapForStats)

    // Define the state for the selected option
    var selectedOption by remember {
        mutableStateOf(animalList.firstOrNull() ?: "Оберіть тип даних для діаграми")
    }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Purple40 ,
                onClick = {
                    navController.navigate(Screen.FormForAnimalStatistics.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White ,
                )
            }
        }
    ) {
        Column {
            if (animalList.isEmpty()) {
                Text(text = "No data available")
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
                        Text(
                            text = selectedOption ,
                            fontSize = 16.sp
                        )
                    }
                    DropdownMenu(
                        expanded = expanded ,
                        onDismissRequest = { expanded = false }
                    ) {
                        animalList.forEach { option ->
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
                Log.d("From launch effect", "")
                Log.d("map", mapForStats.keys.toString())
                val (years , moneyReceived) = mapForStats[selectedOption] ?: Pair(emptyList() , emptyList())
                if (years.isNotEmpty() && moneyReceived.isNotEmpty()) {
                    LinearChart(years , moneyReceived)
                } else {
                    Text(text = "Немає даних")
                }
            }
        }
    }
}

fun sumDoubleValuesByDate(dataMap: Map<String, Pair<List<LocalDate>, List<Double>>>): Map<String, Pair<List<LocalDate>, List<Double>>> {
    val summedMap = mutableMapOf<String, Pair<List<LocalDate>, List<Double>>>()
    for ((key, pair) in dataMap) {
        val dates = pair.first
        val values = pair.second
        val summedValues = mutableMapOf<LocalDate, Double>()
        for (i in dates.indices) {
            val date = dates[i]
            val value = values[i]
            summedValues[date] = summedValues.getOrDefault(date, 0.0) + value
        }
        summedMap[key] = Pair(dates, summedValues.values.toList())
    }
    return summedMap
}

@Composable
fun LinearChart(years: List<LocalDate>, moneyReceived: List<Double>) {
    var clickedIndex by remember { mutableStateOf(-1) }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f)
        .width(LocalConfiguration.current.screenWidthDp.dp * 0.9f)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    val index = calculateClickedIndex(offset)
                    clickedIndex = index
                    true
                },
                onDragEnd = {
                    clickedIndex = -1
                },
                onDrag = { _, _ -> }
            )
        }) {
        val startX = 50f
        val endX = size.width - 50f
        val startY = size.height - 50f
        val endY = 50f

        // Draw x-axis
        drawLine(
            color = Color.Black,
            start = Offset(startX, startY),
            end = Offset(endX, startY),
            strokeWidth = 2f
        )

        // Draw data points and chart path
        val dataPoints = years.zip(moneyReceived)
        val xStep = (endX - startX) / (years.size - 1)
        val yRange = moneyReceived.maxOrNull()!! - moneyReceived.minOrNull()!!
        val yScale = (startY - endY) / yRange

        val path = Path()

        val xLabels = if (years.size <= 10) {
            years // Show all labels if there are 10 or fewer data points
        } else {
            val step = years.size / 5 // Adjust the step based on the number of data points
            years.filterIndexed { index, _ -> index % step == 0 } // Filter labels to show only every step-th label
        }

        dataPoints.forEachIndexed { index, (year, money) ->
            val x = startX + index * xStep
            val y = startY - (money - moneyReceived.minOrNull()!!) * yScale

            if (index == 0) {
                path.moveTo(x, startY)
                path.lineTo(x, y.toFloat())
            } else {
                val prevX = startX + (index - 1) * xStep
                val prevY = startY - (moneyReceived[index - 1] - moneyReceived.minOrNull()!!) * yScale
                val controlX1 = prevX + xStep / 2
                val controlY1 = prevY
                val controlX2 = x - xStep / 2
                val controlY2 = y
                path.cubicTo(
                    controlX1, controlY1.toFloat(), controlX2, controlY2.toFloat(), x,
                    y.toFloat()
                )
            }

            // Draw year label if it's in xLabels
            if (year in xLabels) {
                val textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
                val paint = Paint().asFrameworkPaint()
                paint.textSize = textStyle.fontSize.toPx()
                paint.color = textStyle.color.toArgb()

                drawContext.canvas.nativeCanvas.drawText(
                    year.toString(),
                    x - 50f,
                    startY + 40f,
                    paint
                )
            }
        }

        path.lineTo(endX, startY)
        path.close()

        // Draw filled path
        drawPath(
            path = path,
            color = Color.Blue.copy(alpha = 0.5f)
        )

        // Draw y-axis
        drawLine(
            color = Color.Black,
            start = Offset(endX, startY),
            end = Offset(endX, endY),
            strokeWidth = 2f
        )

        // Draw y-axis labels
        val yStep = yRange / 5
        val yLabels = List(5) { index ->
            (moneyReceived.minOrNull()!! + index * yStep).toInt()
        }

        yLabels.forEachIndexed { index, label ->
            val labelY = startY - index * (startY - endY) / 4 - 10f
            val labelText = label.toString()

            val textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            val paint = Paint().asFrameworkPaint()
            paint.textSize = textStyle.fontSize.toPx()
            paint.color = textStyle.color.toArgb()

            val labelX = endX - 50f
            val labelBaseline = labelY + paint.fontMetrics.bottom
            drawContext.canvas.nativeCanvas.drawText(
                labelText,
                labelX,
                labelBaseline,
                paint
            )
        }
    }
}






//@Preview
//@Composable
//fun LinearChartPreview() {
//    val years = listOf(2018, 2019, 2020, 2021, 2022)
//    val moneyReceived = listOf(1000f, 1500f, 2000f, 1800f, 2500f)
//
//    LinearChart(years, moneyReceived)
//
//}


private fun calculateClickedIndex(offset: Offset): Int {
    // Calculate the index based on the offset and chart dimensions
    // Adjust the logic based on your chart layout and requirements
    return 0
}
