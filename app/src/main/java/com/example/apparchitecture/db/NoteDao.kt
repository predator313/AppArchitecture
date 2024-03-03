package com.example.apparchitecture.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apparchitecture.db.table.Note
import javax.inject.Singleton

@Dao
interface NoteDao {
    //dao should be interface or abstract
    //we just create a method or annotate it
    //room is take cares of it automatically

    //there should be one dao or one entity (good practice)


    @Insert   //here insert means room will automatically handle it we need not to handle it
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)


    @Query("delete from note_table")  //this will delete  whole table
    suspend fun deleteAllNotes()

    @Query("select * from note_table order by priority desc")
    fun getAllNotes():LiveData<List<Note>>

}