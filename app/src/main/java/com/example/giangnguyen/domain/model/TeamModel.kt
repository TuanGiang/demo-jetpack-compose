package com.example.giangnguyen.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val logo: String
)
