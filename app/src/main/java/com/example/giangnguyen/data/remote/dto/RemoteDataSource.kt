package com.example.giangnguyen.data.remote.dto

import android.util.Log
import com.example.giangnguyen.data.remote.NetworkResult
import com.example.giangnguyen.data.remote.SportApi
import com.example.giangnguyen.data.remote.safeCallApi
import com.example.giangnguyen.domain.model.TeamModel

class RemoteDataSource(private val sportApi: SportApi) {
    suspend fun fetchMatchList(): MatchesResponse {
        val result = safeCallApi {
            sportApi.getMatches()
        }
        when (result) {
            is NetworkResult.Success -> {
                return result.data.matches
            }

            is NetworkResult.Error -> {
                throw result.error
            }
        }
    }

    suspend fun fetchMatchListByTeamId(teamId: String): MatchesResponse {
        val result = safeCallApi {
            sportApi.getMatchesByTeamId(teamId)
        }
        when (result) {
            is NetworkResult.Success -> {
                return result.data.matches
            }

            is NetworkResult.Error -> {
                throw result.error
            }
        }
    }

    suspend fun fetchTeamList(): List<TeamModel> {
        Log.i("AAAAAA", "fetchTeamList:")

        val result = safeCallApi {
            sportApi.getTeams()
        }
        when (result) {
            is NetworkResult.Success -> {
                return result.data.teams
            }

            is NetworkResult.Error -> {
                throw result.error
            }
        }
    }
}