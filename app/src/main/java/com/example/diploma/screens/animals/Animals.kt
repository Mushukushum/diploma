package com.example.diploma.screens.animals

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.diploma.animals.AnimalList
import com.example.diploma.navigation.Screen
import com.example.diploma.screens.map.DropdownState
import com.example.diploma.ui.theme.LARGE_PADDING
import com.example.diploma.ui.theme.Purple40
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AnimalsScreen(
    navController: NavHostController,
    animalsViewModel: AnimalsViewModel = hiltViewModel()
){

    val database = FirebaseDatabase.getInstance("https://diploma-b2962-default-rtdb.europe-west1.firebasedatabase.app/")
    val rootReference = database.getReference("/animals")
    val animalList = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val dataSnapshot = rootReference.get().await()
        for (animalSnapshot in dataSnapshot.children) {

            val animalName = animalSnapshot.key // Get
            animalList.add(animalName.toString())
            println("Animal: $animalName")
            }
        }

    LaunchedEffect(Unit) {
        animalsViewModel.getAnimals()
    }

    val animals by animalsViewModel.animalsList.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.background(Purple40),
                onClick = {
                    navController.navigate(Screen.AddNewAnimalType.passAnimals(animalList.toList()))
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
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {
                LazyColumn {
                    items(items = animals, key = { item: AnimalList -> item.id }) { animalType ->
                        ExpandableItem(
                            animal = animalType,
                            navController = navController
                        )
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableItem(animal: AnimalList,
                   navController: NavHostController
) {
    val clicked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { clicked.value = true })
            .padding(start = 8.dp),
        verticalAlignment = CenterVertically
    ) {

        Column(modifier = Modifier.weight(0.7f)) {
            Text(
                text = animal.name ,
                style = MaterialTheme.typography.bodySmall ,
                fontSize = 22.sp
            )
            Row {
                Text("Вік: " , fontStyle = FontStyle.Italic, fontSize = 16.sp)
                Text(
                    modifier = Modifier.size(40.dp) ,
                    text = ChronoUnit.DAYS.between(animal.date , LocalDate.now()).toString(),
                    fontSize = 16.sp
                )
            }
        }
        Column {
                Text("Кількість: " , fontStyle = FontStyle.Italic)
                Text(
                    text = animal.quantity.toString() ,
                    modifier = Modifier.size(40.dp) ,
                    textAlign = TextAlign.Center ,
                    fontSize = 22.sp
                )
            }
        }
    if (clicked.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.AnimalDetails.passAnimal(animal))
        }
    }
}

@Composable
fun InsertNewAnimal (
    listOfAnimals: List<String>,
    animalsViewModel: AnimalsViewModel = hiltViewModel() ,
    navController: NavHostController
) {

    val quantity = remember {
        mutableStateOf(TextFieldValue())
    }

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    val firstFieldDropdownState = remember { DropdownState() }

    var isFirstFieldSelected by remember { mutableStateOf(false) }

    var firstSelectedValue by remember { mutableStateOf<String?>(null) }

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
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp ,
                            color = Color.Black ,
                            shape = RoundedCornerShape(size = 4.dp)
                        )) {
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
                        listOfAnimals.distinct().forEach { option ->
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
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    label = { Text("Вкажіть кількість особин") } ,
                    value = quantity.value ,
                    onValueChange = { quantity.value = it } ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    shape = RoundedCornerShape(8.dp) ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CalendarField(
                    label = "Оберіть дату народження" ,
                    value = selectedDate ,
                    onDateSelected = { selectedDate = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        animalsViewModel.insertNewAnimal(
                            AnimalList(
                                id = 0 ,
                                name = firstSelectedValue.toString() ,
                                quantity = quantity.value.text.toInt() ,
                                date = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time))
                            )
                        )
                        navController.popBackStack()
                    }
                )
                {
                    Text(
                        text = "Додати запис"
                    )
                }
            }
        }
    }
}



@Composable
fun CalendarField(
    label: String,
    value: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val datePickerState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        TextField(
            value = datePickerState.value,
            onValueChange = { datePickerState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            enabled = false,
            label = { Text(text = label) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                }
            }
        )

        if (expanded) {
            MyDatePicker(
                value = value,
                onDateSelected = {
                    expanded = false
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = dateFormat.format(it.time)
                    datePickerState.value = TextFieldValue(formattedDate) // Display the selected date in the TextField
                    onDateSelected(it)
                }
            )
        }
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
fun MyDatePicker(value: Calendar, onDateSelected: (Calendar) -> Unit) {
    val context = LocalContext.current
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        onDateSelected(selectedDate)
    }

    // Create a DatePickerDialog with the current value as the initial selection
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            dateSetListener,
            value.get(Calendar.YEAR),
            value.get(Calendar.MONTH),
            value.get(Calendar.DAY_OF_MONTH)
        )
    }

    DisposableEffect(Unit) {
        datePickerDialog.setOnCancelListener { onDateSelected(value) }
        onDispose { }
    }

    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}