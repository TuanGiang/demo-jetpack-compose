package com.example.giangnguyen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giangnguyen.domain.model.MatchFavoriteModel
import kotlinx.coroutines.flow.Flow


@Dao
interface MatchFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(favoriteModel: MatchFavoriteModel)

    @Query("SELECT * FROM matchFavorite")
    fun getMatches(): Flow<List<MatchFavoriteModel>>

    @Query("SELECT * FROM matchFavorite WHERE dateString =:date AND home =:home AND away =:away ")
    fun getFavoriteByMatch(date: String, home: String, away: String): Flow<MatchFavoriteModel?>
}