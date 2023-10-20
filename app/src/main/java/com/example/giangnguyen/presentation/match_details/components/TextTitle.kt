package com.example.giangnguyen.presentation.match_details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TextTitle(title: String) {
    Text(text = title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
}