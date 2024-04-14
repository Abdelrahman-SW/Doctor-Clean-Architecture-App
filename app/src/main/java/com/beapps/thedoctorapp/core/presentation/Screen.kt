package com.beapps.thedoctorapp.core.presentation

sealed class Screen (val route : String) {
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("register")

    data object HomeScreen : Screen("home")
    data object ProfileScreen : Screen("profile")
    data object PatientsScreen : Screen("patients-screen")
    data object PatientsContentsScreen : Screen("patients_contents_screen")

    fun withArgs (vararg args : String) : String = buildString {
        append(route)
        args.forEach {arg->
            append("/$arg")
        }
    }
}