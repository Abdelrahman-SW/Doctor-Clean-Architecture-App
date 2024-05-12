package com.beapps.thedoctorapp.content.domain.models

data class PatientNote(
    val id: Int,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
    val byDoctorId: String,
    val toPatientId: String
)


