package com.example.giangnguyen.presentation.components

import com.example.giangnguyen.domain.model.MatchDetailsModel

sealed class MatchItemUiModel {
    data class MatchItemUIHeader(val title: String) : MatchItemUiModel()
    data class MatchItemUIItem(val details: MatchDetailsModel) : MatchItemUiModel()
}
