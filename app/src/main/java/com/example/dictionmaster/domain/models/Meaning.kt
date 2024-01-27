package com.example.dictionmaster.domain.models

data class Meaning(
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>,
)
