package com.gmail.in2horizon.insite.db

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    fun insertTranslation(src: String, dst: String)
    fun getTranslations(count: Int = 0): List<Translation>
    fun getAllTranslationsPaged(): Flow<PagingData<Translation>>
    fun deleteTranslation(translation: Translation)
    fun deleteTranslations()

}