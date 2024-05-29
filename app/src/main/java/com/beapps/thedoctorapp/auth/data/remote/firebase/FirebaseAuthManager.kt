package com.beapps.thedoctorapp.auth.data.remote.firebase

import com.beapps.thedoctorapp.auth.data.dto.DoctorDto
import com.beapps.thedoctorapp.auth.domain.AuthManager
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.auth.data.mappers.toDoctor
import com.beapps.thedoctorapp.auth.data.mappers.toDoctorDto
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseAuthManager : AuthManager {

    companion object {
        const val EMAIL_FIELD = "email"
        const val PASSWORD_FIELD = "password"
        const val NAME_FIELD = "name"
        const val SURNAME_FIELD = "surname"
        const val PHONE_NUM_FIELD = "phone_num"
        const val ID_FIELD = "id"
    }

    private val collectionName = "doctors"
    private val db = FirebaseFirestore.getInstance()

    override fun login(email: String, password: String): Flow<Result<Doctor, Error.AuthError.LoginError>> {

        return flow {
            try {
                val result = db.collection(collectionName)
                    .whereEqualTo(EMAIL_FIELD, email)
                    .get()
                    .await()

                if (result.isEmpty) {
                    emit(Result.Error(Error.AuthError.LoginError.IncorrectEmail))
                    return@flow
                }

                for (document in result.documents) {
                    val storedPassword = document.getString(PASSWORD_FIELD)
                    if (storedPassword != null && storedPassword == password) {
                        val doctor = document.toObject<DoctorDto>()
                        emit(Result.Success(doctor?.toDoctor()?.copy(id = document.id) ?: DoctorDto().toDoctor()))
                        return@flow
                    }
                }

                emit(Result.Error(Error.AuthError.LoginError.IncorrectPassword))

            }
            catch (e: Exception) {
                emit(Result.Error(Error.AuthError.LoginError.Others(e.message)))
            }
        }
    }

    override suspend fun register(
        doctor: Doctor
    ): Result<Doctor, Error.AuthError.RegisterError> {
        val doctorDto = doctor.toDoctorDto()
        if (ifUserAlreadyExits(doctorDto.email)) return Result.Error(Error.AuthError.RegisterError.UserAlreadyExitsError)
        return try {
            val result = db.collection(collectionName)
                .add(doctorDto)
                .await()
            val newDoctor = doctorDto.toDoctor().copy(id = result.id)
            Result.Success(newDoctor)
        } catch (e: Exception) {
            Result.Error(Error.AuthError.RegisterError.Others(e.message))
        }
    }

    override suspend fun ifUserAlreadyExits(email: String): Boolean {
        val result = db.collection(collectionName)
            .whereEqualTo(EMAIL_FIELD, email)
            .get()
            .await()

        return !result.isEmpty
    }

    override fun logout() {

    }

}