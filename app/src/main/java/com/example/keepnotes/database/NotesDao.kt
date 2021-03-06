package com.example.keepnotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun allData(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Notes)

    @Delete()
    suspend fun deleteItem(notes: Notes)

    @Update()
    suspend fun update(notes: Notes)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery ")
    fun searchNotes(searchQuery: String): Flow<List<Notes>>
}