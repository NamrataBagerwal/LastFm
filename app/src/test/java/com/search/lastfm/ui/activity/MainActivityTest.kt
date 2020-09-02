package com.search.lastfm.ui.activity

import android.os.Build
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainActivityTest : KoinTest {

    companion object {
        private const val IS_NOT_VISIBLE = "is not visible"
        private const val IS_VISIBLE = "is visible"
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Mock
    private lateinit var songListLiveData: LiveData<List<SongDto>>

    @Captor
    private lateinit var songObserverCaptor: ArgumentCaptor<Observer<List<SongDto>>>

    @Mock
    private lateinit var albumListLiveData: LiveData<List<AlbumDto>>

    @Captor
    private lateinit var albumObserverCaptor: ArgumentCaptor<Observer<List<AlbumDto>>>

    @Mock
    private lateinit var artistListLiveData: LiveData<List<ArtistDto>>

    @Captor
    private lateinit var artistObserverCaptor: ArgumentCaptor<Observer<List<ArtistDto>>>

    @Before
    fun setUp() {
        koinApplication {
            modules(
                listOf(
                    DependencyInjectionModule.albumModule,
                    DependencyInjectionModule.artistModule,
                    DependencyInjectionModule.songModule,
                    DependencyInjectionModule.viewModelModule
                )
            )
        }

        viewModel = declareMock {
            given(this.getSongLiveData()).willReturn(songListLiveData)
            given(this.getAlbumLiveData()).willReturn(albumListLiveData)
            given(this.getArtistLiveData()).willReturn(artistListLiveData)

        }

        activityScenario = launchActivity()
        activityScenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun `has visible home page music cover view on create`() {

        activityScenario.onActivity { activity ->
            Assert.assertEquals(
                "Home Page Music Cover $IS_VISIBLE",
                View.VISIBLE,
                activity.imageViewHomePage.visibility
            )
        }
    }

    @Test
    fun `has recycler view and their respective title's parent layout hidden on create`() {
        activityScenario.onActivity { activity ->
            Assert.assertEquals(
                "linearLayoutSearchResults $IS_NOT_VISIBLE",
                View.GONE,
                activity.linearLayoutSearchResults.visibility
            )
        }
    }

    // TODO: Fix this Test Case
//    @Test
//    fun `search result is displayed for some query`() {
//
//        val songList = TestData.getSongListTestResponse()
//        val albumList = TestData.getAlbumListTestResponse()
//        val artistList = TestData.getArtistListTestResponse()
//
//        activityScenario.moveToState(Lifecycle.State.RESUMED)
//
//        activityScenario.onActivity { activity ->
//
//            activity.setVisible(true)
//
//            val menu = RoboMenu()
//            val menuItem: MenuItem = menu.add(R.id.app_bar_search).setActionView(mockSearchView)
//            val mockSearchView: SearchView = Mockito.mock(SearchView::class.java)
//            menu.add(R.id.app_bar_search).actionView = mockSearchView
//            activity.onCreateOptionsMenu(menu as Menu)
//
//            println("TAG ${activityScenario.state}")
//            println( "test ${shadowOf(activity).optionsMenu.findItem(R.id.app_bar_search)}" )
//
//            val searchView = shadowOf(activity).optionsMenu.findItem((R.id.app_bar_search)).actionView as SearchView
//            searchView.setQuery("name", true)
//
//            songObserverCaptor.value.onChanged(songList)
//            albumObserverCaptor.value.onChanged(albumList)
//            artistObserverCaptor.value.onChanged(artistList)
//
//            assertDisplayOfSearchResults(activity)
//
//        }
//
//
//    }

    @After
    fun tearDown() {
        activityScenario.close()
        stopKoin()
    }



    private fun assertDisplayOfSearchResults(activity: MainActivity){
        Assert.assertEquals(
            "linearLayoutSearchResults $IS_VISIBLE",
            View.VISIBLE,
            activity.linearLayoutSearchResults.visibility
        )
    }
}