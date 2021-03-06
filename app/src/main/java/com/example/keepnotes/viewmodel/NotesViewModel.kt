package com.example.keepnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.database.Notes
import com.example.keepnotes.database.NotesDao
import com.example.keepnotes.database.NotesDatabase
import com.example.keepnotes.database.Repository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val notesDao: NotesDao by lazy{
        NotesDatabase.getDatabase(application).notesDao()
    }
    private val notesRepository: Repository

    val getAllNotes: LiveData<List<Notes>>


    init {
        notesRepository = Repository(notesDao)
        getAllNotes = notesRepository.allData
    }

    // To search Notes From Database
    fun searchDatabase(searchQuery: String): LiveData<List<Notes>>{
        return notesRepository.searchNotes(searchQuery).asLiveData()
    }

     fun insertData(notes: Notes){
        viewModelScope.launch(IO) {
            notesRepository.insert(notes)
        }
    }

     fun updateData(notes: Notes){
         viewModelScope.launch(IO) {
             notesRepository.update(notes)
         }
     }

    fun deleteItem(notes: Notes){
        viewModelScope.launch(IO) {
            notesRepository.delete(notes)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(IO) {
            notesRepository.deleteAll()
        }
    }


}