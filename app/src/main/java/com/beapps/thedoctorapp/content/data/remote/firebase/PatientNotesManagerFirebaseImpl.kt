package com.beapps.thedoctorapp.content.data.remote.firebase

import android.content.Context
import com.beapps.thedoctorapp.content.data.remote.firebase.dto.PatientNoteDto
import com.beapps.thedoctorapp.content.domain.PatientNotesManager
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class PatientNotesManagerFirebaseImpl(val context: Context) : PatientNotesManager {

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val notesPathName = "Doctor Notes"
    override suspend fun getPatientNotes(
        patientId: String,
        doctorId: String
    ): Result<List<PatientNote>, Error.ManageNotesErrors> {
        return try {
            val notesFolderPath =
                storageRef.child(doctorId).child(patientId).child(notesPathName)
            val notes = notesFolderPath.listAll().await().items
            val notesDto = notes.map {
                PatientNoteDto(
                    id = it.name,
                    note = "",
                    createdAt = it.metadata.await().getCustomMetadata("createdAt")
                        ?: System.currentTimeMillis().toString(),
                    byDoctorId = doctorId,
                    toPatientId = patientId
                )
            }
            val patientNotes = notesDto.map {
                it.toPatientNote()
            }
            Result.Success(patientNotes)
        } catch (e: Exception) {
            Result.Error(Error.ManageNotesErrors.Others(e.message))
        }
    }

    override suspend fun getNoteContent(note: PatientNote): Result<String, Error.ManageNotesErrors> {
        val noteDto = note.toPatientNoteDto()
        val notesFolderPath =
            storageRef.child(noteDto.byDoctorId).child(noteDto.toPatientId).child(notesPathName)
                .child(noteDto.id)
        return try {
            val content = extractTextFileContent(notesFolderPath)
            Result.Success(content)
        } catch (e: Exception) {
            Result.Error(Error.ManageNotesErrors.Others(e.message))
        }
    }

    override suspend fun insertNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors> {

        if (ifNoteTitleAlreadyExists(note)) {
            return Result.Error(Error.ManageNotesErrors.TitleAlreadyExists)
        }
        val noteDto = note.toPatientNoteDto()
        val notesFolderPath =
            storageRef.child(noteDto.byDoctorId).child(noteDto.toPatientId).child(notesPathName)
                .child(noteDto.id)
        val metadata = StorageMetadata.Builder()
            .setCustomMetadata("createdAt", System.currentTimeMillis().toString())
            .setContentType("text/plain")
            .build()
        val result =
            notesFolderPath.putBytes(noteDto.note.toByteArray(Charsets.UTF_8), metadata).await()
        return if (result.task.isSuccessful) {
            return getPatientNotes(noteDto.toPatientId, noteDto.byDoctorId)
        } else {
            Result.Error(Error.ManageNotesErrors.Others(result.task.exception?.message))
        }
    }

    private suspend fun ifNoteTitleAlreadyExists(note: PatientNote): Boolean {
        val notesFolderPath =
            storageRef.child(note.byDoctorId).child(note.toPatientId).child(notesPathName)
        val notesTitles = notesFolderPath.listAll().await().items.map {
            it.name
        }
        val noteDto = note.toPatientNoteDto()
        return (notesTitles.contains(noteDto.id))
    }

    override suspend fun updateNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors> {
        val noteDto = note.toPatientNoteDto()
        val notesFolderPath =
            storageRef.child(noteDto.byDoctorId).child(noteDto.toPatientId).child(notesPathName)
                .child(noteDto.id)
        val metadata = StorageMetadata.Builder()
            .setCustomMetadata("createdAt", noteDto.createdAt)
            .setContentType("text/plain")
            .build()
        val result =
            notesFolderPath.putBytes(noteDto.note.toByteArray(Charsets.UTF_8), metadata).await()
        return if (result.task.isSuccessful) {
            return getPatientNotes(note.toPatientId, note.byDoctorId)
        } else {
            Result.Error(Error.ManageNotesErrors.Others(result.task.exception?.message))
        }
    }

    override suspend fun deleteNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors> {
        val noteDto = note.toPatientNoteDto()
        val notesFolderPath =
            storageRef.child(noteDto.byDoctorId).child(noteDto.toPatientId).child(notesPathName)
                .child(noteDto.id)
        return try {
            notesFolderPath.delete().await()
            getPatientNotes(noteDto.toPatientId, noteDto.byDoctorId)
        } catch (e: Exception) {
            Result.Error(Error.ManageNotesErrors.Others(e.message))
        }

    }

    private suspend fun extractTextFileContent(fileRef: StorageReference): String {
        return withContext(Dispatchers.IO) {
            val taskSnapshot = fileRef.stream.await()
            try {
                // Read the file content from the InputStream
                val inputStream: InputStream? = taskSnapshot?.stream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                // Read each line and append it to the StringBuilder
                var readerLine = reader.readLine()
                while (readerLine != null) {
                    stringBuilder.append(readerLine).append("\n")
                    readerLine = reader.readLine()
                }
                // Close the reader
                reader.close()
                stringBuilder.toString().trim()
            } catch (e: Exception) {
                throw e
            }
        }
    }

}