package com.example.giangnguyen.data

import android.util.Log
import com.example.giangnguyen.data.local.LocalDataSource
import com.example.giangnguyen.data.remote.NetworkError
import com.example.giangnguyen.data.remote.dto.RemoteDataSource
import com.example.giangnguyen.domain.model.MatchFavoriteModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.domain.model.MatchStatus
import com.example.giangnguyen.domain.model.TeamModel
import com.example.giangnguyen.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AppRepository {
    override fun getMatchListStream(): Flow<List<MatchModel>> {
        return localDataSource.getMatchList()
    }

    override suspend fun getMatchListByTeamId(
        teamID: String,
        fetchList: Boolean
    ): Flow<List<MatchModel>> {
        if (fetchList) {
            try {
                val result = remoteDataSource.fetchMatchListByTeamId(teamID)
                val matches = mutableListOf<MatchModel>()
                result.previous.forEach {
                    matches += it.copy(
                        matchStatus = MatchStatus.PREVIOUS
                    )
                }
                result.upcoming.forEach {
                    matches += it.copy(
                        matchStatus = MatchStatus.UPCOMING
                    )
                }
                localDataSource.saveMatchList(matches)
            } catch (e: NetworkError) {
                e.printStackTrace()
            }
        }
        return localDataSource.getMatchList()
    }

    override fun getTeamListStream(): Flow<List<TeamModel>> {
        return localDataSource.getTeamList()
    }

    override suspend fun fetchMatchList(): Flow<List<MatchModel>> = flow {

        val result = remoteDataSource.fetchMatchList()
        val matches = mutableListOf<MatchModel>()
        result.previous.forEach {
            matches += it.copy(
                matchStatus = MatchStatus.PREVIOUS
            )
        }
        result.upcoming.forEach {
            matches += it.copy(
                matchStatus = MatchStatus.UPCOMING
            )
        }
        localDataSource.saveMatchList(matches)
        emit(matches)
    }.flowOn(defaultDispatcher)


    override suspend fun fetchTeamList(): Flow<List<TeamModel>> = flow {
        val result = remoteDataSource.fetchTeamList()
        localDataSource.saveTeamList(result)
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getTeamStreamByTeamName(name: String): Flow<TeamModel?> {
        return localDataSource.getTeamByName(name)
    }

    override fun getFavoriteStreamByMatch(
        date: String,
        home: String,
        away: String
    ): Flow<MatchFavoriteModel?> {
        return localDataSource.getFavoriteStreamByMatch(date, home, away)
    }

    override fun getMatchListStreamByTeam(teamName: String): Flow<List<MatchModel>> {
        return localDataSource.getMatchListStreamByTeam(teamName)
    }

    override suspend fun saveFavorite(value: MatchFavoriteModel) {
        localDataSource.saveFavorite(value)
    }
}