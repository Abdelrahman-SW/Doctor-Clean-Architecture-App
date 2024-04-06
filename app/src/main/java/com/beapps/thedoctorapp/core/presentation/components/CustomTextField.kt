package com.beapps.thedoctorapp.core.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(modifier: Modifier = Modifier , value: String, isError: Boolean = false, label: @Composable () -> Unit, supportingText: @Composable() (() -> Unit)? = null, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        isError = isError,
        supportingText = supportingText,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    )
}