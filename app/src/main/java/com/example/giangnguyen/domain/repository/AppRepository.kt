package com.example.giangnguyen.domain.repository

import com.example.giangnguyen.domain.model.MatchFavoriteModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.domain.model.TeamModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getMatchListStream(): Flow<List<MatchModel>>

    suspend fun getMatchListByTeamId(
        teamID: String,
        fetchList: Boolean = false
    ): Flow<List<MatchModel>>

    fun getTeamListStream(): Flow<List<TeamModel>>

    suspend fun fetchMatchList(): Flow<List<MatchModel>>
    suspend fun fetchTeamList(): Flow<List<TeamModel>>
    fun getTeamStreamByTeamName(homeTeamName: String): Flow<TeamModel?>
    fun getFavoriteStreamByMatch(date: String, home: String, away: String): Flow<MatchFavoriteModel?>

    fun getMatchListStreamByTeam(
        teamName: String
    ): Flow<List<MatchModel>>

    suspend fun saveFavorite(value: MatchFavoriteModel)

}