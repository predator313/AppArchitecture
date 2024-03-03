package com.example.apparchitecture.repository

import androidx.lifecycle.LiveData
import com.example.apparchitecture.db.NoteDao
import com.example.apparchitecture.db.table.Note
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class NoteRepository (private val noteDao: NoteDao) {

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}