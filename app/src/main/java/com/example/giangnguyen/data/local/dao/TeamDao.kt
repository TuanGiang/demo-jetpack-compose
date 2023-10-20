package com.example.giangnguyen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giangnguyen.domain.model.TeamModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getTeams(): Flow<List<TeamModel>>

    @Query("SELECT * FROM teams WHERE name =:name")
    fun getTeamByName(name: String): Flow<TeamModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(teams: List<TeamModel>)

}