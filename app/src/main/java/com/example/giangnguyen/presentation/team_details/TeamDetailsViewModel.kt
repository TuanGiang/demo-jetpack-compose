package com.example.giangnguyen.presentation.team_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giangnguyen.R
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchStatus
import com.example.giangnguyen.domain.model.TeamModel
import com.example.giangnguyen.domain.repository.AppRepository
import com.example.giangnguyen.presentation.match_list.MatchListUiState
import com.example.giangnguyen.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TeamDetailsUiState(
    val team: TeamModel? = null,
    val matches: List<MatchDetailsModel> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val teamName = savedStateHandle.get<String>("name")!!
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<TeamDetailsUiState> = combine(
        _userMessage,
        _isLoading,
        appRepository.getMatchListStreamByTeam(teamName),
        appRepository.getTeamListStream()
    ) { userMessage, isLoading, matches, teams ->
        if (teams.isNotEmpty()) {
            val team = teams.find { it.name == teamName }
            val matchesDetails = matches.sortedBy { it.matchStatus }.map { match ->
                MatchDetailsModel(
                    match = match,
                    homeTeam = teams.find { it.name == match.home },
                    awayTeam = teams.find { it.name == match.away }
                )
            }
            TeamDetailsUiState(
                team = team,
                matches = matchesDetails,
                isLoading = isLoading,
                userMessage = userMessage
            )
        } else {
            TeamDetailsUiState(isLoading = true)
        }

    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = TeamDetailsUiState(isLoading = true)
    )

    init {
        reloadData()
    }


    private fun reloadData() {
        viewModelScope.launch {
            appRepository.getTeamListStream()
                .catch {
                    _userMessage.emit(R.string.message_error_general)
                    _isLoading.emit(false)

                }.onStart {
                    _isLoading.emit(true)
                }
                .collect {
                    _isLoading.emit(false)
                }
        }
    }

}
