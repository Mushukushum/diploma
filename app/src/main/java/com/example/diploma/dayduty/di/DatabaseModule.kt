package com.example.diploma.dayduty.di

import android.content.Context
import androidx.room.Room
import com.example.diploma.animals.data_source.AnimalsDataSource
import com.example.diploma.animals.data_source.AnimalsDataSourceImpl
import com.example.diploma.animals.database.AnimalsDatabase
import com.example.diploma.dayduty.data.DayDutiesDatabase
import com.example.diploma.dayduty.domain.model.repository.DataSource
import com.example.diploma.dayduty.domain.model.repository.DataSourceImpl
import com.example.diploma.map.data.MapDatabase
import com.example.diploma.map.data.repository.MapDataSource
import com.example.diploma.map.data.repository.MapDataSourceImpl
import com.example.diploma.util.Constants.ANIMALS_DATABASE
import com.example.diploma.util.Constants.DAY_DUTIES_DATABASE
import com.example.diploma.util.Constants.MAP_CROP_TABLE
import com.example.diploma.util.Constants.MAP_DATABASE
import com.example.diploma.util.Constants.STATISTICS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DayDutiesDatabase {
        return Room.databaseBuilder(
            context ,
            DayDutiesDatabase::class.java ,
            DAY_DUTIES_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideMapDatabase(
        @ApplicationContext context: Context
    ): MapDatabase {
        return Room.databaseBuilder(
            context,
            MapDatabase::class.java,
            MAP_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAnimalsDatabase(
        @ApplicationContext context: Context
    ): AnimalsDatabase {
        return Room.databaseBuilder(
            context,
            AnimalsDatabase::class.java,
            ANIMALS_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

//    @Provides
//    @Singleton
//    fun provideStatisticsDatabase(
//        @ApplicationContext context: Context
//    ): StatisticsDatabase{
//        return Room.databaseBuilder(
//            context,
//            StatisticsDatabase::class.java,
//            STATISTICS_DATABASE
//        ).fallbackToDestructiveMigration().build()
//    }

    @Provides
    @Singleton
    fun provideDataSource(
        database: DayDutiesDatabase
    ): DataSource {
        return DataSourceImpl(
            dayDutiesDatabase = database
        )
    }

    @Provides
    @Singleton
    fun provideMapDataSource(
        database: MapDatabase
    ): MapDataSource{
        return MapDataSourceImpl(
            mapDatabase = database
        )
    }

    @Provides
    @Singleton
    fun provideAnimalsDataSource(
        database: AnimalsDatabase
    ): AnimalsDataSource {
        return AnimalsDataSourceImpl (
            animalsDatabase = database
        )
    }

//    @Provides
//    @Singleton
//    fun provideStatisticsDataSource(
//        database: StatisticsDatabase
//    ): StatisticsDataSource {
//        return StatisticsDataSourceImpl (
//            statisticsDatabase = database
//        )
//    }
}