package com.example.giangnguyen.presentation.match_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.giangnguyen.R
import com.example.giangnguyen.domain.model.MatchModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MatchListScreen(
    viewModel: MatchListViewModel = hiltViewModel(),
    navigateMatchDetails: (MatchModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        MatchPagerView(
            pagerState = pagerState,
            pages = listOf(
                MatchPagerPage(
                    titleResId = R.string.title_upcoming,
                    R.drawable.ic_launcher_background,
                    uiState.value.upcomingMatchList
                ),
                MatchPagerPage(
                    titleResId = R.string.title_previous,
                    R.drawable.ic_launcher_background,
                    uiState.value.previousMatchList
                ),
            ),
            navigateMatchDetails = navigateMatchDetails,
            modifier = modifier.padding(it)
        )
    }
}