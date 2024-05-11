package com.beapps.thedoctorapp.content.presentation.patinet.patientsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.presentation.patinet.patientsList.components.PatientItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsListScreen(
    navController: NavController,
    screenState: PatientsListScreenState,
    onEvent: (PatientsListViewModel.PatientScreenEvents) -> Unit
) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                    title = {
                        Text(
                            text = "Patients",
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
                        .padding(horizontal = 16.dp , vertical = 8.dp), horizontalAlignment = CenterHorizontally
                ) {

                    SearchBar(
                        modifier = Modifier,
                        query = screenState.searchQuery,
                        onQueryChange = {
                            onEvent(
                                PatientsListViewModel.PatientScreenEvents.OnSearchQueryChanged(
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
                                text = "Search Patients .. ", fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        },
                    ) {}

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {

                        items(screenState.filteredPatients) { patient ->
                            PatientItem(patient = patient, onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "patient",
                                    patient
                                )
                                navController.navigate(
                                    Screen.PatientActionsScreen.route
                                )
                            })
                            HorizontalDivider()
                        }
                    }
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

@Composable
fun PatientScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<PatientsListViewModel>()
    PatientsListScreen(navController, viewModel.screenState, viewModel::onEvent)
}