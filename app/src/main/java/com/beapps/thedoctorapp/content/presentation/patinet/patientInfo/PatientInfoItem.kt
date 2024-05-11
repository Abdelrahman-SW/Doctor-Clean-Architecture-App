package com.beapps.thedoctorapp.content.presentation.patinet.patientInfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beapps.thedoctorapp.ui.theme.Purple40

@Composable
fun PatientInfoItem(
    modifier: Modifier = Modifier,
    item: PatientInfo,
    onCopyClicked: (PatientInfo) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Purple40)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = item.icon, contentDescription = "Icon")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = item.description, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.ContentCopy,
                tint = Purple40,
                contentDescription = "copy",
                modifier = modifier.clickable {
                    onCopyClicked(item)
                })
        }
    }
}


data class PatientInfo(
    val title: String,
    val description: String,
    val icon: ImageVector
)