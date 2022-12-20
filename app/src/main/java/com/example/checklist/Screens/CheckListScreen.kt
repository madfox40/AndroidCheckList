package com.example.checklist.Screens

import android.content.res.Configuration
import android.os.Debug
import android.util.Log
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checklist.Data.CheckList
import com.example.checklist.Data.CheckListItem
import com.example.checklist.ui.theme.CheckListTheme
import com.example.checklist.Data.data
import kotlin.math.exp


@Composable
fun MyCheckListItem(
    checListkItem: CheckListItem,
    onClose: () -> Unit,
    checkedState: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by rememberSaveable { mutableStateOf(checkedState) }

    MyCheckListItem(
        checListkItem = checListkItem,
        checked = checkedState,
        onCheckedChange = onCheckedChange,
        onClose = onClose,
    )
}


@Composable
fun MyCheckListItem(
    checListkItem: CheckListItem,
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
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)



        TextField(
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent, textColor = MaterialTheme.colors.primary,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = checListkItem.name, onValueChange = {
                data.editNameComposable(checListkItem, it)
            },
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
fun CheckedItemsComponent() {


}

@Composable
fun MyCheckList(
    checkItems: SnapshotStateList<CheckListItem> = remember { data.notCheckedItems },
    checkedItems: SnapshotStateList<CheckListItem> = remember { data.chekedItems },
) {
    val deletedItems = remember { mutableStateListOf<Int>() }
    Scaffold(
        /*floatingActionButton = {
            FloatingActionButton(onClick = { data.add(CheckListItem("", false)) }) {

            }
        },
        isFloatingActionButtonDocked = false,*/
        topBar = {
            TopAppBar {
                var text by remember {
                    mutableStateOf("CheckList")
                }
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.secondary,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    value = text,
                    textStyle = MaterialTheme.typography.subtitle1,
                    onValueChange = { text = it }
                )
                IconButton(onClick = { data.removeDeleted() }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Click to refresh", modifier = Modifier.weight(1f))
                }
            }
        }
    ) {
        // Screen content
        Column() {
            var expanded by remember {
                mutableStateOf(true)
            }
            LazyColumn(
                Modifier
                    .padding(bottom = 0.dp, start = 0.dp, end = 0.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                /*.animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )*/
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
                            var checkedState by rememberSaveable {
                                mutableStateOf(false)
                            }
                            MyCheckListItem(
                                checListkItem = checkItem,
                                onCheckedChange = { newValue ->
                                    //data.removeDeleted()
                                    data.addChecked(checkItem)
                                    data.removeNotChecked(checkItem)
                                    data.showLists()
                                    checkedState = newValue
                                },
                                checkedState = checkedState,
                                onClose = {
                                    deletedItems.add(System.identityHashCode(checkItem))
                                    checkItem.deleted = true
                                },
                            )
                        }
                    }

                )
                item {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = { checkItems.add(CheckListItem(name = "", deleted = false)) }) {
                        Row() {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primaryVariant
                            )
                            Spacer(modifier = Modifier.padding(4.dp))

                            Text(text = "Add New Item")
                        }
                    }

                    Column(modifier = Modifier.padding(start = 10.dp)) {

                        IconButton(
                            modifier = Modifier.padding(0.dp),
                            onClick = { expanded = !expanded }) {
                            Row(
                                modifier = Modifier.padding(0.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier.padding(0.dp),
                                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                    contentDescription = "See checked items or not",
                                    tint = MaterialTheme.colors.secondary,
                                )
                                Text(
                                    modifier = Modifier.padding(start = 2.dp),
                                    text = "CheckedItems",
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 20.sp
                                )
                            }
                        }


                    }

                }
                if (expanded) {
                    items(
                        items = checkedItems,
                        itemContent = { checkedItem ->
                            AnimatedVisibility(
                                visible = !(System.identityHashCode(checkedItem) in deletedItems),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                var checkedState by rememberSaveable {
                                    mutableStateOf(true)
                                }
                                var name by remember {
                                    mutableStateOf(checkedItem.name)
                                }
                                MyCheckListItem(
                                    checListkItem = checkedItem,
                                    onCheckedChange = { newValue ->
                                        data.removeChecked(checkedItem)
                                        data.addNotChecked(checkedItem)
                                        data.showLists()
                                        checkedState = newValue
                                    },
                                    checkedState = checkedState,
                                    onClose = {
                                        deletedItems.add(System.identityHashCode(checkedItem))
                                        checkedItem.deleted = true
                                    })
                            }
                        }
                    )
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