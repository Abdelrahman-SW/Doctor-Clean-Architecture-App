package com.beapps.thedoctorapp.auth.domain

data class Doctor(
    val id: String = "",
    val name: String,
    val surname: String,
    val phoneNum: String,
    val email: String,
    val password: String
)
