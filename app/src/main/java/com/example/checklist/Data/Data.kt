package com.example.checklist.Data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.Keep
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.checklist.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*


val db = FirebaseFirestore.getInstance()

fun updateCheckList(id: String, context: Context? = null) {
    db.collection("checkLists").document(id).collection("checked")
        .get()
        .addOnSuccessListener { result ->
            db.collection("checkLists").document(id).collection("notChecked")
                .get()
                .addOnSuccessListener { result2 ->
                    data.deleteData()
                    for (checkedItem in result) {
                        data.addChecked(checkedItem.toObject(CheckListItem::class.java))
                    }
                    for (checkedItem in result2) {
                        data.addNotChecked(checkedItem.toObject(CheckListItem::class.java))
                    }
                    Toast.makeText(context, "Data loaded successfully", Toast.LENGTH_SHORT).show();
                }
        }.addOnFailureListener {
            Toast.makeText(context, "Some error loading the data", Toast.LENGTH_SHORT).show()
        }
}


fun saveCheckList(id: String, chekList: CheckList, context: Context? = null) {
    Log.d("hola", chekList.toString())
    db.collection("checkLists").document(id).collection("checked")
    var number_to_save = chekList.chekedItems.orEmpty().size + chekList.notCheckedItems.orEmpty().size
    var saved = 0
    for (checkedItem: CheckListItem in chekList.chekedItems.orEmpty()) {
        if (checkedItem.deleted == true) {
            db.collection("checkLists").document(id).collection("checked")
                .document(checkedItem.id).delete().addOnSuccessListener {
                    saved = saved +1
                    if (saved == number_to_save){
                        Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            db.collection("checkLists").document(id).collection("checked")
                .document(checkedItem.id).set(checkedItem).addOnSuccessListener {
                    saved = saved +1
                    if (saved == number_to_save){
                        Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    for (notCheckedItem: CheckListItem in chekList.notCheckedItems.orEmpty()) {
        if (notCheckedItem.deleted == true) {
            db.collection("checkLists").document(id).collection("notChecked")
                .document(notCheckedItem.id).delete().addOnSuccessListener {
                    saved = saved +1
                    if (saved == number_to_save){
                        Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            db.collection("checkLists").document(id).collection("notChecked")
                .document(notCheckedItem.id).set(notCheckedItem).addOnSuccessListener {
                    saved = saved +1
                    if (saved == number_to_save){
                        Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

fun generateRandomId(): String {
    val random = Random()
    val randomInt = random.nextInt(100000000)
    var generatedId = randomInt.toString().padStart(8, '0')
    //Log.d("idGenerado", generatedId)
    return generatedId
}

data class CheckList(
    var id: String? = null,
    var chekedItems: SnapshotStateList<CheckListItem>? = getCheckedItems().toMutableStateList()
        .toMutableStateList(),
    var notCheckedItems: SnapshotStateList<CheckListItem>? = getCheckedItems().toMutableStateList()
        .toMutableStateList(),
    var usedIds: MutableList<String> = mutableListOf()
) {
    //constructor() : this(
    //   id = "",
    //   chekedItems = MutableList(0) { i -> CheckListItem("", false) }.toMutableStateList(),
    //   notCheckedItems = MutableList(0) { i -> CheckListItem("", false) }.toMutableStateList()
    //)

    fun deleteData() {
        chekedItems!!.clear()
        notCheckedItems!!.clear()
    }

    fun editNameComposable(id: String, name: String) {
        var index = 0
        for (i in chekedItems.orEmpty()) {
            if (chekedItems!![index].id == id
            ) {
                Log.d("Hola", "LA ide es " + chekedItems!![index].id)
                chekedItems!![index] = CheckListItem(id = id, name = name, deleted = false)
                //chekedItems.set(index = index, element = CheckListItem(name = name, deleted = false))
                break
            }
            index += 1
        }

        index = 0
        for (i in notCheckedItems.orEmpty()) {
            if (notCheckedItems!![index].id == id

            ) {
                notCheckedItems!![index] = CheckListItem(name = name, id = id, deleted = false)
                //notCheckedItems.set(index = index, element = CheckListItem(name = name, deleted = false))

                break
            }
            index += 1
        }

    }

    fun searchItemInNotCheckedItems(id:String):Boolean {
        var finded = false
        for (i in notCheckedItems.orEmpty()) {
            if (i.id == id){
                return true
            }
        }
        return false
    }

    fun searchItemInCheckedItems(id:String):Boolean {
        var finded = false
        for (i in chekedItems.orEmpty()) {
            if (i.id == id){
                return true
            }
        }
        return false
    }

    fun addNewItem(checkListItem: CheckListItem) {
        var randomId = generateRandomId()
        notCheckedItems?.add(checkListItem)
        while (usedIds.contains(randomId)) {
            randomId = generateRandomId()
        }
        Log.d("gole", "Item añadido" + checkListItem.id)
        checkListItem.id = randomId
        usedIds.add(randomId)
        data.showLists()
    }

    fun showLists() {
        var checkedList = "CHECKED:"
        var notCheckedList = "NOT CHECKED:"
        for (i in notCheckedItems.orEmpty()) {
            var name = i.name
            var id = i.id
            notCheckedList = notCheckedList + " ($id,$name),"
        }
        for (i in chekedItems.orEmpty()) {
            var name = i.name
            var id = i.id
            checkedList = checkedList + " ($id,$name),"
        }
        Log.d("Hola", notCheckedList)
        Log.d("Hola", checkedList)
    }

    fun removeDeleted() {
        for (i in chekedItems.orEmpty()) {
            if (i.deleted!!) {
                chekedItems?.remove(i)
            }
        }
        for (i in notCheckedItems.orEmpty()) {
            if (i.deleted!!) {
                notCheckedItems?.remove(i)
            }
        }
    }

    fun addChecked(checkListItem: CheckListItem) {
        chekedItems!!.removeIf{it.id == checkListItem.id}
        var newItem = CheckListItem(id = checkListItem.id, name = checkListItem.name, deleted = false )
        chekedItems?.add(newItem)
    }

    fun removeChecked(checkListItem: CheckListItem) {
        chekedItems!!.removeIf{it.id == checkListItem.id}
        var newItem = CheckListItem(id = checkListItem.id, name = "deletedItem", deleted = true )
        chekedItems?.add(newItem)
        //chekedItems.remove(checkListItem)

    }

    fun addNotChecked(checkListItem: CheckListItem) {
        notCheckedItems!!.removeIf{it.id == checkListItem.id}
        var newItem = CheckListItem(id = checkListItem.id, name = checkListItem.name, deleted = false )
        notCheckedItems?.add(newItem)
    }

    fun removeNotChecked(checkListItem: CheckListItem) {
        notCheckedItems!!.removeIf{it.id == checkListItem.id}
        var newItem = CheckListItem(id = checkListItem.id, name = "deletedItem", deleted = true )
        notCheckedItems?.add(newItem)
        //notCheckedItems.remove(checkListItem)
    }

}

data class CheckListItem(
    var name: String? = "",
    var deleted: Boolean? = false,
    var id: String = ""
) {

}


data class CheckedArguments(var checkedArguments: SnapshotStateList<CheckListItem>) {

}


data class NotCheckedArguments(var notCheckedArguments: SnapshotStateList<CheckListItem>) {

}


private fun getCheckedItems() = List(0) { i -> CheckListItem("", false) }

//var notCheckedArguments = NotCheckedArguments(getCheckdArguments().toMutableStateList())
//var checkedArguments = CheckedArguments(SnapshotStateList<CheckListItem>())
var data = CheckList(
    id = "checkList",
    notCheckedItems = getCheckedItems().toMutableStateList(),
    chekedItems = SnapshotStateList<CheckListItem>(),
)


