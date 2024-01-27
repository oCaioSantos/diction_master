package com.example.dictionmaster.domain.models

data class Definition(
    val definition: String,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val example: String
)
