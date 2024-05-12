package com.beapps.thedoctorapp.content.data.remote.firebase.dto

import com.beapps.thedoctorapp.content.domain.models.Patient

data class PatientDto(
    val doctor_ID: String = "",
    val name: String= "",
    val surname: String= "",
    val mail: String= "",
    val password: String= "",
    val phone: Long = 0L
)

fun PatientDto.toPatient() = Patient(
    name = name,
    surname = surname,
    email = mail,
    phone = phone.toString()
)