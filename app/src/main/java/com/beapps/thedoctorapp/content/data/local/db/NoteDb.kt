package com.beapps.thedoctorapp.content.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NoteEntity::class] , version = 1)
abstract class NoteDb : RoomDatabase(){

    abstract val noteDao : NoteDao

    companion object{
        const val DATABASE_NAME = "note_db"
    }

}