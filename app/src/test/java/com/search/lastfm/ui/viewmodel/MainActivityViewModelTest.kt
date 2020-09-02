package com.search.lastfm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.util.TestCoroutineRule
import com.search.lastfm.ui.util.TestData
import com.search.lastfm.usecase.AlbumUsecase
import com.search.lastfm.usecase.ArtistUsecase
import com.search.lastfm.usecase.SongUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest : KoinTest {

    companion object {
        private const val SEARCH_QUERY = "name"
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val viewModel: MainActivityViewModel by inject()

    private lateinit var songUsecase: SongUsecase
    private lateinit var albumUsecase: AlbumUsecase
    private lateinit var artistUsecase: ArtistUsecase

    @Mock
    private lateinit var songObserver: Observer<List<SongDto>>

    @Mock
    private lateinit var albumObserver: Observer<List<AlbumDto>>

    @Mock
    private lateinit var artistObserver: Observer<List<ArtistDto>>

    @Before
    fun setUp() {
        startKoin {
            modules(
                listOf(
                    DependencyInjectionModule.albumModule,
                    DependencyInjectionModule.artistModule,
                    DependencyInjectionModule.songModule,
                    DependencyInjectionModule.viewModelModule
                )
            )
        }

        songUsecase = declareMock()
        albumUsecase = declareMock()
        artistUsecase = declareMock()

    }

    @Test
    fun `Verify viewModel updateArtistLiveData calls ArtistUsecase with search query passed from MainActivity SearchView and returns some list`() {

        testCoroutineRule.runBlockingTest {
            doReturn(TestData.getArtistListTestResponse())
                .`when`(artistUsecase)
                .searchArtist(SEARCH_QUERY)

            viewModel.getArtistLiveData().observeForever(artistObserver)
            viewModel.updateArtistLiveData(SEARCH_QUERY)

            verify(artistUsecase).searchArtist(anyString())
            verify(artistObserver).onChanged(TestData.getArtistListTestResponse())

            viewModel.getArtistLiveData().removeObserver(artistObserver)
        }
    }

    @Test
    fun `Verify viewModel getAlbumLiveData() calls AlbumUsecase with search query passed from MainActivity SearchView and returns some list`() {

        testCoroutineRule.runBlockingTest {
            doReturn(TestData.getAlbumListTestResponse())
                .`when`(albumUsecase)
                .searchAlbum(SEARCH_QUERY)

            viewModel.getAlbumLiveData().observeForever(albumObserver)
            viewModel.updateAlbumLiveData(SEARCH_QUERY)

            verify(albumUsecase).searchAlbum(anyString())
            verify(albumObserver).onChanged(TestData.getAlbumListTestResponse())

            viewModel.getAlbumLiveData().removeObserver(albumObserver)
        }
    }

    @Test
    fun `Verify viewModel getSongLiveData() calls SongUsecase with search query passed from MainActivity SearchView and returns some list`() {

        testCoroutineRule.runBlockingTest {
            doReturn(TestData.getSongListTestResponse())
                .`when`(songUsecase)
                .searchSong(SEARCH_QUERY)

            viewModel.getSongLiveData().observeForever(songObserver)
            viewModel.updateSongLiveData(SEARCH_QUERY)

            verify(songUsecase).searchSong(anyString())
            verify(songObserver).onChanged(TestData.getSongListTestResponse())

            viewModel.getSongLiveData().removeObserver(songObserver)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}