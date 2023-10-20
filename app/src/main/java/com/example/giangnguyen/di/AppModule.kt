package com.example.giangnguyen.di

import android.content.Context
import com.example.giangnguyen.data.DefaultRepository
import com.example.giangnguyen.data.local.AppDatabase
import com.example.giangnguyen.data.local.LocalDataSource
import com.example.giangnguyen.data.local.dao.MatchDao
import com.example.giangnguyen.data.local.dao.MatchFavoriteDao
import com.example.giangnguyen.data.local.dao.TeamDao
import com.example.giangnguyen.data.remote.dto.RemoteDataSource
import com.example.giangnguyen.data.remote.SportApi
import com.example.giangnguyen.domain.repository.AppRepository
import com.example.giangnguyen.presentation.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun providesNavigationManager() = NavigationManager()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideMatchDao(appDatabase: AppDatabase): MatchDao {
        return appDatabase.matchDao()
    }

    @Provides
    fun provideMatchFavoriteDao(appDatabase: AppDatabase): MatchFavoriteDao {
        return appDatabase.matchFavoriteDao()
    }

    @Provides
    fun provideTeamDao(appDatabase: AppDatabase): TeamDao {
        return appDatabase.teamDao()
    }

    @Provides
    fun provideLocalDataSource(
        matchDao: MatchDao,
        teamDao: TeamDao,
        favoriteDao: MatchFavoriteDao
    ): LocalDataSource {
        return LocalDataSource(matchDao, teamDao, favoriteDao)
    }

    @Provides
    fun provideRemoteDataSource(sportApi: SportApi): RemoteDataSource {
        return RemoteDataSource(sportApi);
    }

    @Singleton
    @Provides
    fun provideAppRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): AppRepository {
        return DefaultRepository(localDataSource, remoteDataSource);
    }


}