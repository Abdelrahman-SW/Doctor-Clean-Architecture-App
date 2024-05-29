package com.beapps.thedoctorapp.content.domain

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

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


fun String.copyToClipboard(context: Context) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", this)
    clipboardManager.setPrimaryClip(clipData)
}