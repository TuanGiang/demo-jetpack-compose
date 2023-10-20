package com.example.giangnguyen.presentation.match_details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchFavoriteModel
import com.example.giangnguyen.domain.repository.AppRepository
import com.example.giangnguyen.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchDetailsUiState(
    val matchDetails: MatchDetailsModel? = null,
    val matchesHistory: List<MatchDetailsModel> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {


    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val homeTeamName = savedStateHandle.get<String>("home")!!
    private val awayTeamName = savedStateHandle.get<String>("away")!!
    private val dateString = savedStateHandle.get<String>("dateString")!!

    val favoriteState: StateFlow<MatchFavoriteModel?> =
        appRepository.getFavoriteStreamByMatch(
            dateString ,
            homeTeamName,
            awayTeamName
        ).stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = null
        )

    val uiState: StateFlow<MatchDetailsUiState> = combine(
        _userMessage,
        _isLoading,
        appRepository.getMatchListStream(),
        appRepository.getTeamStreamByTeamName(homeTeamName),
        appRepository.getTeamStreamByTeamName(awayTeamName),
    ) { userMessage, isLoading, matches, homeTeam, awayTeam ->
        if (matches.isNotEmpty()) {
            val match =
                matches.find { it.home == homeTeamName && it.away == awayTeamName && it.dateString == dateString }
            val matchesHistory = matches.filter { match ->
                (match.home == homeTeamName && match.away == awayTeamName) || (match.home == awayTeamName && match.away == homeTeamName)
            }.map {
                MatchDetailsModel(
                    it,
                    homeTeam = if (it.home == homeTeamName) homeTeam else awayTeam,
                    awayTeam = if (it.away == awayTeamName) awayTeam else homeTeam,
                )
            }
            if (match != null) {
                MatchDetailsUiState(
                    matchDetails = MatchDetailsModel(
                        match, homeTeam, awayTeam
                    ),
                    matchesHistory = matchesHistory,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            } else {
                MatchDetailsUiState(isLoading = isLoading, userMessage = userMessage)
            }

        } else {
            MatchDetailsUiState(isLoading = isLoading, userMessage = userMessage)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = MatchDetailsUiState(isLoading = true)
    )

    fun toggleFavorite() {
        viewModelScope.launch {
            val favoriteModel = favoriteState.value
            if (favoriteModel != null) {
                appRepository.saveFavorite(favoriteState.value!!.copy(isFavorite = !favoriteState.value!!.isFavorite))
            } else {
                appRepository.saveFavorite(
                    MatchFavoriteModel(
                        dateString!!, homeTeamName!!, awayTeamName!!, true
                    )
                )
            }
        }
    }



}