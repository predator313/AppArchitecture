package com.example.apparchitecture.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apparchitecture.db.table.Note
import com.example.apparchitecture.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>>get() = _allNotes

    init {
        repository.getAllNotes().observeForever { notes ->
            _allNotes.value = notes
        }
    }

    fun insertNotes(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun updateNotes(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.getAllNotes().removeObserver { }
    }

}
