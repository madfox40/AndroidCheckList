package com.example.checklist.Data

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.checklist.MainActivity



data class CheckList (var chekedArguments: CheckedArguments, var notCheckedArguments: NotCheckedArguments){
}

data class CheckListItem (var name: String){

}

data class CheckedArguments(var checkedArguments: SnapshotStateList<CheckListItem>){

}



data class NotCheckedArguments(var notCheckedArguments: SnapshotStateList<CheckListItem>){

}