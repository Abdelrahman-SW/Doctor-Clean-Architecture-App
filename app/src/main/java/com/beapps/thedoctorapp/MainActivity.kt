package com.beapps.thedoctorapp

import PatientFilesScreenRoot
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beapps.thedoctorapp.auth.presentation.login.LoginScreenRoot
import com.beapps.thedoctorapp.auth.presentation.register.RegisterScreenRoot
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.content.presentation.doctor.home.HomeScreenRoot
import com.beapps.thedoctorapp.content.presentation.doctor.profile.ProfileScreenRoot
import com.beapps.thedoctorapp.content.presentation.patinet.patientActions.PatientActionsScreen
import com.beapps.thedoctorapp.content.presentation.patinet.patientInfo.PatientInfoScreen
import com.beapps.thedoctorapp.content.presentation.patinet.patientsList.PatientScreenRoot
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesScreen
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.multimedia.presentation.MediaScreen
import com.beapps.thedoctorapp.ui.theme.TheDoctorAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheDoctorAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination = remember {
                        mainViewModel.getTheCurrentRoute()
                    }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(Screen.LoginScreen.route) {
                            LoginScreenRoot(navController)
                        }
                        composable(Screen.RegisterScreen.route) {
                            RegisterScreenRoot(navController)
                        }
                        composable(Screen.HomeScreen.route) {
                            HomeScreenRoot(navController)
                        }
                        composable(Screen.ProfileScreen.route) {
                            ProfileScreenRoot(mainViewModel.getCurrentLoggedInDoctor() ?: Doctor())
                        }
                        composable(Screen.PatientsListScreen.route) {
                            PatientScreenRoot(navController)
                        }
                        composable(Screen.PatientActionsScreen.route) {
                            val patient =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Patient>(
                                    "patient"
                                )
                            PatientActionsScreen(
                                navController = navController,
                                patient = patient
                            )
                        }
                        composable(Screen.PatientsFilesScreen.route) {
                            val patient =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Patient>(
                                    "patient"
                                )
                            PatientFilesScreenRoot(navController, patient)
                        }

                        composable(Screen.PatientInfoScreen.route) {
                            val patient =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Patient>(
                                    "patient"
                                )
                            PatientInfoScreen(navController = navController , patient = patient)
                        }

                        composable(Screen.PatientNotesScreen.route) {
                            val patient =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Patient>(
                                    "patient"
                                )
                            PatientNotesScreen(patient = patient , navController = navController)
                        }

                        composable(
                            Screen.MultiMediaScreen.route
                        ) {
                            val patientFile =
                                navController.previousBackStackEntry?.savedStateHandle?.get<PatientFile>(
                                    "patientContent"
                                )
                            MediaScreen(patientFile = patientFile, navController)
                        }

                    }
                }
            }
        }
    }
}
