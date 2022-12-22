package com.example.checklist.Screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.checklist.ui.theme.CheckListTheme
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MyAuthScreen() {

    LazyColumn() {
        item {
            var autenticated by rememberSaveable() {
                mutableStateOf(false)
            }
            var email by rememberSaveable() {
                mutableStateOf("")
            }
            var password by rememberSaveable() {
                mutableStateOf("")
            }
            var autenticatedEmail by rememberSaveable {
                mutableStateOf("")
            }
            if (!autenticated) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter Email") })
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter Password") })
            } else {
                Text(text = autenticatedEmail)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                if (!autenticated) {
                    Button(onClick = {
                        if (email != "" && password != "") {
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        email = it.result?.user?.email.toString() ?: ""
                                        autenticated = true
                                        autenticatedEmail = email
                                    } else {
                                        Log.d("hola", "error de registro")
                                    }
                                }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Text(text = "Register")
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Button(onClick = {
                        if (email != "" && password != "") {
                            FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        email = it.result?.user?.email.toString() ?: ""
                                        autenticated = true
                                        autenticatedEmail = email
                                    } else {
                                        Log.d("hola", "error de registro")
                                    }
                                }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Text("Login")
                    }
                } else {
                    Button(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        email = ""
                        password = ""
                        autenticated = false
                    }, modifier = Modifier.weight(1f)) {
                        Text(text = "Loggout")
                    }
                }

            }
        }

    }
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAuthScreen() {
    CheckListTheme {
        MyAuthScreen()
    }
}