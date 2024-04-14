package com.beapps.thedoctorapp.content.data.firebase

import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.getPatientFromFolderName
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseContentManager : ContentManager {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    override suspend fun displayPatients(doctorId: String): Result<List<Patient>, Error.GetContentErrors> {
        val doctorChildRef = storageRef.child(doctorId)
        return try {
            val foldersName = doctorChildRef.listAll().await().prefixes.map {
                it.name
            }
            val patients = foldersName.map { folderName ->
                folderName.getPatientFromFolderName()
            }
            if (patients.isEmpty()) {
                return Result.Error(Error.GetContentErrors.EmptyContent)
            }
            Result.Success(patients)
        } catch (e: Exception) {
            Result.Error(
                Error.GetContentErrors.Others(
                    e.message
                )
            )
        }

    }

    override suspend fun getPatientContent(
        doctorId: String,
        patientFolderName: String
    ): Result<List<PatientContent>, Error.GetContentErrors> {
        return try {
            val doctorChildRef = storageRef.child(doctorId)
            val patientChildRef = doctorChildRef.child(patientFolderName)
            val files = patientChildRef.listAll().await().items
            val patientContents = files.map { file ->
                val name = file.name
                val metadata = file.metadata.await()
                val patientContent = metadata.toPatientContent().copy(name = name)
                patientContent
            }
            Result.Success(patientContents)
        } catch (e: Exception) {
            Result.Error(Error.GetContentErrors.Others(message = e.message))
        }
        // Use metadata as needed
    }
}