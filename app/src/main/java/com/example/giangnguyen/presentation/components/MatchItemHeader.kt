package com.example.giangnguyen.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giangnguyen.ui.theme.MatchHeaderBackground

@Composable
fun MatchItemHeader(
    dateString: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(MatchHeaderBackground)
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = dateString, fontWeight = FontWeight.Bold)
    }
}



@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun MatchItemHeaderPreview() {
    MatchItemHeader("10 Nov 1992")
}