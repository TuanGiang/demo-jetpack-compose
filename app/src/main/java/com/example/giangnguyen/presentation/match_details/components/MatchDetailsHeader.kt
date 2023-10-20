package com.example.giangnguyen.presentation.match_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.presentation.components.TeamLogo
import com.example.giangnguyen.ui.theme.MatchHeaderBackground
import com.example.giangnguyen.ui.theme.MatchWinnerTextColor
import com.example.giangnguyen.utils.DateTimeUtils

@Composable
fun MatchDetailsHeader(
    matchDetails: MatchDetailsModel,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.Center) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                matchDetails.homeTeam?.let {
                    TeamLogo(
                        logoURL = it.logo,
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(
                                CircleShape
                            )
                    )
                }
                Text(
                    text = matchDetails.match.home,
                    fontWeight = FontWeight.SemiBold
                )

            }

            Column(modifier = Modifier.width(120.dp)) {
                Text(
                    text = DateTimeUtils.convertMatchDateStringToHourMinuteString(matchDetails.match.dateString),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = DateTimeUtils.convertMatchDateStringToOnlyDateString(matchDetails.match.dateString),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                matchDetails.awayTeam?.let {
                    TeamLogo(
                        logoURL = it.logo,
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(
                                CircleShape
                            )
                    )
                }
                Text(
                    text = matchDetails.match.away,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Text(
            text = matchDetails.match.description,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        matchDetails.match.winner?.let {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Winner:"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    color = MatchWinnerTextColor,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}