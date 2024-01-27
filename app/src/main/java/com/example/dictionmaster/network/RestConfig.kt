package com.example.dictionmaster.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestConfig {

    private const val BASE_URL =
        "https://api.dictionaryapi.dev/api/v2/entries/en/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}