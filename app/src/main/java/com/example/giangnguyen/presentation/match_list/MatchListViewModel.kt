package com.example.giangnguyen.presentation.match_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giangnguyen.R
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchStatus
import com.example.giangnguyen.domain.repository.AppRepository
import com.example.giangnguyen.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchListUiState(
    val upcomingMatchList: List<MatchDetailsModel> = emptyList(),
    val previousMatchList: List<MatchDetailsModel> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<MatchListUiState> = combine(
        _userMessage,
        _isLoading,
        appRepository.getMatchListStream(),
        appRepository.getTeamListStream()
    ) { userMessage, isLoading, matches, teams ->
        val matchesDetails = mutableListOf<MatchDetailsModel>()
        matches.forEach { match ->
            val homeTeamDetails = teams.find { match.home == it.name }
            val awayTeamDetails = teams.find { match.away == it.name }
            matchesDetails += MatchDetailsModel(
                match, homeTeamDetails, awayTeamDetails
            )
        }
        MatchListUiState(
            upcomingMatchList = matchesDetails.filter { it.match.matchStatus == MatchStatus.UPCOMING },
            previousMatchList = matchesDetails.filter { it.match.matchStatus == MatchStatus.PREVIOUS },
            isLoading = isLoading,
            userMessage = userMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = MatchListUiState(isLoading = true)
    )

    init {
        reloadData()
    }


    private fun reloadData() {
        viewModelScope.launch {
            appRepository.fetchMatchList()
                .zip(appRepository.fetchTeamList()) { _, _ ->{}
                }
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