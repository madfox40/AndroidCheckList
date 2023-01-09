package com.example.checklist

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
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
import com.example.checklist.Navigation.AppNavigation
import com.example.checklist.Screens.MyAuthScreen
import com.example.checklist.Screens.MyCheckList
import com.example.checklist.ui.theme.CheckListTheme


class MainActivity : AppCompatActivity() {
    val data = mutableListOf<MyItems>(
        MyItems(
            name = "Hola",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum volutpat leo vel nisi luctus, a fringilla nisl pulvinar. Praesent varius, nibh sed placerat sodales, ante sem sodales ipsum, at dignissim arcu urna non est. Aenean in rutrum diam. Sed nec mollis felis. In in tempus orci. Ut non aliquet nisl."
        ),
        MyItems(
            name = "Muy Buenas",
            body = "Praesent quis sagittis nisi, at euismod metus. Nunc faucibus lectus erat, quis porta lectus tincidunt eu. Nullam luctus risus eget viverra ultricies. Maecenas congue, eros ac bibendum placerat, nibh diam semper elit, at sagittis tortor justo id velit. Integer vel est vel felis varius placerat. Etiam sollicitudin arcu diam, non auctor lectus ullamcorper sed."
        ),
        MyItems(name = "Hola", body = "Esto es una prueba jajaj saludos"),
        MyItems(
            name = "Que tal",
            body = "Nullam sed posuere dolor, at vehicula felis. Aliquam erat volutpat. Duis semper, tellus sed cursus imperdiet, mi mi suscipit eros, a pulvinar leo purus at tortor. Vivamus ac imperdiet leo. Aliquam sollicitudin luctus tellus, vitae tempus nibh elementum et. Integer sit amet mollis purus. In hac habitasse platea dictumst."
        ),
        MyItems(name = "Caracola", body = "Esto es una prueg"),
        MyItems(name = "Pepito", body = "A ver a ver"),
        MyItems(name = "Adrianita", body = "A ver a ver"),
        MyItems(name = "Adrianita", body = "A ver a ver")
    ).toMutableStateList()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            //MyAuthScreen()
            //Auth()
            //AppNavigation()
            CheckListTheme {
                AppNavigation()
                //MyCheckList()
                //MyAuthScreen()
            }
        }
        //Setup
        //setupAuth()
    }


    data class MyItems(val name: String, val body: String)






    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview(){
        AppNavigation()
    }
}
