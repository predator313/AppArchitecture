package com.example.apparchitecture.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    val title:String,
    val desc:String,
    val priority:Int,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null

)
