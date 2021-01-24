package com.example.keepnotes.database

import androidx.lifecycle.LiveData

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
}