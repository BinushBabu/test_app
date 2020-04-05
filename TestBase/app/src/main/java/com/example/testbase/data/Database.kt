package com.example.testbase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [PersonalData::class], version = 1, exportSchema = false
)
@TypeConverters(SuggestionConvector::class)
abstract class Database : RoomDatabase() {

    abstract fun personalDao(): PersonalDao


}

