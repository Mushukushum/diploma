package com.example.diploma.screens.animals

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diploma.animals.AnimalList
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun AnimalsDetails(
    animal: AnimalList
) {
    val database = FirebaseDatabase.getInstance("https://diploma-b2962-default-rtdb.europe-west1.firebasedatabase.app/")
    val rootReference = database.getReference("/animals")
    val animalList = remember { mutableStateListOf<Pair<String, String>>() }

    LaunchedEffect(Unit) {
        val dataSnapshot = rootReference.get().await()
        for (animalSnapshot in dataSnapshot.children) {

            val animalName = animalSnapshot.key // Get
            if (animal.name.lowercase(Locale.ROOT) == animalName) {// the animal name (e.g., "horse", "chicken", etc.)
                val animalData = animalSnapshot.value as? Map<* , *> // Cast the value to a map
                animalData?.let { data ->
                    for ((key , value) in data) {
                        // Access individual key-value pairs
                        animalList.add(Pair(key.toString() , value.toString()))
                        //println("Animal: $animalName, Key: $key, Value: $value")
                    }
                }
            }
        }
    }
    Log.d("Age: ", ChronoUnit.DAYS.between(LocalDate.now(), animal.date).toString())

    ShowProcedures(animalList = animalList, age = ChronoUnit.DAYS.between(animal.date, LocalDate.now()).toInt())
}

@Composable
fun ShowProcedures(animalList: List<Pair<String, String>>, age: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp , start = 16.dp , end = 16.dp)
    ) {
        Text(
            text = "Необхідні процедури",
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) { // Add this modifier
            for (animals in animalList) {
                val progress = calculateProgress(age, animals.first.toInt())
                Row {
                    Column(modifier = Modifier
                        .padding(top = 8.dp)
                        .weight(0.8f)) {
                        Text(animals.second , fontSize = 14.sp)
                        LinearProgressIndicator(
                            progress = progress ,
                            modifier = Modifier.fillMaxWidth() ,
                            color = forIndicator(progress)
                        )
                        Log.d("forIndicator(progress)" , forIndicator(progress).toString())
                    }
                    Column(modifier = Modifier.weight(0.1f)) {
                        Text(text = "${age}/${animals.first}" , fontSize = 14.sp, textAlign = TextAlign.Left)
                    }
                }
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
fun calculateProgress(age: Int , endAge: Int): Float {
    if (age >= endAge)
        return 1f
    else (age < endAge)
        return age.toFloat() / endAge.toFloat()
}

fun forIndicator(progress: Float): Color {
    return if (progress == 1f)
        Color.Green
    else Color.Red
}
