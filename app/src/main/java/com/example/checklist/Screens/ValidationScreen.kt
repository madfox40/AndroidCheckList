package com.example.checklist.Screens

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.checklist.MainActivity

class ValidationScreen {
    private  var canAuthenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private fun setupAuth(context: Context){
        if (BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS){
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Autenticate utilizando el sensor biométrico")
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }
    private fun authenticate(auth: (auth:Boolean) ->  Unit, context: Context, activity: AppCompatActivity) {

        if (canAuthenticate) {
            BiometricPrompt(activity, ContextCompat.getMainExecutor(context),
                object: BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                    }
                }).authenticate(promptInfo)
        } else {
            auth(true)
        }
    }
/*
    @Composable
    fun Auth(){
        var auth by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier
            .background(if (auth) Color.Green else Color.Red)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(if (auth) "Necesitas autenticarte" else "Estás autenticado",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if(auth){
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
        @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreviewAuth(){
        Auth()
    }
    */
}

