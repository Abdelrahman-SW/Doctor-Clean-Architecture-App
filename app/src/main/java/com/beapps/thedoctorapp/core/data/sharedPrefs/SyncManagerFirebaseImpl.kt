package com.beapps.thedoctorapp.core.data.sharedPrefs

import android.util.Log
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.GraphManager
import com.beapps.thedoctorapp.core.domain.GraphPoint
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.core.domain.SyncManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class SyncManagerFirebaseImpl @Inject constructor(
    private val graphManager: GraphManager<String, Float>
) : SyncManager {

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    override suspend fun syncGraphsForDoctorPatients(doctor: Doctor): Result<Unit, Error.SyncingErrors.SyncingPatientsGraphsErrors> {
        return try {
            val doctorRef = storageRef.child(doctor.id)
            val patientFolders = doctorRef.listAll().await().prefixes
            if (patientFolders.isEmpty()) {
                return Result.Error(Error.SyncingErrors.SyncingPatientsGraphsErrors.EmptyPatients)
            }
            for (patientFolder in patientFolders) {
                val patientId = patientFolder.name
                val patientFolderRef = doctorRef.child(patientId)
                val movementsRef = patientFolderRef.child("Movements")
                val movementsFolders = movementsRef.listAll().await().prefixes
                if (movementsFolders.isEmpty()) continue
                for (movementFolder in movementsFolders) {
                    val movementFolderRef = movementsRef.child(movementFolder.name)
                    val textFilesForMovement = movementFolderRef.listAll()
                        .await().items.filter { it.name.endsWith(".txt") }
                    if (textFilesForMovement.isEmpty()) continue
                    val graphPoints: List<GraphPoint<String, Float>> =
                        constructGraphPoints(textFilesForMovement)
                    val image = graphManager.drawGraph(
                        points = graphPoints,
                        yLabelCounts = 10,
                        descriptionText = "Movement${movementFolder.name} Graph",
                        lineLabel = "Movement.${movementFolder.name}"
                    )
                    uploadGraph(
                        image,
                        patientFolderRef.child("Graphs")
                            .child("Movement_${movementFolder.name}_Graph.png")
                    )
                    Log.d(
                        "ab_do",
                        "Graph of Movement ${movementFolder.name} of Patient $patientId is Uploaded"
                    )
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Error.SyncingErrors.SyncingPatientsGraphsErrors.SyncingFailed(e.message))
        }
    }


    private suspend fun constructGraphPoints(textFilesForMovement: List<StorageReference>): List<GraphPoint<String, Float>> {
        val points = mutableListOf<GraphPoint<String, Float>>()
        for (textFile in textFilesForMovement) {
            points.add(GraphPoint(textFile.name.dropLast(4), extractYValueFromTextFile(textFile)))
        }
        return points
    }


    private suspend fun extractYValueFromTextFile(textFile: StorageReference): Float {
        val content = try {
            val inputStream = textFile.getBytes(Long.MAX_VALUE).await().inputStream()
            val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            val existingContent = reader.readText()
            withContext(Dispatchers.IO) {
                reader.close()
            }
            existingContent
        } catch (e: Exception) {
            ""
        }
        if (content.isEmpty()) {
            return 0f
        }

        val lines = content.lines().filter { it.isNotBlank() }

        // Return the last line
        val lastLine = lines.last()

        return try {
            val digitsFromEnd = StringBuilder()
            for (i in lastLine.length - 1 downTo 0) {
                val char = lastLine[i]
                if (char.isDigit() || char == '.') {
                    digitsFromEnd.append(char)
                } else {
                    break
                }
            }
            digitsFromEnd.reverse().toString().toFloat()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun uploadGraph(byteArray: ByteArray, reference: StorageReference) {
        val metadata = StorageMetadata.Builder()
            .setContentType("image/png")
            .build()
        val task = reference.putBytes(byteArray, metadata).await().task
        if (!task.isSuccessful) {
            throw task.exception ?: Exception("The Result Graph Is Not Uploaded")
        }
    }

}