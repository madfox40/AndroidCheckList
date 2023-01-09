package com.example.checklist

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.checklist.Navigation.AppScreens
import com.example.checklist.Screens.*
import com.example.checklist.ui.theme.CheckListTheme
import com.example.splashscreen.ui.theme.SplashScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyAuthScreen()
            //Auth()
            //AppNavigation()
            CheckListTheme {
                AppNavigation()

                //MyAuthScreen(navHostController = NavHostController(this))
                //MyCheckList()
                //MyAuthScreen()
            }
        }
        //Setup
        setupAuth()
    }

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
            composable(route = AppScreens.AuthScreen.route){
                MyAuthScreen(navController)
            }
            composable(route = AppScreens.SecondScreen.route + "/{text}",
                arguments = listOf(navArgument(name = "text"){
                    type = NavType.StringType
                })){
                SecondScreen(navController, it.arguments?.getString("text"))
            }
        }
    }

    data class MyItems(val name: String, val body: String)

    var canAuth = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private fun setupAuth() {
        if (BiometricManager.from(this).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            Log.d("hola", "llegamos")
            canAuth = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentication")
                .setSubtitle("Autenticate")
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG
                            or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )
                .build()
        } else {
            Log.d("hola", "No llegamos")
        }
        Log.d("hola", "llegar " + canAuth.toString())
    }

    fun authenticate(
        auth: (auth: Boolean) -> Unit,
    ) {
        if (canAuth) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                    }
                }).authenticate(promptInfo)
        } else {
            Log.d("sion", canAuth.toString() + " Can Auten")
            Log.d("sion", "No Hay Biometrico")
            auth(true)
        }
    }

    @Composable
    fun MyAuthScreen(navHostController: NavHostController) {
        var auth by remember {
            mutableStateOf(false)
        }
        if (auth == false) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.checklistlogo),
                    contentDescription = "CheckList logo"
                )
                Button(onClick = {
                    authenticate {
                        auth = it
                    }
                }) {
                    Text(text = "Autenticate")
                }
            }
        } else {
            //Vamos a la pagina principal
            //navHostController.popBackStack()
            //navHostController.navigate(AppScreens.CheckListScreen.route)
            Log.d("hola", "verificado")
            CheckListScreen(navHostController)
        }
    }


    @Composable
    fun AuthCpy() {
        var auth by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .background(if (auth) Color.Green else Color.Red)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                if (auth) "You need to authenticate" else "You are authenticated",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (auth) {
                    auth = false
                } else {
                    authenticate {
                        auth = it
                    }
                }
            }) {
                Text(if (auth) "Cerrar" else "Authenticate")
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AppNavigation()
    }
}
