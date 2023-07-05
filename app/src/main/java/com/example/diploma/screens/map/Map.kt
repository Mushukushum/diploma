package com.example.diploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diploma.R
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.screens.map.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt


@Composable
fun MapScreen(
    mapViewModel:MapViewModel = hiltViewModel()
) {

    val listOfDots = remember { mutableStateListOf<LatLng>() }
    var count = 0
    var drawPolygon = false

    var selectedPolygon by remember { mutableStateOf<MapCropPolygon?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.0753, 29.1922) , 12f)
    }

    val refreshMarkers = {
        val centerLocation = cameraPositionState.position.target
        val topLeftLocation = cameraPositionState.projection?.visibleRegion?.farLeft ?: cameraPositionState.position.target
        val radius = SphericalUtil.computeDistanceBetween(topLeftLocation, centerLocation)
        //viewModel.stationsWithin(centerLocation.latitude, centerLocation.longitude, radius)
    }

    if (cameraPositionState.isMoving) {
        refreshMarkers()
    }


    val showWebView = remember {
        mutableStateOf(false)
    }

    var showDialog by remember {
        mutableStateOf(false) }
    val dismissDialog = { showDialog = false }

    var cropToUpdateData by remember {
        mutableStateOf(MapCropPolygon(
            id = 0,
            coordinates = listOf(),
            area = 0.0,
            currentCulture = "",
            previousCulture = "",
            cropIndex = 1.0
        ))
    }

    if (showDialog) {
        if(cropToUpdateData.currentCulture.isNullOrBlank()){
            cropToUpdateData.previousCulture?.let {
                SelectNewCultureDialog(index = cropToUpdateData.cropIndex, onConfirm = {
                    newCurrent,previous,newIndex ->
                mapViewModel.insertPolygons(MapCropPolygon(
                    id = cropToUpdateData.id,
                    coordinates = listOfDots,
                    currentCulture = newCurrent,
                    previousCulture = previous,
                    cropIndex = newIndex,
                    area = SphericalUtil.computeArea(listOfDots)
                )
                )
            },
                dismissDialog = dismissDialog )
            }
            drawPolygon = false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {


        GoogleMap(
            modifier = Modifier.fillMaxSize() ,
            cameraPositionState = cameraPositionState ,
            onMapLoaded = refreshMarkers ,
            properties = MapProperties(mapType = MapType.HYBRID) ,
            onMapClick = {
                if (drawPolygon) {
                    showWebView.value = false
                    listOfDots.add(LatLng(it.latitude , it.longitude))
                    showWebView.value = true
                }

            }
        ) {
            mapViewModel.getPolygons().forEach { mapcrop ->
                Polygon(
                    points = mapcrop.coordinates ,
                    strokeColor = Color.Yellow ,
                    fillColor = Color.Yellow.copy(alpha = 0.2f) ,
                    clickable = true,
                    onClick = {
                        selectedPolygon = mapcrop
                        showDialog = true
                    }
                )
                MarkerInfoWindow(
                    state = rememberMarkerState(position = calculateCentralCoordinate(mapcrop.coordinates)),
                    title = mapcrop.currentCulture,
                ) { marker ->
                    // Implement the custom info window here
                    Column(modifier = Modifier.background(shape = RectangleShape, color = Color.White)) {
                        Text(marker.title ?: "Default Marker Title")
                        Text(marker.snippet ?: "Попередня культура: ${mapcrop.previousCulture}\nІВҐ: ${mapcrop.cropIndex}\nПлоща: ${(mapcrop.area / 10000).roundToInt()} га")
                    }
                }
            }
            if (listOfDots.size > 1) {
                for (i in 1 until listOfDots.size) {
                    Polyline(
                        points = listOfDots , color = Color.Yellow
                    )
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            Button(
                modifier = Modifier.fillMaxWidth() ,
                onClick = {
                    showDialog = true
                }
            ) {
                Text("Save crop")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { drawPolygon = true }
            ) {
                Text("Add new crop")
            }
        }
    }
}

@Composable
fun SelectNewCultureDialog(index:Double , onConfirm: (String? , String? , Double) -> Unit , dismissDialog: () -> Unit) {

    val database = FirebaseDatabase.getInstance("https://diploma-b2962-default-rtdb.europe-west1.firebasedatabase.app/")
    val rootReference = database.getReference("/cultures")
    val culturesList = remember { mutableStateListOf<String>() }
    val previousList = remember { mutableStateListOf<String>() }
    val admissibilityList = remember {
        mutableStateListOf<Pair<String, Pair<String, String>>>()
    }

    LaunchedEffect(Unit) {
        val dataSnapshot = rootReference.get().await()
        for (cultureSnapshot in dataSnapshot.children) {

            val cultureName = cultureSnapshot.key // Get the animal name (e.g., "horse", "chicken", etc.)
            val animalData = cultureSnapshot.value as? Map<*, *> // Cast the value to a map
            animalData?.let { data ->
                for ((previous, admissibility) in data) {
                    // Access individual key-value pairs
                    culturesList.add(cultureName.toString())
                    previousList.add(previous.toString())
                    //println("культура: $cultureName, попередник: $key, допустимість: $value")
                    admissibilityList.add(Pair(admissibility.toString(), Pair(first = cultureName.toString(), second = previous.toString())))
                }
            }
        }
    }

    // Remember the state of the dropdown selections
    val firstFieldDropdownState = remember { DropdownState() }
    val secondFieldDropdownState = remember { DropdownState() }

    // Track the selection state of each dropdown
    var isFirstFieldSelected by remember { mutableStateOf(false) }
    var isSecondFieldSelected by remember { mutableStateOf(false) }

    // Store the selected values in mutable state variables
    var firstSelectedValue by remember { mutableStateOf<String?>(null) }
    var secondSelectedValue by remember { mutableStateOf<String?>(null) }

    // Create the dialog
    Dialog(
        onDismissRequest = { dismissDialog() }
    ) {

        var first = ""

        var second = ""

        var tempIndex = index

        AlertDialog(
            onDismissRequest = { /* Handle dialog dismiss if needed */ },
            title = { Text(text = "Вибір культури для посіву", fontSize = 20.sp, style = TextStyle(fontWeight = FontWeight.Bold)) },
            text = {
                Column {
                    Box {
                        TextButton(
                            onClick = {
                                firstFieldDropdownState.expanded = !firstFieldDropdownState.expanded
                            }
                        ) {
                            Text(text = firstFieldDropdownState.selectedItem ?: stringResource(R.string.select_first_field), fontSize = 16.sp)
                        }

                        DropdownMenu(
                            expanded = firstFieldDropdownState.expanded,
                            onDismissRequest = { firstFieldDropdownState.expanded = false }
                        ) {
                            culturesList.distinct().forEach { option ->
                                first = option
                                DropdownMenuItem(
                                    onClick = {
                                        firstFieldDropdownState.selectedItem = option
                                        firstFieldDropdownState.expanded = false
                                        isFirstFieldSelected = true
                                        firstSelectedValue = option
                                    },
                                    text = {
                                        Text(text = option)
                                    }
                                )
                            }
                        }
                    }

                    Box {
                        TextButton(
                            onClick = {
                                secondFieldDropdownState.expanded = !secondFieldDropdownState.expanded
                            }
                        ) {
                            Text(text = secondFieldDropdownState.selectedItem ?: stringResource(R.string.select_second_field), fontSize = 16.sp)
                        }

                        DropdownMenu(
                            expanded = secondFieldDropdownState.expanded,
                            onDismissRequest = { secondFieldDropdownState.expanded = false }
                        ) {
                            previousList.distinct().forEach { option ->
                                second = option
                                DropdownMenuItem(
                                    onClick = {
                                        secondFieldDropdownState.selectedItem = option
                                        secondFieldDropdownState.expanded = false
                                        isSecondFieldSelected = true
                                        secondSelectedValue = option
                                    },
                                    text = {
                                        Text(text = option)
                                    }
                                )
                            }
                        }
                    }

                    if (isFirstFieldSelected && isSecondFieldSelected) {
                        val selectedAdmissibility = admissibilityList.find { it.second.first == firstSelectedValue && it.second.second == secondSelectedValue }
                        if (selectedAdmissibility != null) {
                            val backgroundColor = when (selectedAdmissibility.first) {
                                "недопустимий" -> Color.Red
                                "допустимий" -> Color.Green
                                "найкращий" -> Color.Cyan
                                else -> Color.Transparent // Default background color if none of the expected values match
                            }

                            tempIndex = when(selectedAdmissibility.first){
                                "недопустимий" -> index - 0.1
                                "допустимий" -> if(index<=0.95) index + 0.05 else 1.0
                                "найкращий" -> 1.0
                                else -> index
                            }
                            
                            val iconPainter = when (selectedAdmissibility.first){
                                "недопустимий" -> painterResource(id = R.drawable.worst)
                                "допустимий" -> painterResource(id = R.drawable.good)
                                "найкращий" -> painterResource(id = R.drawable.brilliant)
                                else -> painterResource(id = R.drawable.unknown)
                            }

                            val textForCard = when(selectedAdmissibility.first){
                                "недопустимий" -> "Увага! Не рекомендується сіяти цю культуру на цій ділянці. Така комбінація попередника і наступника призводить до виснаження ґрунту та є недопустимою в рамках проведення сівозміни."
                                "допустимий" -> "Така комбінація попередника і наступника є допустимою в плані дотримання сівозміни та призводить до невеликого виснаження ґрунту. Однак, бажано обрати кращого наступника для максимальної родючості."
                                "найкращий" -> "Ця комбінація попередника та наступника є ідеальним варіантом, сприяє максимальній родючості ґрунту та мінімальному його виснаженню."
                                else -> "Немає інформації про цю комбінацію попередника і наступника."
                            }

                            Card(
                                modifier = Modifier.fillMaxWidth().background(backgroundColor.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = iconPainter,
                                        contentDescription = "Your Icon",
                                        modifier = Modifier.size(40.dp),
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = textForCard,
                                        color = Color.Black, // Text color is always black for visibility
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Check if options are selected in both dropdowns
                        if (firstFieldDropdownState.selectedItem != null && secondFieldDropdownState.selectedItem != null) {
                            onConfirm(firstFieldDropdownState.selectedItem, secondFieldDropdownState.selectedItem, tempIndex)
                            dismissDialog()
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {}
                ) {
                    Text(text = "Відміна")
                }
            },
            modifier = Modifier.height(400.dp)
        )
    }
}
class DropdownState {
    var expanded by mutableStateOf(false)
    var selectedItem by mutableStateOf<String?>(null)
}

fun calculateCentralCoordinate(coordinates: List<LatLng>): LatLng {

    var sumLat = 0.0
    var sumLng = 0.0

    for (coordinate in coordinates) {
        sumLat += coordinate.latitude
        sumLng += coordinate.longitude
    }

    val avgLat = sumLat / coordinates.size
    val avgLng = sumLng / coordinates.size

    return LatLng(avgLat, avgLng)
}