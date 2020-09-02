package com.search.lastfm

import android.app.Application
import com.search.lastfm.di.DependencyInjectionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LastFmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // get list of all modules
        val diModuleList = listOf(
            DependencyInjectionModule.albumModule,
            DependencyInjectionModule.artistModule,
            DependencyInjectionModule.songModule,
            DependencyInjectionModule.viewModelModule
        )
        // start koin with the module list
        startKoin {
            // Android context
            androidContext(this@LastFmApplication)
            // modules
            modules(diModuleList)
        }
    }
}