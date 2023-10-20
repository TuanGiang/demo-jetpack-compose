package com.example.giangnguyen.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "matchFavorite", primaryKeys = ["dateString", "home", "away"])
data class MatchFavoriteModel(
    val dateString: String,
    val home: String,
    val away: String,
    val isFavorite: Boolean = false
)