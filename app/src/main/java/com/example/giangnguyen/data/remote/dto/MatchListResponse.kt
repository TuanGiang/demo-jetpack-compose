package com.example.giangnguyen.data.remote.dto

import com.example.giangnguyen.domain.model.MatchModel

data class MatchListResponse(
    val matches : MatchesResponse
)

data class MatchesResponse(
    val previous: List<MatchModel>,
    val upcoming: List<MatchModel>
)