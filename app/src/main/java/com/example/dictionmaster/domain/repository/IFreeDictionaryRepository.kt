package com.example.dictionmaster.domain.repository

import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import retrofit2.Response

interface IFreeDictionaryRepository {

    suspend fun getMeaningsFromNetwork(word: String): Response<List<FreeDictionaryResponse>>

}