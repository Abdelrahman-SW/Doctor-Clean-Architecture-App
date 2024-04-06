package com.beapps.thedoctorapp.auth.domain

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface AuthManager {
    fun login (email : String , password : String) : Flow<Result<Doctor, Error.AuthError.LoginError>>
    suspend fun register (
        name: String,
        surname: String,
        phoneNum: String,
        email: String,
        password: String
    ): Result<Doctor , Error.AuthError.RegisterError>

    fun logout()

    suspend fun ifUserAlreadyExits(email: String) : Boolean

}