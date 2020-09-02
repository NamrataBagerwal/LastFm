package com.search.lastfm.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.search.lastfm.AppConstants
import com.search.lastfm.BuildConfig
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.album.AlbumRepositoryImpl
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
class AlbumUsecaseTest : KoinTest {

    companion object {
        private const val ALBUM_SEARCH = "album.search"
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

    private val albumUsecase: AlbumUsecase by inject()

    private lateinit var albumRepository: BaseRepository

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

        albumRepository = declareMock<AlbumRepositoryImpl>()
    }

    @Test
    fun `Verify AlbumUsecase called with search query passed from MainActivityViewModel calls AlbumRepository Search Album`() {

        Assert.assertNotNull(
            "Verify AlbumUsecase called with search query passed from MainActivityViewModel calls AlbumRepository Search Album",
            albumRepository as AlbumRepositoryImpl
        )

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(TestData.getAlbumApiTestResponse())
                .`when`(albumRepository as AlbumRepositoryImpl)
                .searchAlbum(
                    ALBUM_SEARCH, TestData.SEARCH_QUERY, BuildConfig.API_KEY,
                    AppConstants.JSON_FORMAT
                )

            albumUsecase.searchAlbum(TestData.SEARCH_QUERY)

            verify(albumRepository as AlbumRepositoryImpl).searchAlbum(
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