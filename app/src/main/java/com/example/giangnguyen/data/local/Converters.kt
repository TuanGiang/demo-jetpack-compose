package com.example.giangnguyen.data.local

import androidx.room.TypeConverter
import com.example.giangnguyen.domain.model.MatchStatus

class Converters {
    @TypeConverter
    fun toStatus(value: Int) = enumValues<MatchStatus>()[value]

    @TypeConverter
    fun fromStatus(value: MatchStatus) = value.ordinal
}