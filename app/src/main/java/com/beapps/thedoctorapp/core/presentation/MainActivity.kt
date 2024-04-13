package com.beapps.thedoctorapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.auth.presentation.login.LoginScreenRoot
import com.beapps.thedoctorapp.auth.presentation.register.RegisterScreenRoot
import com.beapps.thedoctorapp.content.presentation.home.HomeScreen
import com.beapps.thedoctorapp.content.presentation.home.HomeScreenRoot
import com.beapps.thedoctorapp.content.presentation.profile.ProfileScreen
import com.beapps.thedoctorapp.content.presentation.profile.ProfileScreenRoot
import com.beapps.thedoctorapp.ui.theme.TheDoctorAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val currentDoctor by lazy {
        mainViewModel.getCurrentLoggedInDoctor()
    }

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
                            currentDoctor?.let { doctor ->
                                HomeScreenRoot(navController, doctor = doctor)
                            }
                        }
                        composable(Screen.ProfileScreen.route) {
                            currentDoctor?.let { doctor ->
                                ProfileScreenRoot(doctor = doctor)
                            }
                        }
                    }
                }
            }
        }
    }
}
