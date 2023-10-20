package com.example.giangnguyen.presentation.team_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.presentation.components.MatchItemHeader
import com.example.giangnguyen.presentation.components.MatchItemUIConverter
import com.example.giangnguyen.presentation.components.MatchItemUiModel
import com.example.giangnguyen.presentation.components.MatchItemView
import com.example.giangnguyen.presentation.match_details.components.TextTitle
import com.example.giangnguyen.presentation.team_details.components.TeamDetailView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailsScreen(
    viewModel: TeamDetailsViewModel = hiltViewModel(),
    navigateMatchDetails: (MatchModel) -> Unit,
    backPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TeamDetailsAppBar(uiState.value.team?.name ?: "Team Details" ,scrollBehavior, backPressed)
        },
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            uiState.value.team?.let {
                item {
                    TeamDetailView(teamModel = it)
                }
            }
            if (uiState.value.matches.isNotEmpty()) {
                item {
                    TextTitle("Matches")
                }
                val uiModels = MatchItemUIConverter.prepareFromMatchDetailsList(
                    uiState.value.matches
                )
                items(uiModels) {
                    when (it) {
                        is MatchItemUiModel.MatchItemUIItem -> {
                            MatchItemView(
                                matchDetails = it.details,
                                itemClicked = navigateMatchDetails
                            )
                        }

                        is MatchItemUiModel.MatchItemUIHeader -> {
                            MatchItemHeader(dateString = it.title)
                        }
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailsAppBar(teamName: String, scrollBehavior: TopAppBarScrollBehavior, backPressed: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                teamName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { backPressed.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

