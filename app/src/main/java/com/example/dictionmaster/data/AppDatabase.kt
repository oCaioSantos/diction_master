package com.example.dictionmaster.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionmaster.data.dao.FreeDictionaryCacheDao
import com.example.dictionmaster.domain.Converters
import com.example.dictionmaster.domain.models.FreeDictionaryResponse

@Database(
    entities = [FreeDictionaryResponse::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun freeDictionaryCacheDao(): FreeDictionaryCacheDao

    companion object {
        private const val DATABASE_NAME = "diction-master-database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}