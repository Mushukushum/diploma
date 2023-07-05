package com.example.diploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diploma.screens.statistics.CropsChartScreen
import com.example.diploma.screens.statistics.FormForAnimalStatistics

@Composable
fun StatisticsTabLayout(
    navController:NavHostController
) {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex.value,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.height(40.dp).background(MaterialTheme.colorScheme.primary)
        ) {
            Tab(
                selected = selectedTabIndex.value == 0,
                onClick = { selectedTabIndex.value = 0 }
            ) {
                Text("Статистика тварин")
            }
            Tab(
                selected = selectedTabIndex.value == 1,
                onClick = { selectedTabIndex.value = 1 }
            ) {
                Text("Статистика рослин")
            }
            Tab(
                selected = selectedTabIndex.value == 2,
                onClick = { selectedTabIndex.value = 2 }
            ) {
                Text("Фінансовий облік")
            }
        }

        when (selectedTabIndex.value) {
            0 -> ChartScreen(navController = navController)
            1 -> { CropsChartScreen(navController = navController)}
            2 -> PieChart(navController = navController)
        }
    }
}