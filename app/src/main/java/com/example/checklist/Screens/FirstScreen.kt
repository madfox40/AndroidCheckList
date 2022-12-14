package com.example.checklist.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.checklist.Navigation.AppScreens

@Composable
fun FirstScreen(navController: NavController) {
    Scaffold (topBar = {
        TopAppBar() {
            Text(text = "First Screen")
        }
    }) {
        BodyContent(navController)
    }
}

@Composable
fun BodyContent(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola navegación")
        Button(onClick = { navController.navigate(route = AppScreens.SecondScreen.route + "/Este es un parámetro")}) {
            Text(text = "Navega")
        }
    }
}

