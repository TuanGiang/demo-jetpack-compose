package com.example.giangnguyen.presentation.match_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.presentation.components.MatchItemHeader
import com.example.giangnguyen.presentation.components.MatchItemUIConverter
import com.example.giangnguyen.presentation.components.MatchItemUiModel
import com.example.giangnguyen.presentation.components.MatchItemView

@Composable
fun MatchListView(
    matchList: List<MatchDetailsModel>,
    navigateMatchDetails: (MatchModel) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiModels = MatchItemUIConverter.prepareFromMatchDetailsList(
        matchList
    )
    Box(modifier = modifier) {
        LazyColumn {
            items(uiModels) {
                when (it) {
                    is MatchItemUiModel.MatchItemUIItem -> {
                        MatchItemView(matchDetails = it.details, itemClicked = navigateMatchDetails)
                    }

                    is MatchItemUiModel.MatchItemUIHeader -> {
                        MatchItemHeader(dateString = it.title)
                    }
                }
            }

        }
    }
}