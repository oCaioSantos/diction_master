package com.example.dictionmaster.ui.viewholders

import com.example.dictionmaster.domain.models.FreeDictionaryResponse

sealed class DefinitionViewHolder {

    data object Loading : DefinitionViewHolder()
    class Success(val data: FreeDictionaryResponse) : DefinitionViewHolder()
    data object Error : DefinitionViewHolder()
    class NoDefinition(val word: String) : DefinitionViewHolder()
    data object WordLimitExceeded : DefinitionViewHolder()

}