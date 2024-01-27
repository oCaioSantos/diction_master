package com.example.dictionmaster.data.repository.local

import com.example.dictionmaster.data.dao.FreeDictionaryCacheDao
import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import com.example.dictionmaster.domain.repository.ILocalCacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class LocalCacheRepository(
    private val freeDictionaryCacheDao: FreeDictionaryCacheDao
) : ILocalCacheRepository {

    override suspend fun getMeaningsFromCache(word: String): Response<List<FreeDictionaryResponse>> {
        return try {
            val response = freeDictionaryCacheDao.getMeaningsFromCache(word)
            Response.success(response)
        } catch (e: Exception) {
            Response.error(
                404,
                ResponseBody.create(
                    null,
                    e.message ?: "Error"
                )
            )
        }
    }

    override suspend fun saveMeaningsInCache(meanings: FreeDictionaryResponse) {
        withContext(Dispatchers.IO) {
            freeDictionaryCacheDao.saveMeaningsInCache(meanings)
        }
    }

    override suspend fun getWordsCountFromCache(date: String): Response<Int> {
        return withContext(Dispatchers.IO) {
            try {
                Response.success(
                    freeDictionaryCacheDao.getWordsCount(date)
                )
            } catch (e: Exception) {
                Response.error(
                    500,
                    ResponseBody.create(
                        null,
                        e.message ?: "Error"
                    )
                )
            }
        }
    }

    override suspend fun cleanCacheDatabase(date: String): Response<Int> {
        return withContext(Dispatchers.IO) {
            try {
                Response.success(
                    freeDictionaryCacheDao.cleanCacheDatabase(date)
                )
            } catch (e: Exception) {
                Response.error(
                    500,
                    ResponseBody.create(
                        null,
                        e.message ?: "Error"
                    )
                )
            }
        }
    }

}