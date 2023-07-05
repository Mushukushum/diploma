package com.example.diploma.screens.day_duties

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diploma.map.data.MapCropPolygon
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.TabLayout
import com.example.diploma.screens.map.MapViewModel
import com.example.diploma.ui.theme.LARGE_PADDING
import com.example.diploma.ui.theme.Purple40
import com.google.android.gms.maps.model.LatLng
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Plants(
    mapViewModel: MapViewModel = hiltViewModel()
){

    val plants by mapViewModel.crops.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.background(Purple40),
                onClick = {
//                    navController.navigate(Screen.AddNewAnimalType.passAnimals(animalList.toList()))
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
                .background(Color.White.copy(alpha = 0.5f))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    items(items = plants , key = { item: MapCropPolygon -> item.id }) { plant ->
                        calculateCentralCoordinate(plant.coordinates)?.let { it1 ->
                            CropsItem(
                                currentCulture = plant.currentCulture.toString() ,
                                previousCulture = plant.previousCulture.toString() ,
                                area = plant.area.roundToInt() ,
                                index = plant.cropIndex ,
                                coord = it1
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropsItem(
    currentCulture: String,
    previousCulture: String,
    area: Int,
    index: Double,
    coord: LatLng
) {

    var showSelectedPlot by remember { mutableStateOf(false) }
    
    if(showSelectedPlot){
        TabLayout(tabIndex = 0, )
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp ,
                color = Color.Black ,
                shape = RoundedCornerShape(size = 4.dp)
            ),
        color = Color.White ,
        shape = RoundedCornerShape(size = LARGE_PADDING) ,
        contentColor = Color.White,
        onClick = {
             showSelectedPlot = true
         }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth() ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(0.6f)
                .padding(start = 12.dp)) {
                Row() {
                    Text("Поточна культура: ", fontSize = 12.sp)
                    Text(
                        text = currentCulture ,
                        style = MaterialTheme.typography.bodySmall ,
                        fontSize = 12.sp ,
                        textAlign = TextAlign.Center
                    )
                }
                Row() {
                    Text("Попередник: ", fontSize = 12.sp)
                    Text(
                        text = previousCulture ,
                        style = MaterialTheme.typography.bodySmall ,
                        fontSize = 12.sp ,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Column() {
                Text("ІВҐ")
                Text(
                    text = index.toString()
                )
            }
            Spacer(modifier = Modifier.size(size = LARGE_PADDING))
                Column(modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(0.2f)) {
                    Text("Площа: " , fontStyle = FontStyle.Italic)
                    Text(
                        text = area.toString(),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }


fun calculateCentralCoordinate(coordinates: List<LatLng>): LatLng? {
    if (coordinates.isEmpty()) {
        return null
    }

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




