package com.example.dictionmaster.di

import com.example.dictionmaster.data.AppDatabase
import com.example.dictionmaster.data.repository.local.LocalCacheRepository
import com.example.dictionmaster.domain.repository.IFreeDictionaryRepository
import com.example.dictionmaster.data.repository.remote.FreeDictionaryRepository
import com.example.dictionmaster.data.services.FreeDictionaryService
import com.example.dictionmaster.domain.repository.ILocalCacheRepository
import com.example.dictionmaster.network.RestConfig
import com.example.dictionmaster.ui.viewmodels.DefinitionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {

    single {
        AppDatabase.getInstance(androidContext())
    }

    single {
        RestConfig
            .getInstance()
            .create(FreeDictionaryService::class.java)
    }

    single<IFreeDictionaryRepository> {
        FreeDictionaryRepository(
            get()
        )
    }

    single<ILocalCacheRepository> {
        LocalCacheRepository(
            get<AppDatabase>().freeDictionaryCacheDao()
        )
    }

    viewModel {
        DefinitionViewModel(get(), get())
    }
}