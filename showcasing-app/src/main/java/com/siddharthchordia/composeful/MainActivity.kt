package com.siddharthchordia.composeful

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import composables.ComponentInfo
import composables.ComponentSnapshot
import composables.ExpandableCard
import theme.FancyDarkColors


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    Home(navController = navController)// Home screen content here (LazyColumn with ComponentSnapshot items)
                }
                composable(Screen.ComponentDetails.route) { backStackEntry ->
                    ComponentDetails()// ComponentDetails screen content here
                }
            }

        }

    }



}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ComponentDetails : Screen("componentDetails/{componentName}") {
        fun createRoute(componentName: String) = "componentDetails/$componentName"
    }
}

