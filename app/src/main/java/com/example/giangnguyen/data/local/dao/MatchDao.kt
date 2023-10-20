package com.example.giangnguyen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giangnguyen.domain.model.MatchModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(matches: List<MatchModel>)

    @Query("SELECT * FROM matches")
    fun getMatches(): Flow<List<MatchModel>>

    @Query("SELECT * FROM matches WHERE home =:name OR away =:name")
    fun getMatchesByTeamName(name: String): Flow<List<MatchModel>>
}