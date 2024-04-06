package com.beapps.thedoctorapp.auth.data.firebase

import android.util.Log
import com.beapps.thedoctorapp.auth.data.dto.DoctorDto
import com.beapps.thedoctorapp.auth.data.dto.toDoctor
import com.beapps.thedoctorapp.auth.domain.AuthManager
import com.beapps.thedoctorapp.auth.domain.Doctor
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

    override fun login(email: String, password: String): Flow<Result<Doctor, Error.AuthError>> {

        return flow {
            try {
                val result = db.collection(collectionName)
                    .whereEqualTo(EMAIL_FIELD, email)
                    .get()
                    .await()

                if (result.isEmpty) {
                    emit(Result.Error(Error.AuthError.IncorrectEmail))
                    return@flow
                }

                for (document in result.documents) {
                    val storedPassword = document.getString(PASSWORD_FIELD)
                    if (storedPassword != null && storedPassword == password) {
                        val doctor = document.toObject<DoctorDto>()
                        emit(Result.Success(doctor?.toDoctor()?.copy(id = document.id)))
                        return@flow
                    }
                }

                emit(Result.Error(Error.AuthError.IncorrectPassword))

            } catch (e: Exception) {
                emit(Result.Error(Error.AuthError.UndefinedError(e.message ?: "Unknown Error")))
            }
        }
    }

    override suspend fun register(
        name: String,
        surname: String,
        phoneNum: String,
        email: String,
        password: String
    ): Result<Doctor, Error.AuthError> {
        val doctor = DoctorDto(
            name = name,
            phoneNum = phoneNum,
            email = email,
            password = password,
            surname = surname
        )
        if (ifUserAlreadyExits(email)) return Result.Error(Error.AuthError.UserAlreadyExitsError)
        return try {
            val result = db.collection(collectionName)
                .add(doctor)
                .await()
            val newDoctor = doctor.toDoctor().copy(id = result.id)
            Result.Success(newDoctor)
        } catch (e: Exception) {
            Result.Error(Error.AuthError.UndefinedError(e.message ?: "Unknown Error"))
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