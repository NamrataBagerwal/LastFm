package com.search.lastfm.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.search.lastfm.AppConstants
import com.search.lastfm.BuildConfig
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.artist.ArtistRepositoryImpl
import com.search.lastfm.ui.util.TestCoroutineRule
import com.search.lastfm.ui.util.TestData
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class ArtistUsecaseTest : KoinTest {

    companion object {
        private const val ARTIST_SEARCH = "artist.search"
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

    private val artistUsecase: ArtistUsecase by inject()

    private lateinit var artistRepository: BaseRepository

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

        artistRepository = declareMock<ArtistRepositoryImpl>()
    }

    @Test
    fun `Verify ArtistUsecase called with search query passed from MainActivityViewModel calls ArtistRepository Search Artist`() {

        Assert.assertNotNull(
            "Verify ArtistUsecase called with search query passed from MainActivityViewModel calls ArtistRepository Search Artist",
            artistRepository as ArtistRepositoryImpl
        )

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(TestData.getArtistApiTestResponse())
                .`when`(artistRepository as ArtistRepositoryImpl)
                .searchArtist(
                    ARTIST_SEARCH, TestData.SEARCH_QUERY, BuildConfig.API_KEY,
                    AppConstants.JSON_FORMAT
                )

            artistUsecase.searchArtist(TestData.SEARCH_QUERY)

            verify(artistRepository as ArtistRepositoryImpl).searchArtist(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}