package com.beapps.thedoctorapp.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.core.presentation.components.CustomTextField


@Composable
private fun RegisterScreen(
    navController: NavController,
    registerState: RegisterState,
    onEvent: (RegisterScreenEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current
        

        DisposableEffect(registerState.screenState) {
            when(val statue = registerState.screenState) {
                is RegisterScreenState.Error -> {
                    val message = when(statue.error) {
                        Error.AuthError.RegisterError.UserAlreadyExitsError -> "There is Already An Account With this Email , Try to Log-in instead"
                        is Error.AuthError.RegisterError.Others -> {
                            statue.error.message  ?: "UnKnown Error"
                        }
                    }
                    Toast.makeText(context , message , Toast.LENGTH_LONG).show()
                }
               is RegisterScreenState.Success -> {
                    navController.popBackStack(Screen.LoginScreen.route , true)
                    navController.navigate(Screen.HomeScreen.route)
                }
                RegisterScreenState.GoToLogin -> {
                    navController.popBackStack()
                }

                RegisterScreenState.Idle -> Unit
            }
            onDispose {
                onEvent(RegisterScreenEvents.OnDispose)
            }
        }
        

        CustomTextField(value = registerState.name, label = { Text(text = "Enter Name") }) {
            onEvent(RegisterScreenEvents.NameChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = registerState.surname, label = { Text(text = "Enter Surname") }) {
            onEvent(RegisterScreenEvents.SurnameChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = registerState.phoneNum,
            label = { Text(text = "Enter Phone No.") }) {
            onEvent(RegisterScreenEvents.PhoneNumChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = registerState.email, label = { Text(text = "Enter Email") }) {
            onEvent(RegisterScreenEvents.EmailChanged(it))
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = registerState.password, label = { Text(text = "Enter Password") }) {
            onEvent(RegisterScreenEvents.PasswordChanged(it))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onEvent(RegisterScreenEvents.RegisterClicked) }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You already have an Account ? , Login Now",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.clickable { onEvent(RegisterScreenEvents.LoginClicked) }
        )

        Spacer(modifier = Modifier.height(16.dp))


        if (registerState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RegisterScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    RegisterScreen(navController, registerState = viewModel.registerState, viewModel::onEvent)
}