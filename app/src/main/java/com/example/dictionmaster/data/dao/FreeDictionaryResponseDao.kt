package com.example.dictionmaster.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dictionmaster.domain.models.FreeDictionaryResponse

@Dao
interface FreeDictionaryCacheDao {

    @Query("SELECT * FROM free_dictionary_response WHERE word = :word")
    suspend fun getMeaningsFromCache(word: String): List<FreeDictionaryResponse>

    @Insert
    suspend fun saveMeaningsInCache(freeDictionaryResponse: FreeDictionaryResponse)

    @Delete
    suspend fun deleteMeaningsFromCache(freeDictionaryResponse: FreeDictionaryResponse)

    @Update
    suspend fun updateMeaningsInCache(freeDictionaryResponse: FreeDictionaryResponse)

    @Query("SELECT COUNT(*) FROM free_dictionary_response WHERE added_on = :date")
    suspend fun getWordsCount(date: String): Int

    @Query("DELETE FROM free_dictionary_response WHERE added_on < :date")
    suspend fun cleanCacheDatabase(date: String): Int

}