package com.gmail.in2horizon.insite

import androidx.room.*
import com.gmail.in2horizon.insite.db.Translation
import com.gmail.in2horizon.insite.db.TranslationDao

@Database(entities = [Translation::class],version=2,
    exportSchema = true,
  //  autoMigrations = [AutoMigration(1,2)]
)
abstract class TranslationDatabase: RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}


/*

object Converters {(
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }
}*/
