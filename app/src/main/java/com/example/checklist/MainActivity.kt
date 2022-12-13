package com.example.checklist

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.checklist.ui.theme.CheckListTheme
import java.sql.DatabaseMetaData
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                MyItems(name = "Adrianita", body = "A ver a ver")
            )

            CheckListTheme() {
                MyComposition(checkItems = data)
            }
        }
    }


    data class MyItems(val name: String, val body: String)

    @Composable
    fun MyComponent(checkItem: MyItems) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            MyImage()
            MyTexts(checkItem)
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
    fun MyComposition(checkItems: List<MyItems>) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {

                }
            },
            isFloatingActionButtonDocked = false,
            topBar = {
                TopAppBar { /* Top app bar content */ }
            }
        ) {
            // Screen content
            LazyColumn()
            {
                items(checkItems) { checkItem ->
                    MyComponent(checkItem = checkItem)
                }
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
            MyComposition(checkItems = data)
        }
    }
}
