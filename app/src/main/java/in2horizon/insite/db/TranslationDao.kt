package com.gmail.in2horizon.insite.db

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface TranslationDao {

/*
    @Query("SELECT * FROM translation ORDER BY date DESC")
    fun getTranslations(): List<Translation>
*/

    @Query("SELECT * FROM translation WHERE src = :src")
    fun getTranslation(src:String):List<Translation>

    @Query("INSERT INTO translation(src,dst,date) VALUES(:src,:dst,datetime('now','localtime'))")
    fun insertTranslation(src: String, dst: String)

    @Delete
    fun deleteTranslations(vararg translations:Translation)

    @Query("DELETE FROM translation")
    fun deleteTranslations()

    @Query("SELECT * FROM translation ORDER BY date DESC LIMIT :count")
    fun getTranslations(count: Int):List<Translation>

    @Query("SELECT * FROM translation ORDER BY date DESC")
    fun pagingSource():PagingSource<Int,Translation>

}

























