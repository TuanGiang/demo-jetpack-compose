package com.example.giangnguyen.presentation.team_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.giangnguyen.domain.model.TeamModel
import com.example.giangnguyen.presentation.components.TeamLogo

@Composable
fun TeamDetailView(teamModel: TeamModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamLogo(
            logoURL = teamModel.logo,
            contentDescription = teamModel.name,
            modifier = Modifier
                .size(80.dp)
                .clip(
                    CircleShape
                )
        )
        Text(
            text = teamModel.name,
            fontWeight = FontWeight.Bold
        )
    }
}
