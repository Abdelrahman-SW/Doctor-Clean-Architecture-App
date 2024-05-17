package com.beapps.thedoctorapp.content.data.remote.firebase.dto

data class PatientNoteDto(
    val id: String ,
    val note: String,
    val createdAt: String,
    val byDoctorId: String = "",
    val toPatientId: String =""
) {
}