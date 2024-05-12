package com.beapps.thedoctorapp.content.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM notes WHERE byDoctorId = :doctorId AND toPatientId = :patientId")
    fun getAllNotes (doctorId: String, patientId: String): Flow<List<NoteEntity>>
}