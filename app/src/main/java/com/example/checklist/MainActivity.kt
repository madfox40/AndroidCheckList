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

            //Auth()
            //AppNavigation()
            CheckListTheme {

                MyCheckList()
            }
        }
        //Setup
        setupAuth()
    }

    private  var canAuthenticate = false
    private lateinit var promptInfo:BiometricPrompt.PromptInfo

    private fun setupAuth(){
        if (BiometricManager.from(this).canAuthenticate(Authenticators.BIOMETRIC_STRONG
            or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS){
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Autenticate utilizando el sensor biométrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG
                or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    private fun authenticate(auth: (auth:Boolean) ->  Unit) {

        if (canAuthenticate) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
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

    data class MyItems(val name: String, val body: String)



    @Composable
    fun MyCheckListItem(label: String, onClose: () -> Unit) {
        var checkedState by rememberSaveable { mutableStateOf(false) }

        MyCheckListItem(
            label = label,
            checked = checkedState,
            onCheckedChange = { newValue -> checkedState = newValue },
            onClose = onClose
        )
    }

    @Composable
    fun MyCheckListItem(
        label: String,
        checked:Boolean = false,
        onCheckedChange: (Boolean) -> Unit = {},
        onClose: () -> Unit ,
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //MyImage()
            Checkbox(checked = checked, onCheckedChange)

            var text by rememberSaveable {
                mutableStateOf(label)
            }
            TextField(colors= TextFieldDefaults.textFieldColors(
                backgroundColor= Color.White, textColor = MaterialTheme.colors.primary,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
                value = text, onValueChange = {text = it},
                textStyle = MaterialTheme.typography.subtitle1)
            //MyText(text = label, color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1)
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = MaterialTheme.colors.primaryVariant)
                }
            }


            //MyTexts(checkItem)

        }
    }


    @Composable
    fun MyTexts(checkItem: MyItems) {

        var expanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .padding(start = 8.dp)
            .clickable {
                expanded = !expanded
            }) {

            MyText(text = checkItem.name, color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.padding(8.dp))
            MyText(
                text = checkItem.body,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle1,
                lines = if (expanded) Int.MAX_VALUE else 1
                )
        }
    }

    @Composable
    fun MyText(
        text: String,
        color: Color,
        style: androidx.compose.ui.text.TextStyle,
        lines:Int= Int.MAX_VALUE ,
        modifier:Modifier = Modifier) {

        Text(modifier = modifier,
            text = text,
            color = color,
            style = style,
            maxLines = lines
        )
    }

    @Composable
    fun MyImage() {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            "Mi imagen de prueba",
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.primary)
                .size(64.dp)
        )
    }

    @Composable
    fun MyBottomAppBar () {

    }

    @Composable
    fun MytopAppBar(){

    }

    @Composable
    fun MyCheckList(
        checkItems: SnapshotStateList<MyItems> = remember { data }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { data.add(MyItems("Hola","adios"))}) {

                }
            },
            isFloatingActionButtonDocked = false,
            topBar = {
                TopAppBar { /* Top app bar content */ }
            }
        ) {
            // Screen content
            Column() {
                LazyColumn(Modifier.padding(bottom = 0.dp))
                {
                    items(checkItems) { checkItem ->
                        MyCheckListItem(label = checkItem.name, onClose = {checkItems.remove(checkItem)})
                    }
                }
                /*Button(modifier = Modifier.padding(8.dp),onClick = { checkItems.add(MyItems(name = "hola", body = "adios")) }) {
                    Row() {
                        Icon(Icons.Filled.Add, contentDescription = "Close", tint = MaterialTheme.colors.primaryVariant)
                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(text = "Add New Item")
                    }
                }*/
            }

                //.padding(8.dp)

        }
    }

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
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewText() {
        var data = listOf<MyItems>(
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

            )

        CheckListTheme() {
            MyCheckList()
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreviewAuth(){
        Auth()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview(){
        AppNavigation()
    }
}
