package com.beapps.thedoctorapp.auth.data.mappers

import com.beapps.thedoctorapp.auth.data.dto.DoctorDto
import com.beapps.thedoctorapp.core.domain.Doctor

fun Doctor.toDoctorDto() : DoctorDto {
    return DoctorDto(
        name = name,
        phoneNum = phoneNum,
        email = email,
        password = password,
        surname = surname
    )
}

fun DoctorDto.toDoctor() : Doctor {
    return Doctor(
        name = name,
        phoneNum = phoneNum,
        email = email,
        password = password,
        surname = surname
    )
}