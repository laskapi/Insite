package com.gmail.in2horizon.insite.db

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomTranslationRepository @Inject constructor(
    private val translationDao: TranslationDao,
    private val translationPager: Pager<Int, Translation>
) :
    TranslationRepository {


    override fun insertTranslation(src: String, dst: String) {

        translationDao.insertTranslation(src, dst)
        /*val sites = translationDao.getTranslation(url)
        if (sites.isNotEmpty()) {
            translationDao.deleteTranslations(*sites.map { it }.toTypedArray())
        }
            translationDao.insertTranslation(url)*/
    }

    override fun getTranslations(count: Int): List<Translation> {
        return translationDao.getTranslations(count)
    }

    override fun getAllTranslationsPaged(): Flow<PagingData<Translation>> {
        return translationPager.flow
            //.map {pagingData->
            //pagingData.map{it.}
    }


    override fun deleteTranslation(translation: Translation) {
        return translationDao.deleteTranslations(translation)
    }

    override fun deleteTranslations() {
        return translationDao.deleteTranslations()
    }

}