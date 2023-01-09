package com.example.checklist.Navigation

sealed class AppScreens(val route:String) {
    object MainScreen: AppScreens("splash_screen")
    object CheckListScreen: AppScreens("checklist_screen")
    object SecondScreen: AppScreens("second_screen")
    object AuthScreen: AppScreens("auth_screen")
}
