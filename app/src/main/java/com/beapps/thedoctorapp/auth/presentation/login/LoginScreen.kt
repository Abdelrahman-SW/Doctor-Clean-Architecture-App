package com.beapps.thedoctorapp.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.core.presentation.components.CustomTextField



@Composable
private fun LoginScreen(navController: NavController , loginState: LoginState, onEvent: (LoginScreenEvents) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(loginState.goToRegister) {
            if (loginState.goToRegister) {
                navController.navigate(Screen.RegisterScreen.route)
                loginState.goToRegister = false
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

        Text(
            text = "You don`t have an Account ? , Register Now",
            fontSize = 22.sp,
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
    LoginScreen(navController , loginState = viewModel.loginState , viewModel::onEvent)
}