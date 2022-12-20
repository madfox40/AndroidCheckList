package com.example.checklist.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.checklist.MainActivity

class OtherComponents {
    @Composable
    fun MyTexts(checkItem: MainActivity.MyItems) {

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
}

@Composable
fun MyText(
    text: String,
    color: Color,
    style: androidx.compose.ui.text.TextStyle,
    lines:Int= Int.MAX_VALUE,
    modifier:Modifier = Modifier) {

    Text(modifier = modifier,
        text = text,
        color = color,
        style = style,
        maxLines = lines
    )
}

/*@Composable
fun MyImage() {
    Image(
        painterResource(R.drawable.ic_launcher_foreground),
        "Mi imagen de prueba",
        modifier = Modifier
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.primary)
            .size(64.dp)
    )
}*/