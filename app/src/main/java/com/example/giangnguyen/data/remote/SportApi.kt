package com.example.giangnguyen.data.remote

import com.example.giangnguyen.data.remote.dto.MatchListResponse
import com.example.giangnguyen.data.remote.dto.TeamListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SportApi {
    @GET("/teams")
    suspend fun getTeams(
    ): Response<TeamListResponse>

    @GET("/teams/matches")
    suspend fun getMatches(
    ): Response<MatchListResponse>

    @GET("/teams/{teamId}/matches")
    suspend fun getMatchesByTeamId(@Path(value = "teamId") teamId: String): Response<MatchListResponse>
}