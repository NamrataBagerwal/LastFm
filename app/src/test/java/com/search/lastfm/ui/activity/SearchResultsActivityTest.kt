package com.search.lastfm.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.adapter.SongAdapter
import com.search.lastfm.ui.util.TestData
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowIntent
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SearchResultsActivityTest : KoinTest {

    companion object {
        private const val IS_NOT_VISIBLE = "is not visible"
        private const val IS_VISIBLE = "is visible"
        private const val TYPE_OF_ITEM = "TYPE_OF_ITEM"
        private const val SEE_ALL_SONGS = "SEE ALL SONGS"
        private const val INDEX_ZERO = 0
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var activityScenario: ActivityScenario<SearchResultsActivity>
    private val songList: List<SongDto> = TestData.getSongListTestResponse()

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

        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            SearchResultsActivity::class.java
        )

        intent.putExtra(
            TYPE_OF_ITEM,
            SEE_ALL_SONGS
        )

        intent.putParcelableArrayListExtra(SEE_ALL_SONGS, songList as ArrayList<out Parcelable>)

        activityScenario = launchActivity(intent)

    }

    @Test
    fun `Verify SearchResultsActivity has home page music cover hidden during on create`() {

        activityScenario.onActivity { activity ->
            Assert.assertEquals(
                "SearchResultsActivity Home Page Music Cover $IS_NOT_VISIBLE",
                View.GONE,
                activity.imageViewHomePage.visibility
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity has recycler view and their respective title's parent layout visible during on create`() {
        activityScenario.onActivity { activity ->
            Assert.assertEquals(
                "SearchResultsActivity linearLayoutSearchResults $IS_VISIBLE",
                View.VISIBLE,
                activity.linearLayoutSearchResults.visibility
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity has non null intent during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertNotNull(
                "Verify SearchResultsActivity has non null intent during onCreate",
                activity.intent
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity has SEE_ALL_SONGS string for key TYPE_OF_ITEM in intent during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertEquals(
                "Verify SearchResultsActivity has SEE_ALL_SONGS string for key TYPE_OF_ITEM in intent during onCreate",
                SEE_ALL_SONGS,
                activity.intent.getStringExtra(TYPE_OF_ITEM)
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity recycler view's height is changed to wrap content during on create`() {
        activityScenario.onActivity { activity ->
            Assert.assertEquals(
                "Verify SearchResultsActivity recycler view's height is changed to wrap content during on createE",
                ViewGroup.LayoutParams.WRAP_CONTENT,
                activity.trendingSongsRecyclerView.layoutParams.height
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity intent's SELL ALL SONGS key's value is painted on UI during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertNotNull(
                "Verify SearchResultsActivity intent's SELL ALL SONGS key's value is painted on UI during onCreate",
                activity.trendingSongsRecyclerView.adapter as SongAdapter
            )

            val songAdapter: SongAdapter = activity.trendingSongsRecyclerView.adapter as SongAdapter
            Assert.assertEquals(
                "Verify SearchResultsActivity intent's SELL ALL SONGS key's value is painted on UI during onCreate",
                songList.size,
                songAdapter.itemCount
            )
            Assert.assertEquals(
                "Verify SearchResultsActivity intent's SELL ALL SONGS key's value is painted on UI during onCreate",
                songList,
                songAdapter.songList
            )
        }
    }

    @Test
    fun `Verify SearchResultsActivity invokes SearchResultDetailActivity on Recycler View Item Click`(){
        activityScenario.onActivity { activity ->

            activity.trendingSongsRecyclerView.layoutManager?.findViewByPosition(INDEX_ZERO)
                ?.performClick()

            val startedIntent: Intent = shadowOf(activity).nextStartedActivity
            val shadowIntent: ShadowIntent = shadowOf(startedIntent)

            Assert.assertEquals(
                "Verify SearchResultsActivity invokes SearchResultDetailActivity on Recycler View Item Click",
                SearchResultDetailActivity::class.java,
                shadowIntent.intentClass
            )
        }
    }

    @After
    fun tearDown() {
        activityScenario.close()
        stopKoin()
    }
}