package com.example.dictionmaster.domain

import androidx.room.TypeConverter
import com.example.dictionmaster.domain.models.Definition
import com.example.dictionmaster.domain.models.Meaning
import com.example.dictionmaster.domain.models.Phonetic
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromPhoneticList(phoneticList: List<Phonetic>): String {
        return gson.toJson(phoneticList)
    }

    @TypeConverter
    fun toPhoneticList(phoneticList: String): List<Phonetic> {
        return gson.fromJson(phoneticList, Array<Phonetic>::class.java).toList()
    }

    @TypeConverter
    fun fromMeaningList(meaningList: List<Meaning>): String {
        return gson.toJson(meaningList)
    }

    @TypeConverter
    fun toMeaningList(meaningList: String): List<Meaning> {
        return gson.fromJson(meaningList, Array<Meaning>::class.java).toList()
    }

    @TypeConverter
    fun fromDefinitionList(definitionList: List<Definition>): String {
        return gson.toJson(definitionList)
    }

    @TypeConverter
    fun toDefinitionList(definitionList: String): List<Definition> {
        return gson.fromJson(definitionList, Array<Definition>::class.java).toList()
    }

}