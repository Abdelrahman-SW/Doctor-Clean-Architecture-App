package com.beapps.thedoctorapp.content.data.remote.firebase

import com.beapps.thedoctorapp.content.data.remote.firebase.dto.PatientDto
import com.beapps.thedoctorapp.content.data.remote.firebase.dto.toPatient
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class ContentManagerFirebaseImpl : ContentManager {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val patientsCollectionName = "Patients"
    private val patientsFirestoreDb = FirebaseFirestore.getInstance()

    override suspend fun getPatients(doctorId: String): Result<List<Patient>, Error.GetContentErrors> {
        val doctorChildRef = storageRef.child(doctorId)
        return try {
            val patients = mutableListOf<Patient>()

            val foldersName = doctorChildRef.listAll().await().prefixes.map {
                it.name
            }
            if (foldersName.isEmpty()) {
                return Result.Error(Error.GetContentErrors.EmptyContent)
            }

//            val patientsIds = foldersName.map { folderName ->
//                folderName.getPatientIdFromFolderName()
//            }

            val patientsIds = foldersName
            // get patient list from ids
            val result = patientsFirestoreDb.collection(patientsCollectionName).whereIn(
                FieldPath.documentId(), patientsIds
            ).get().await()

            if (result.isEmpty) {
                return Result.Error(Error.GetContentErrors.EmptyContent)
            }
            for (document in result.documents) {
                val patientDto = document.toObject<PatientDto>() ?: continue
                patients.add(patientDto.toPatient().copy(id = document.id , assignedDoctorId = doctorId))
            }
            return Result.Success(data = patients)
        } catch (e: Exception) {
            Result.Error(
                Error.GetContentErrors.Others(
                    e.message
                )
            )
        }
    }

    override suspend fun getPatientFiles(
        patient: Patient
    ): Result<List<PatientFile>, Error.GetContentErrors> {
        return try {
            val doctorChildRef = storageRef.child(patient.assignedDoctorId)
            val patientChildRef = doctorChildRef.child(patient.id)
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