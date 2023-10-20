package com.example.giangnguyen.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.domain.model.MatchStatus
import com.example.giangnguyen.domain.model.TeamModel
import com.example.giangnguyen.ui.theme.MatchHeaderBackground
import com.example.giangnguyen.ui.theme.MatchWinnerTextColor
import com.example.giangnguyen.utils.DateTimeUtils


@Composable
fun MatchItemView(
    matchDetails: MatchDetailsModel,
    itemClicked: (MatchModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.clickable {
        itemClicked.invoke(matchDetails.match)
    }) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = matchDetails.match.home,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(horizontal = 8.dp)
                    )
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
                }
            }
            Box(modifier = Modifier.width(60.dp)) {
                Text(
                    text = DateTimeUtils.convertMatchDateStringToHourMinuteString(matchDetails.match.dateString),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(horizontal = 8.dp)
                    )
                }
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
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
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
        Spacer(modifier = Modifier.height(8.dp))
        Divider(thickness = 1.dp)
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TeamLogo(
    logoURL: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    GlideImage(
        model = logoURL,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun MatchItemViewPreview() {
    val matchDetails = MatchDetailsModel(
        match = MatchModel(
            "2022-04-23T18:00:00.000Z",
            "Team Cool Eagles vs. Team Red Dragons",
            "Team Cool Eagles",
            "Team Red Dragons",
            "Team Red Dragons",
            "https://tstzj.s3.amazonaws.com/highlights.mp4",
            MatchStatus.UPCOMING
        ),
        awayTeam = TeamModel(
            "767ec50c-7fdb-4c3d-98f9-d6727ef8252b",
            "Team Red Dragons",
            "https://tstzj.s3.amazonaws.com/dragons.png"
        ),
        homeTeam = TeamModel(
            "7b4d8114-742b-4410-971a-500162101158",
            "Team Cool Eagles",
            "https://tstzj.s3.amazonaws.com/eagle.png"
        )
    )
    MatchItemView(
        matchDetails = matchDetails,
        itemClicked = {},
        modifier = Modifier
    )
}