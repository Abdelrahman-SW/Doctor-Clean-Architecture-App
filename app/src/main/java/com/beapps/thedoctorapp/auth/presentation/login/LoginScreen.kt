package com.beapps.thedoctorapp.auth.presentation.login

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
private fun LoginScreen(
    navController: NavController,
    loginState: LoginState,
    onEvent: (LoginScreenEvents) -> Unit
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

        DisposableEffect(loginState.screenState) {
            when(val statue = loginState.screenState) {
                is LoginScreenState.Error -> {
                    val message = when(statue.error) {
                        Error.AuthError.LoginError.IncorrectEmail -> "The Email You Entered Is Not Correct"
                        Error.AuthError.LoginError.IncorrectPassword -> "The Password You Entered Is Not Correct"
                        is Error.AuthError.LoginError.Others -> {
                            statue.error.message ?: "UnKnown Error"
                        }
                    }
                    Toast.makeText(context, message , Toast.LENGTH_LONG).show()
                }
                is LoginScreenState.Success -> {
                    navController.popBackStack(Screen.LoginScreen.route , true)
                    navController.navigate(Screen.HomeScreen.route)
                }
                LoginScreenState.GoToRegister -> {
                    navController.navigate(Screen.RegisterScreen.route)
                }
                LoginScreenState.Idle -> Unit
            }

            onDispose {
                onEvent(LoginScreenEvents.OnDispose)
            }
        }

        CustomTextField(value = loginState.email, label = { Text(text = "Email") }) {
            onEvent(LoginScreenEvents.EmailChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = loginState.password, label = { Text(text = "Password") }) {
            onEvent(LoginScreenEvents.PasswordChanged(it))
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onEvent(LoginScreenEvents.LoginClicked) }) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You don`t have an Account ? , Register Now",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.clickable { onEvent(LoginScreenEvents.RegisterClicked) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loginState.isLoading) {
            CircularProgressIndicator()
        }


    }
}

@Composable
fun LoginScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    LoginScreen(navController, loginState = viewModel.loginState, viewModel::onEvent)
}