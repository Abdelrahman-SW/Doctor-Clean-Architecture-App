package com.beapps.thedoctorapp.content.domain.models

data class PatientNote(
    val id: String,
    val note: String,
    val createdAt: Long = 0L,
    val byDoctorId: String = "",
    val toPatientId: String =""
)


