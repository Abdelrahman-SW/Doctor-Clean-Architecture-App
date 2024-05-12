package com.beapps.thedoctorapp.content.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val note : String,
    val createdAt : Long = System.currentTimeMillis(),
    val byDoctorId : String ,
    val toPatientId : String
)
