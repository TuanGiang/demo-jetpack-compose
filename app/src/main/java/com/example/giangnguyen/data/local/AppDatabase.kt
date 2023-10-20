package com.example.giangnguyen.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.giangnguyen.data.local.dao.MatchDao
import com.example.giangnguyen.data.local.dao.MatchFavoriteDao
import com.example.giangnguyen.data.local.dao.TeamDao
import com.example.giangnguyen.domain.model.MatchFavoriteModel
import com.example.giangnguyen.domain.model.MatchModel
import com.example.giangnguyen.domain.model.TeamModel

@Database(
    entities = [MatchModel::class, TeamModel::class, MatchFavoriteModel::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun teamDao(): TeamDao
    abstract fun matchFavoriteDao(): MatchFavoriteDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "SPORT.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}
