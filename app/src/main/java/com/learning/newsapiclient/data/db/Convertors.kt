package com.learning.newsapiclient.data.db

import androidx.room.TypeConverter
import com.learning.newsapiclient.data.model.Source

class Convertors {
    // Here we will define a function to return the name of the source instead of source object.
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    /**
     * we also need to create another function to get an source
     * instance from the room table when retrieving data
     */

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}