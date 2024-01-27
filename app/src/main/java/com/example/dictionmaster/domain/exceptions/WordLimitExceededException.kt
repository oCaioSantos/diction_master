package com.example.dictionmaster.domain.exceptions

class WordLimitExceededException : Exception() {
    override val message: String
        get() = "You have exceeded the limit of words per day"
}