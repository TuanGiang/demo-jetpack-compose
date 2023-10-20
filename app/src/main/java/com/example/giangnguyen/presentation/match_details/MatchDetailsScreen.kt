package com.example.giangnguyen.presentation.match_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.presentation.components.MatchItemHeader
import com.example.giangnguyen.presentation.components.MatchItemUIConverter
import com.example.giangnguyen.presentation.components.MatchItemUiModel
import com.example.giangnguyen.presentation.components.MatchItemView
import com.example.giangnguyen.presentation.match_details.components.MatchDetailsHeader
import com.example.giangnguyen.presentation.match_details.components.TextTitle
import com.example.giangnguyen.presentation.match_details.components.VideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    viewModel: MatchDetailsViewModel = hiltViewModel(),
    navigateMatchDetails: (MatchModel) -> Unit,
    backPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteState = viewModel.favoriteState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MatchDetailsAppBar(
                favoriteState.value?.isFavorite == true,
                scrollBehavior,
                { viewModel.toggleFavorite() },
                backPressed
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            uiState.value.matchDetails?.let { matchDetails ->
                item {
                    MatchDetailsHeader(matchDetails = matchDetails)
                }
                matchDetails.match.highlights?.let {
                    item {
                        TextTitle("Highlights")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                        ) {
                            VideoPlayer(videoUrl = it)
                        }
                        Divider(thickness = 1.dp)
                    }
                }
            }
            if (uiState.value.matchesHistory.isNotEmpty()) {
                item {
                    TextTitle("Matches History")
                }
                val uiModels = MatchItemUIConverter.prepareFromMatchDetailsList(
                    uiState.value.matchesHistory
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
fun MatchDetailsAppBar(
    isFavorite: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onFavoriteClicked: () -> Unit,
    backPressed: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Match Details",
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
        actions = {
            IconButton(onClick = { onFavoriteClicked.invoke() }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

