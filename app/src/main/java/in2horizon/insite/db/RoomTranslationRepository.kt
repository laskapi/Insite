package com.gmail.in2horizon.insite.db

import javax.inject.Inject

class RoomTranslationRepository @Inject constructor(private val translationDao: TranslationDao) : TranslationRepository {



    override fun insertTranslation(src: String, dst: String) {

       translationDao.insertTranslation(src,dst)
        /*val sites = translationDao.getTranslation(url)
        if (sites.isNotEmpty()) {
            translationDao.deleteTranslations(*sites.map { it }.toTypedArray())
        }
            translationDao.insertTranslation(url)*/
    }

    override fun getTranslations(count: Int):List<Translation> {
        return translationDao.getTranslations(count)
    }
    override fun deleteTranslation(translation:Translation){
        return translationDao.deleteTranslations(translation)
    }

    override fun deleteTranslations() {
        return translationDao.deleteTranslations()
    }

}