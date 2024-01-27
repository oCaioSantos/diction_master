package com.example.dictionmaster.domain.repository

import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import retrofit2.Response

interface ILocalCacheRepository {

    suspend fun getWordsCountFromCache(date: String): Response<Int>

    suspend fun saveMeaningsInCache(meanings: FreeDictionaryResponse)

    suspend fun getMeaningsFromCache(word: String): Response<List<FreeDictionaryResponse>>

    suspend fun cleanCacheDatabase(date: String): Response<Int>

}