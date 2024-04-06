package com.beapps.thedoctorapp.auth.data.dto

import com.beapps.thedoctorapp.auth.domain.Doctor

data class DoctorDto(
    val name: String="",
    val surname: String="",
    val phoneNum: String="",
    val email: String="",
    val password: String=""
)

fun DoctorDto.toDoctor() : Doctor{
    return Doctor(
        name = name,
        phoneNum = phoneNum,
        email = email,
        password = password,
        surname = surname
    )
}
