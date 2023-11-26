package com.gmail.in2horizon.insite.db

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dagger.hilt.android.qualifiers.ApplicationContext
import in2horizon.insite.InsiteApplication
import in2horizon.insite.R


@Entity(indices = [Index(value = ["src"],unique=true)])
data class Translation(@PrimaryKey(autoGenerate = true) val tid: Int=0,
//    @ColumnInfo val srclng: String,
//    @ColumnInfo val dstlng: String,
                       @ColumnInfo val src: String= InsiteApplication.appContext.getString(R.string
                           .open_translator),
                       @ColumnInfo val dst: String="",
                       @ColumnInfo val date:String=""

    )
