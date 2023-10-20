package com.example.giangnguyen.data.local

import com.example.giangnguyen.data.local.dao.MatchDao
import com.example.giangnguyen.data.local.dao.MatchFavoriteDao
import com.example.giangnguyen.data.local.dao.TeamDao
import com.example.giangnguyen.domain.model.MatchFavoriteModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.domain.model.TeamModel
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val matchDao: MatchDao,
    private val teamDao: TeamDao,
    private val matchFavoriteDao: MatchFavoriteDao
) {
    fun getMatchList(): Flow<List<MatchModel>> {
        return matchDao.getMatches()
    }

    suspend fun getMatchListByTeamId(
        teamID: String
    ): Flow<List<MatchModel>> {
        return matchDao.getMatches()
    }

    fun getTeamList(): Flow<List<TeamModel>> {
        return teamDao.getTeams()
    }

    fun getTeamByName(name: String): Flow<TeamModel?> {
        return teamDao.getTeamByName(name)
    }

    suspend fun saveMatchList(matches: List<MatchModel>) {
        matchDao.insertAll(matches)
    }

    fun saveTeamList(result: List<TeamModel>) {
        teamDao.insertAll(result)
    }

    fun getMatchListStreamByTeam(name: String): Flow<List<MatchModel>> {
        return matchDao.getMatchesByTeamName(name)
    }

    fun getFavoriteStreamByMatch(date: String, home: String, away: String): Flow<MatchFavoriteModel?> {
        return matchFavoriteDao.getFavoriteByMatch(date, home, away)
    }

    fun saveFavorite(value: MatchFavoriteModel) {
        return matchFavoriteDao.insertOrUpdate(value)
    }

}