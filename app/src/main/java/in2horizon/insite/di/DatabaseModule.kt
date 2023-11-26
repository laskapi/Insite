package com.gmail.in2horizon.insite.di

import android.content.Context
import androidx.room.Room
import com.gmail.in2horizon.insite.TranslationDatabase
import com.gmail.in2horizon.insite.db.TranslationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTranslationDao(translationDatabase: TranslationDatabase): TranslationDao {
        return translationDatabase.translationDao()
    }

    @Provides
    @Singleton
    fun provideTranslationDatabase(@ApplicationContext appContext: Context): TranslationDatabase {
        return Room.databaseBuilder(appContext, TranslationDatabase::class.java,"TranslationDB")
            .build()
    }
}