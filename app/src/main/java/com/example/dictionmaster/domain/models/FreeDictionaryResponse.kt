package com.example.dictionmaster.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(tableName = "free_dictionary_response")
data class FreeDictionaryResponse(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "phonetics")
    val phonetics: List<Phonetic>,
    @ColumnInfo(name = "meanings")
    val meanings: List<Meaning>,
    @ColumnInfo(name = "added_on")
    var addedOn: String
)

fun FreeDictionaryResponse.wordToCapitalized(): String? {
    return try {
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    } catch (e: Exception) {
        null
    }
}

fun FreeDictionaryResponse.getPhoneticText(): String? {
    return try {
        phonetics.first {
            return@first it.text != null && it.text.isNotEmpty()
        }.text
    } catch (e: Exception) {
        null
    }
}

fun FreeDictionaryResponse.getPhoneticAudio(): String? {
    return try {
        phonetics.first {
            return@first it.audio != null && it.audio.isNotEmpty()
        }.audio
    } catch (e: Exception) {
        null
    }
}

fun FreeDictionaryResponse.getDefinitionMockList(): List<DefinitionMock> {
    return listOf(
        DefinitionMock(
            definition = "A process of teaching, training and learning, especially in schools or colleges, to improve knowledge and develop skills.",
            examples = listOf(
                "primary/elementary education",
                "post-secondary education",
                "a college/university education",
                "She completed her formal education in 2019.",
                "Students from lower income families are less likely to continue their education",
                "to further/pursue an education",
                "to get/receive an education",
                "The school provides an axcellent all-round education.",
            ),
            synonyms = emptyList(),
            antonyms = emptyList(),
            types = listOf("uncountable", "countable")
        ),
        DefinitionMock(
            definition = "a particular kind of teaching or training",
            examples = listOf(
                "education about something",
                "education about danger on the roads",
                "The council has launched a new health education campaign.",
                "an alcohol education programme (= to warn of the dangers of alcohol)",
                "Patient education is important to minimize the risk of a second heart attack.",
            ),
            synonyms = emptyList(),
            antonyms = emptyList(),
            types = listOf("uncountable", "singular")
        ),
        DefinitionMock(
            definition = "the institutions or people involved in teaching and training",
            examples = listOf(
                "the Education Department",
                "the Department of Education",
                "There should be closer links between education and industry.",
                "the education secretary",
            ),
            synonyms = emptyList(),
            antonyms = emptyList(),
            types = listOf("uncountable")
        )
    )
}