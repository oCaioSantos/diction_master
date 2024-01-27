package com.example.dictionmaster.data.services

import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import com.example.dictionmaster.domain.models.Meaning
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FreeDictionaryService {

    @GET("{word}")
    fun getMeanings(
        @Path("word") word: String
    ): Call<List<FreeDictionaryResponse>>

}