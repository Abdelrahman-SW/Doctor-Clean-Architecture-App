package com.beapps.thedoctorapp.content.presentation.patinet.patientInfo

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.domain.copyToClipboard
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    patient: Patient?
) {

    val context = LocalContext.current

    val patientInfoItems = remember(patient) {
        listOf(
            PatientInfo(
                title = "Email",
                description = patient?.email ?: "",
                icon = Icons.Default.Email
            ),
            PatientInfo(
                title = "Phone",
                description = patient?.phone ?: "",
                icon = Icons.Default.Phone
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = "Contact Info",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }

    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (infoItem in patientInfoItems) {
                PatientInfoItem(
                    item = infoItem,
                    onCopyClicked = {
                        it.description.copyToClipboard(context)
                        Toast.makeText(context , "Copied Successfully !", Toast.LENGTH_SHORT).show()
                    }
                )

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
