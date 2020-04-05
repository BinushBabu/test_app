package com.example.testbase.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class SuggestionConvector {

    @TypeConverter
    fun listToJson(value: List<SuggestionItem>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<SuggestionItem>? {
        val objects = Gson().fromJson(value, Array<SuggestionItem>::class.java) as Array<SuggestionItem>
        val list = objects.toList()
        return list
    }
}