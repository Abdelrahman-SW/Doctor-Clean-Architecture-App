package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.content.domain.models.Patient

const val DELIMITER_CHAR = "_"

//fun String.getPatientFromFolderName () : Patient {
//    val parts = this.split(DELIMITER_CHAR)
//    // [name_surname_patientId]
//    return Patient(id = parts[2], name = parts[0], surname = parts[1])
//}

//fun String.getPatientIdFromFolderName () : String {
//    val parts = this.split(DELIMITER_CHAR)
//    // [name_surname_patientId]
//    return parts[2]
//}

//fun Patient.extractFolderName() : String {
//    return "${name}${DELIMITER_CHAR}${surname}${DELIMITER_CHAR}${id}"
//}