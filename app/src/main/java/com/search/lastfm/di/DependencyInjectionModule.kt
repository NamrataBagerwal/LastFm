package com.search.lastfm.di

import com.search.lastfm.remote_repository.webservice.album.AlbumApi
import com.search.lastfm.remote_repository.webservice.album.AlbumApiFactory
import com.search.lastfm.remote_repository.webservice.album.AlbumRepositoryImpl
import com.search.lastfm.remote_repository.webservice.artist.ArtistApi
import com.search.lastfm.remote_repository.webservice.artist.ArtistApiFactory
import com.search.lastfm.remote_repository.webservice.artist.ArtistRepositoryImpl
import com.search.lastfm.remote_repository.webservice.song.SongApi
import com.search.lastfm.remote_repository.webservice.song.SongApiFactory
import com.search.lastfm.remote_repository.webservice.song.SongRepositoryImpl
import com.search.lastfm.ui.viewmodel.MainActivityViewModel
import com.search.lastfm.usecase.album.AlbumUsecase
import com.search.lastfm.usecase.artist.ArtistUsecase
import com.search.lastfm.usecase.song.SongUsecase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyInjectionModule {

    // Initializing Repository and Usecase Modules
    val albumModule = module {
        single {
            val albumApi: AlbumApi = AlbumApiFactory.albumApi
            return@single AlbumRepositoryImpl(albumApi)
        }

        single { AlbumUsecase(get() as AlbumRepositoryImpl) }
    }

    val artistModule = module {
        single {
            val artistApi: ArtistApi = ArtistApiFactory.artistApi
            return@single ArtistRepositoryImpl(artistApi)
        }

        single { ArtistUsecase(get() as ArtistRepositoryImpl) }
    }

    val songModule = module {
        single {
            val songApi: SongApi = SongApiFactory.songApi
            return@single SongRepositoryImpl(songApi)
        }
        single { SongUsecase(get() as SongRepositoryImpl) }
    }

    // Initializing ViewModel Modules
    val viewModelModule = module {
        viewModel {
            MainActivityViewModel(get(), get(), get())
        }
    }

}