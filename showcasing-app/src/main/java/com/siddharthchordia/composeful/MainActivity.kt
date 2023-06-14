package com.siddharthchordia.composeful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import composables.ComponentConfiguration
import composables.ExpandableCard
import composables.NavigationDrawer
import composables.SearchBar
import theme.FancyDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val showcaseViewModel = viewModel<ShowcaseViewModel>()
            FancyDarkTheme {
                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                val viewModel: ShowcaseViewModel = viewModel()
                val searchDialogVisible = remember { mutableStateOf(false) }
                Scaffold(

                    topBar = {

                        TopAppBar(
                            title = { Text("Component Library") },
                            actions = {
                                if (currentRoute == MainScreen.Home.route) {
                                    IconButton(onClick = { searchDialogVisible.value = true }) {
                                        Icon(Icons.Default.Search, contentDescription = "Search")
                                    }
                                }
                            }
                        )
                    },
                    drawerContent = {
                        NavigationDrawer(
                            categories = showcaseViewModel.categories,
                            onCategorySelected = { category ->
                                // Handle category selection
                            },
                            onSearchQueryChanged = { query ->
                                showcaseViewModel.onSearchQueryChanged(query)
                            }
                        )
                    },
                    bottomBar = {
                        if (currentRoute in MainScreen.values()
                            .map { it.route }
                        ) MainBottomNavigation(
                            navController,
                            currentRoute ?: ""
                        )
                    }
                ) { it ->
                    if (searchDialogVisible.value) {
                        Dialog(onDismissRequest = { searchDialogVisible.value = false }) {
                            Surface(modifier = Modifier.padding(16.dp)) {
                                SearchBar(onSearchQueryChanged = { query ->
                                    viewModel.onSearchQueryChanged(
                                        query
                                    )
                                })
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = MainScreen.Home.route,
                        modifier = Modifier.padding(it)
                    ) {
//                        composable(Screen.Home.route) {
//                            Home(
//                                navController = navController,
//                                showcaseViewModel = showcaseViewModel
//                            ) // Home screen content here (LazyColumn with ComponentSnapshot items)
//                        }
                        composable(Screen.ComponentDetails.route) { backStackEntry ->
                            val componentName = backStackEntry.arguments?.getString("componentName")
                            val componentDetailsInfo = remember {
                                mutableStateOf( showcaseViewModel.getComponentDetailsInfo
                                (componentName))}

                            componentDetailsInfo.value?.let {
                                ListComponentDetails(
                                    componentName = componentName,
                                    componentDetailsInfo = it,
                                )
                            }

                        }
                        composable(MainScreen.Home.route) {
                            Home(
                                navController = navController,
                                showcaseViewModel
                            )
                        }
                        composable(MainScreen.Favorites.route) {
                            // Favorites screen content here
                            Favorites(
                                showcaseViewModel = showcaseViewModel,
                                navController = navController
                            )
                        }
                        composable(MainScreen.Settings.route) {
                            // Settings screen content here
                        }
//
//                        composable("componentDetails/{componentName}") { backStackEntry ->
//                        }
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
//    object Home : Screen("home")
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

sealed class ComponentDetailType {
    object ListComponentDetails : ComponentDetailType()
    object SingleComponentDetails : ComponentDetailType()
    data class CustomComponentDetails(val componentName: String) : ComponentDetailType()
}
