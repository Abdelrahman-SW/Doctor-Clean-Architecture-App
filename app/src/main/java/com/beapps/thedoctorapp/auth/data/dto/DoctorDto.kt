package com.beapps.thedoctorapp.auth.data.dto

import androidx.annotation.Keep


@Keep
data class DoctorDto(
    val name: String="",
    val surname: String="",
    val phoneNum: String="",
    val email: String="",
    val password: String=""
)


