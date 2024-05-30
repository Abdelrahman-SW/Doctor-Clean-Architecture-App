@file:JvmName("PatientFilesViewModelKt")

package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.presentation.patinet.patientFiles.components.PatientFileItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientFilesScreen(
    navController: NavController,
    patient: Patient?,
    screenState: PatientFilesScreenState,
    onEvent: (PatientFilesViewModel.PatientFilesScreenEvents) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        patient?.let {
            onEvent(
                PatientFilesViewModel.PatientFilesScreenEvents.GetPatientFiles(
                    patient
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = "Files",
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

        if (screenState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                SearchBar(
                    modifier = Modifier,
                    query = screenState.searchQuery,
                    onQueryChange = {
                        onEvent(
                            PatientFilesViewModel.PatientFilesScreenEvents.OnSearchQueryChanged(
                                it
                            )
                        )
                    },
                    onSearch = {

                    },
                    active = false,
                    onActiveChange = { },
                    placeholder = {
                        Text(
                            text = "Search Files .. ", fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                ) {}

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(screenState.filteredPatientFiles) { patientContent ->
                        PatientFileItem(patientFile = patientContent, onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "patientContent",
                                patientContent
                            )
                            navController.navigate(Screen.MultiMediaScreen.route)
                        })
                        HorizontalDivider()
                    }
                }

                screenState.error?.let { error ->
                    val errorMessage = when (error) {
                        Error.GetContentErrors.EmptyContent -> "No data To Display"
                        is Error.GetContentErrors.Others -> error.message ?: "UnKnown Error"
                    }
                    Text(text = errorMessage)
                }
            }
        }
    }
}

@Composable
fun PatientContentScreenRoot(
    navController: NavController,
    patient: Patient?
) {
    val viewModel = hiltViewModel<PatientFilesViewModel>()
    PatientFilesScreen(
        navController,
        patient,
        viewModel.screenState,
        viewModel::onEvent
    )
}