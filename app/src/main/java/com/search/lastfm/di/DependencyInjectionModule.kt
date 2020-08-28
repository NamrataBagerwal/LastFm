package com.search.lastfm.di

import org.koin.dsl.module

object DependencyInjectionModule {

    val repositoryModule = module {
        single {
//            val factsApi: FactsApi = FactsApiFactory.factsApi
//            return@single FactsRepositoryImpl(factsApi)
        }
    }

    val viewModelModule = module {
//        viewModel { FactsViewModel(get()) }
    }

}