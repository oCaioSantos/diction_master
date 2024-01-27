package com.example.dictionmaster.domain.models

data class DefinitionMock (
    val definition: String,
    val examples: List<String>,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val types: List<String>,
)