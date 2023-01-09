package com.example.checklist.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.checklist.Screens.CheckListScreen
import com.example.checklist.Screens.FirstScreen
import com.example.checklist.Screens.SecondScreen
import com.example.splashscreen.ui.theme.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route){
        composable(route = AppScreens.MainScreen.route){
            SplashScreen(navController)
        }
        composable(route = AppScreens.CheckListScreen.route){
            CheckListScreen(navController)
        }
        composable(route = AppScreens.SecondScreen.route + "/{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })){
            SecondScreen(navController, it.arguments?.getString("text"))
        }
    }
}