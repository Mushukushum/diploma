package com.example.diploma.screens.day_duties

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.dayduty.domain.model.DayDuty
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.SwipeBackground
import com.example.diploma.ui.theme.LARGE_PADDING
import com.example.diploma.ui.theme.Purple40
import java.time.LocalDate
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class , ExperimentalMaterial3Api::class ,
    ExperimentalFoundationApi::class
)
@Composable
fun ListOfDayDutiesScreen(
    navController: NavHostController,
    dayDutiesViewModel: DayDutiesViewModel = hiltViewModel()) {

    val listOfDayDuties = dayDutiesViewModel.getSelectedDayDuties()

    Scaffold(
        floatingActionButton = {
        FloatingActionButton(
            containerColor = Purple40,
            onClick = {
                dayDutiesViewModel.currentDate?.let { it ->
                    Screen.InsertNewDayDuty.passCurrentDay(it)
                        .let { navController.navigate(it) }
                }

            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = White,
            )
        }
    }) {
        LazyColumn {
            items(
                items = listOfDayDuties,
                key = {dayDuty -> dayDuty.id},
            ) { item ->
                val currentItem by rememberUpdatedState(item)
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            dayDutiesViewModel.deleteDayDuty(currentItem)
                            listOfDayDuties.remove(item)
                            true
                        } else false
                    }
                )

                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)){
                    dayDutiesViewModel.deleteDayDuty(item)
                    listOfDayDuties.remove(item)
                }

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .animateItemPlacement(),
                    directions = setOf(
                        DismissDirection.StartToEnd,
                        DismissDirection.EndToStart
                    ),
                    background = {
                        SwipeBackground(dismissState)
                    },
                    dismissContent = {
                        ShowDayDuties(item)
                    }
                )
            }
        }
    }
}

@Composable
fun ShowDayDuties(dayDuties: DayDuty) {
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
        shape = RoundedCornerShape(size = LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Text(dayDuties.title)
            Text(dayDuties.description)
        }
        }
}

@Preview
@Composable
fun ShowDayDutiesPreview(){
    ShowDayDuties(
        dayDuties = DayDuty(
            id = 0 ,
            title = "Test..." ,
            localDate = LocalDate.parse("2023-03-08"),
            description = "Test....."
        )
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayDutiesItem (
    dayDutiesViewModel: DayDutiesViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val title = remember {
        mutableStateOf(TextFieldValue())
    }

    val description = remember {
        mutableStateOf(TextFieldValue())
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp ,
                color = Black ,
                shape = RoundedCornerShape(size = 4.dp)
            ),
            color = White ,
            shape = RoundedCornerShape(size = LARGE_PADDING) ,
            contentColor = White
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(label = { Text("Заголовок") } ,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    value = title.value,
                    onValueChange = {title.value = it}
                )
                OutlinedTextField(
                    label = { Text("Опис") } ,
                    value = description.value ,
                    onValueChange = { description.value = it },
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth()
                        .background(White)
                        .padding(4.dp)
                        .fillMaxHeight() ,
                    shape = RoundedCornerShape(8.dp) ,
                    maxLines = 10
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        dayDutiesViewModel.insertDayDuty(
                            DayDuty(
                                id = 0,
                                localDate = LocalDate.parse(dayDutiesViewModel.currentDate),
                                title = title.value.text,
                                description = description.value.text
                            )
                        )
                        navController.popBackStack()
                    }
                )
                {
                    Text(text = "Додати запис", modifier = Modifier.padding(8.dp))
                }

            }

        }
    }

