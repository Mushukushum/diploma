package com.example.diploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diploma.screens.day_duties.Plants
import com.google.android.gms.maps.model.LatLng

@Composable
fun TabLayout(tabIndex: Int) {
    val selectedTabIndex = remember { mutableStateOf(tabIndex) }

    Column(Modifier.fillMaxSize()) {
        TabRow(
            modifier = Modifier
                .height(40.dp)
                .background(MaterialTheme.colorScheme.primary),
            selectedTabIndex = selectedTabIndex.value,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Tab(
                selected = selectedTabIndex.value == 0,
                onClick = { selectedTabIndex.value = 0 }
            ) {
                Text("Карта полів")
            }
            Tab(
                selected = selectedTabIndex.value == 1,
                onClick = { selectedTabIndex.value = 1 }
            ) {
                Text("Список полів")
            }
        }
        Column(
            modifier = Modifier
                .weight(1f) // Distributes remaining space
        ) {

            when (selectedTabIndex.value) {
                0 -> MapScreen()
                1 -> Plants()
            }
        }
    }
}