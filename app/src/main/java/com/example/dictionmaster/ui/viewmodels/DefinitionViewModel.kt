package com.example.dictionmaster.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionmaster.common.utils.Utils
import com.example.dictionmaster.domain.repository.IFreeDictionaryRepository
import com.example.dictionmaster.domain.exceptions.WordLimitExceededException
import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import com.example.dictionmaster.domain.repository.ILocalCacheRepository
import com.example.dictionmaster.ui.viewholders.DefinitionViewHolder
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class DefinitionViewModel(
    private val localCacheRepository: ILocalCacheRepository,
    private val remoteRepository: IFreeDictionaryRepository
) : ViewModel() {

    private val _state = MutableLiveData<DefinitionViewHolder>()
    val state: LiveData<DefinitionViewHolder> = _state

    private val _removeSplashScreen = MutableLiveData<Boolean>()
    val removeSplashScreen: LiveData<Boolean> = _removeSplashScreen

    fun cleanCacheDatabase() {
        viewModelScope.launch {
            try {
                val response = localCacheRepository.cleanCacheDatabase(
                    Utils.getTodayDate()
                )
                if (response.isSuccessful) {
                    _removeSplashScreen.value = true
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun doSearchByWord(word: String) {
        viewModelScope.launch {
            try {
                _state.value = DefinitionViewHolder.Loading
                val meaningFromCache = getMeaningsFromCache(word)
                if (meaningFromCache == null) {
                    if (wordsLimitExceeded()) {
                        throw WordLimitExceededException()
                    }
                }
                val response = meaningFromCache ?: getMeaningsFromNetwork(word)
                response?.let {
                    if (it.isSuccessful)
                        _state.value = DefinitionViewHolder.Success(
                            it.body()!!.first()
                        )
                    else throw HttpException(response)
                } ?: run {
                    _state.value = DefinitionViewHolder.NoDefinition(word)
                }
            } catch (e: Exception) {
                when (e) {
                    is WordLimitExceededException -> {
                        _state.value = DefinitionViewHolder.WordLimitExceeded
                    }

                    is HttpException -> {
                        if (e.code() == 404)
                            _state.value = DefinitionViewHolder.NoDefinition(word)
                        else
                            _state.value = DefinitionViewHolder.Error
                    }

                    else -> {
                        _state.value = DefinitionViewHolder.Error
                    }
                }
            }
        }
    }

    private suspend fun wordsLimitExceeded(): Boolean {
        return try {
            val response = localCacheRepository.getWordsCountFromCache(
                Utils.getTodayDate()
            )
            if (!response.isSuccessful) throw HttpException(response)
            response.body()?.let {
                it >= 10
            } ?: run {
                false
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getMeaningsFromNetwork(word: String): Response<List<FreeDictionaryResponse>>? {
        return try {
            val response = remoteRepository.getMeaningsFromNetwork(word)
            if (!response.isSuccessful) throw HttpException(response)
            response.body()?.let {
                if (it.isNotEmpty()) {
                    localCacheRepository.saveMeaningsInCache(
                        response.body()!!.first().apply {
                            addedOn = Utils.getTodayDate()
                        }
                    )
                    response
                } else null
            } ?: run {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getMeaningsFromCache(word: String): Response<List<FreeDictionaryResponse>>? {
        return try {
            val response = localCacheRepository.getMeaningsFromCache(word)
            if (!response.isSuccessful) throw HttpException(response)
            response.body()?.let {
                if (it.isNotEmpty()) response
                else null
            } ?: run {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

}