package com.beapps.thedoctorapp.core.presentation

sealed class Screen (val route : String) {

    data object RegisterScreen : Screen("register")
    data object LoginScreen : Screen("login")
    data object HomeScreen : Screen("home")
    data object ProfileScreen : Screen("profile")
    data object PatientsListScreen : Screen("patients-screen")
    data object PatientActionsScreen : Screen("patient-actions")
    data object PatientsFilesScreen : Screen("patients_files_screen")
    data object PatientInfoScreen : Screen("patient_info_screen")
    data object PatientNotesScreen : Screen("patient_notes_screen")
    data object MultiMediaScreen : Screen("multi_media_screen")

    fun withArgs (vararg args : String) : String = buildString {
        append(route)
        args.forEach {arg->
            append("/$arg")
        }
    }
}