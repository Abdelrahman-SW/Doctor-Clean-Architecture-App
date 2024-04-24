package com.beapps.thedoctorapp.multimedia.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.multimedia.presentation.MediaState

@Composable
fun TextViewerScreen(state: MediaState.TextState, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = state.text)
    }
}