package com.example.giangnguyen.presentation.team_list

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

data class TeamListUiState(
    val teams: List<TeamModel> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class TeamListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<TeamListUiState> = combine(
        _userMessage,
        _isLoading,
        appRepository.getTeamListStream()
    ) { userMessage, isLoading, teams ->
        TeamListUiState(
            teams = teams,
            isLoading = isLoading,
            userMessage = userMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = TeamListUiState(isLoading = true)
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
