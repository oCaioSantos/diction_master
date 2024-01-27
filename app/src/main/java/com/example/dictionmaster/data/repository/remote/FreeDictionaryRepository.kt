package com.example.dictionmaster.data.repository.remote

import com.example.dictionmaster.domain.repository.IFreeDictionaryRepository
import com.example.dictionmaster.data.services.FreeDictionaryService
import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.await

class FreeDictionaryRepository(
    private val api: FreeDictionaryService
) : IFreeDictionaryRepository {

    override suspend fun getMeaningsFromNetwork(word: String): Response<List<FreeDictionaryResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMeanings(word).await()
                Response.success(response)
            } catch (e: Exception) {
                var code = 500
                if (e is HttpException) {
                    code = e.code()
                }
                Response.error(
                    code,
                    ResponseBody.create(
                        null,
                        e.message ?: "Error"
                    )
                )
            }
        }
    }

}