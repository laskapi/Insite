package com.gmail.in2horizon.insite.db

interface TranslationRepository {
    fun insertTranslation(src: String, dst: String)
    fun getTranslations(count: Int = 0): List<Translation>
    fun deleteTranslation(translation: Translation)
    fun deleteTranslations()
}