package com.example.giangnguyen.presentation.team_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.giangnguyen.domain.model.TeamModel
import com.example.giangnguyen.presentation.team_list.components.TeamItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListScreen(
    navigateToTeamDetails: (TeamModel) -> Unit,
    viewModel: TeamListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Teams",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                })
        }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(uiState.value.teams) { team ->
                TeamItemView(teamModel = team, itemClicked = navigateToTeamDetails)
                Divider(thickness = 1.dp)
            }
        }

    }
}

