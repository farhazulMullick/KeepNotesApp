package com.example.keepnotes.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class Repository(private val dao: NotesDao) {

    val allData: LiveData<List<Notes>> = dao.allData()

    suspend fun insert(notes: Notes){
        dao.insert(notes)
    }

    suspend fun delete(notes: Notes){
        dao.deleteItem(notes)
    }

    suspend fun update(notes: Notes){
        dao.update(notes)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    fun searchNotes(searchQuery: String): Flow<List<Notes>> {
        return dao.searchNotes(searchQuery)
    }

}