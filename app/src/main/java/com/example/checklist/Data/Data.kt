package com.example.checklist.Data

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.checklist.MainActivity



data class CheckList (var chekedItems: SnapshotStateList<CheckListItem>, var notCheckedItems: SnapshotStateList<CheckListItem>){

        fun editNameComposable(checkListItem: CheckListItem, name: String){
            var index = 0
            for (i in chekedItems) {
                if (System.identityHashCode(chekedItems[index]) == System.identityHashCode(checkListItem)) {
                    chekedItems[index] = CheckListItem(name = name, deleted = false)
                    //chekedItems.set(index = index, element = CheckListItem(name = name, deleted = false))
                    break
                }
                index += 1
            }

            index = 0
            for (i in notCheckedItems) {
                if (System.identityHashCode(notCheckedItems[index]) == System.identityHashCode(checkListItem)) {
                    notCheckedItems[index] = CheckListItem(name = name, deleted = false)
                    //notCheckedItems.set(index = index, element = CheckListItem(name = name, deleted = false))

                    break
                }
                index += 1
            }

        }

        fun showLists(){
            var checkedList = "CHECKED:"
            var notCheckedList = "NOT CHECKED:"
            for (i in chekedItems) {
                var name = i.name
                checkedList = checkedList + " ($name),"
            }
            for (i in notCheckedItems) {
                var name = i.name
                notCheckedList = notCheckedList + " ($name),"
            }
            Log.d("Hola", checkedList)
            Log.d("Hola", notCheckedList)
        }

        fun removeDeleted(){
            for (i in chekedItems) {
                if (i.deleted) {
                    chekedItems.remove(i)
                }
            }
            for (i in notCheckedItems) {
                if (i.deleted) {
                    notCheckedItems.remove(i)
                }
            }
        }

        fun addChecked(checkListItem: CheckListItem){
            chekedItems.add(checkListItem)
        }

        fun removeChecked(checkListItem: CheckListItem){
            chekedItems.removeIf {item -> System.identityHashCode(item) == System.identityHashCode(checkListItem)}
            //chekedItems.remove(checkListItem)

        }

        fun addNotChecked(checkListItem: CheckListItem){
            notCheckedItems.add(checkListItem)
        }

        fun removeNotChecked(checkListItem: CheckListItem){
            notCheckedItems.removeIf {item -> System.identityHashCode(item) == System.identityHashCode(checkListItem)}
            //notCheckedItems.remove(checkListItem)
        }

}

data class CheckListItem (val name: String, var deleted: Boolean){

}

data class CheckedArguments(var checkedArguments: SnapshotStateList<CheckListItem>){

}



data class NotCheckedArguments(var notCheckedArguments: SnapshotStateList<CheckListItem>){

}


private fun getCheckedItems() = List(1) { i -> CheckListItem("", false) }
//var notCheckedArguments = NotCheckedArguments(getCheckdArguments().toMutableStateList())
//var checkedArguments = CheckedArguments(SnapshotStateList<CheckListItem>())
var data = CheckList(
    notCheckedItems = getCheckedItems().toMutableStateList(),
    chekedItems = SnapshotStateList<CheckListItem>(),
)
