package com.siddharthchordia.composeful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import composables.ComponentInfo
import composables.ComponentSnapshot
import composables.ExpandableCard
import theme.FancyDarkColors
import theme.FancyDarkTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FancyDarkTheme {
                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(
                    topBar = {
                        TopAppBar() {

                        }
                    },
                    bottomBar = {
                    if (currentRoute in MainScreen.values().map { it.route }) MainBottomNavigation(
                        navController,
                        currentRoute ?: ""
                    )
                }
                ) {


                    NavHost(
                        navController = navController,
                        startDestination = MainScreen.Home.route,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(Screen.Home.route) {
                            Home(navController = navController)// Home screen content here (LazyColumn with ComponentSnapshot items)
                        }
                        composable(Screen.ComponentDetails.route) { backStackEntry ->
                            ComponentDetails()// ComponentDetails screen content here
                        }
                        composable(MainScreen.Home.route) {
                            Home(navController = navController)
                        }
                        composable(MainScreen.Favorites.route) {
                            // Favorites screen content here
                        }
                        composable(MainScreen.Settings.route) {
                            // Settings screen content here
                        }
                    }
                }


            }
        }

    }



}

sealed class MainScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : MainScreen("home", "Home", Icons.Default.Home)
    object Favorites : MainScreen("favorites", "Favorites", Icons.Default.Favorite)
    object Settings : MainScreen("settings", "Settings", Icons.Default.Settings)

    companion object {
        fun values() = listOf(Home, Favorites, Settings)
    }
}


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ComponentDetails : Screen("componentDetails/{componentName}") {
        fun createRoute(componentName: String) = "componentDetails/$componentName"
    }
}
@Composable
fun MainBottomNavigation(navController: NavController, currentRoute: String) {
    BottomNavigation {
        MainScreen.values().forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


