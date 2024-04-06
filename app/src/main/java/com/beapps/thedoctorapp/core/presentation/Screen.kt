package com.beapps.thedoctorapp.core.presentation

sealed class Screen (val route : String) {
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("register")

    data object HomeScreen : Screen("home")
    data object ProfileScreen : Screen("profile")
    data object PatientsScreen : Screen("patients_screen")
    data object PatientsContentsScreen : Screen("patients_contents_screen")
}