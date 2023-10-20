package com.example.giangnguyen.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

enum class MatchStatus(val value: Int) {
    UPCOMING(0),
    PREVIOUS(1)
}

@Entity(tableName = "matches", primaryKeys = ["dateString", "home", "away"])
data class MatchModel(
    @SerializedName("date")
    val dateString: String,
    val description: String,
    val home: String,
    val away: String,
    val winner: String?,
    val highlights: String?,
    val matchStatus: MatchStatus = MatchStatus.UPCOMING,
)