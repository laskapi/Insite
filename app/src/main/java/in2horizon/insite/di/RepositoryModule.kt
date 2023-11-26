package com.gmail.in2horizon.insite.di

import com.gmail.in2horizon.insite.db.RoomTranslationRepository
import com.gmail.in2horizon.insite.db.TranslationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesTranslationRepository(impl: RoomTranslationRepository): TranslationRepository


}