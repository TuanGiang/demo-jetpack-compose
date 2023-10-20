package com.example.giangnguyen.domain.model

data class MatchDetailsModel(
    val match: MatchModel,
    val homeTeam: TeamModel?,
    val awayTeam: TeamModel?
)