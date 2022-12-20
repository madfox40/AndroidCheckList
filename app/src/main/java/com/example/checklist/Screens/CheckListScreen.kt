package com.example.checklist.Screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.checklist.Data.CheckListItem
import com.example.checklist.Data.NotCheckedArguments
import com.example.checklist.MainActivity
import com.example.checklist.ui.theme.CheckListTheme

private fun getCheckdArguments() = List(1) { i -> CheckListItem("", false) }
var data = getCheckdArguments().toMutableStateList()


@Composable
fun MyCheckListItem(label: String, onClose: () -> Unit) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    MyCheckListItem(
        label = label,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose,
    )
}

@Composable
fun MyCheckListItem(
    label: String,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(start = 8.dp, bottom = 0.dp, top = 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //MyImage()
        Checkbox(checked = checked, onCheckedChange)

        var text by rememberSaveable {
            mutableStateOf(label)
        }
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White, textColor = MaterialTheme.colors.primary,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = text, onValueChange = { text = it },
            textStyle = MaterialTheme.typography.subtitle1
        )
        //MyText(text = label, color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Composable
fun MyCheckList(
    checkItems: SnapshotStateList<CheckListItem> = remember { data }
) {
    val deletedItems = remember { mutableStateListOf<Int>() }
    Scaffold(
        /*floatingActionButton = {
            FloatingActionButton(onClick = { data.add(CheckListItem("", false)) }) {

            }
        },
        isFloatingActionButtonDocked = false,*/
        topBar = {
            TopAppBar { /* Top app bar content */ }
        }
    ) {
        // Screen content
        Column() {
            LazyColumn(
                Modifier
                    .padding(bottom = 0.dp, start = 0.dp, end = 0.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )
            )
            {
                items(
                    items = checkItems,
                    itemContent = { checkItem ->
                        AnimatedVisibility(
                            visible = !(System.identityHashCode(checkItem) in deletedItems),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            MyCheckListItem(
                                label = checkItem.name,
                                onClose = {
                                    deletedItems.add(System.identityHashCode(checkItem))
                                    checkItem.deleted = true
                                })
                        }
                    }
                )
                item() {
                    Button(modifier = Modifier.padding(8.dp),onClick = { checkItems.add(CheckListItem(name = "", deleted = false)) }) {
                        Row() {
                            Icon(Icons.Filled.Add, contentDescription = "Close", tint = MaterialTheme.colors.primaryVariant)
                            Spacer(modifier = Modifier.padding(4.dp))

                            Text(text = "Add New Item")
                        }
                    }
                }

               /* items(checkItems) { checkItem ->
                    Text("prueba")
                    MyCheckListItem(
                        label = checkItem.name,
                        onClose = { checkItems.remove(checkItem) })
                }*/
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewText() {
    CheckListTheme() {
        MyCheckList()
    }
}