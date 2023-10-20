package com.example.giangnguyen.presentation.team_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun TeamItemView(
    teamModel: TeamModel, itemClicked: (TeamModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                itemClicked.invoke(teamModel)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamLogo(
            logoURL = teamModel.logo,
            contentDescription = teamModel.name,
            modifier = Modifier
                .size(40.dp)
                .clip(
                    CircleShape
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = teamModel.name, fontWeight = FontWeight.SemiBold)
    }
}