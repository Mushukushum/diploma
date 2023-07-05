package com.example.diploma.navigation


import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.diploma.ui.theme.Purple500
import com.example.diploma.ui.theme.Purple700

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val bottomNavItems = listOf(
        Screen.Calendar ,
        Screen.Map ,
        Screen.Animals
    )
    BottomAppBar(
        containerColor = Purple500,
        contentColor = Color.White
    ) {
        bottomNavItems.forEach { item ->
            IconButton(
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(painter = painterResource(id = item.icon) , contentDescription = item.name)
            }
        }
    }
}