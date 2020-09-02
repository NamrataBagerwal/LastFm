package com.search.lastfm.ui.activity

import android.content.Intent
import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.search.lastfm.R
import com.search.lastfm.di.DependencyInjectionModule
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.util.TestData
import kotlinx.android.synthetic.main.activity_search_result_detail.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SearchResultDetailActivityTest : KoinTest {

    companion object {
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

    private lateinit var activityScenario: ActivityScenario<SearchResultDetailActivity>
    private val songDto: SongDto = TestData.getSongListTestResponse()[INDEX_ZERO]

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
            SearchResultDetailActivity::class.java
        )
        intent.putExtra(TYPE_OF_ITEM, SEE_ALL_SONGS)
        intent.putExtra(SEE_ALL_SONGS, songDto)

        activityScenario = launchActivity(intent)

    }

    @Test
    fun `Verify SearchResultDetailActivity has non null intent during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertNotNull(
                "Verify SearchResultDetailActivity has non null intent during onCreate",
                activity.intent
            )
        }
    }

    @Test
    fun `Verify SearchResultDetailActivity has SEE_ALL_SONGS string for key TYPE_OF_ITEM in intent during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertEquals(
                "Verify SearchResultDetailActivity has TYPE_OF_ITEM string in intent during onCreate",
                SEE_ALL_SONGS,
                activity.intent.getStringExtra(TYPE_OF_ITEM)
            )
        }
    }

    @Test
    fun `Verify SearchResultDetailActivity intent's SELL ALL SONGS key's value is painted on UI during onCreate`() {
        activityScenario.onActivity { activity ->

            Assert.assertNotNull(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.intent.getStringExtra(TYPE_OF_ITEM)
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                SEE_ALL_SONGS,
                activity.intent.getStringExtra(TYPE_OF_ITEM)
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.itemTitleTextView.text,
                songDto.name
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.itemDetailTextView1.text,
                songDto.artist
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.itemDetailTextView2.text.toString(),
                "${activity.getString(R.string.listeners)}${songDto.listeners}"
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.itemDetailTextView3.text.toString(),
                "${activity.getString(R.string.streamable)}${songDto.streamable}"
            )
            Assert.assertEquals(
                "Verify SearchResultDetailActivity intent's extra string SELL ALL SONGS object is painted on UI during onCreate",
                activity.itemDetailTextView4.text.toString(),
                "${activity.getString(R.string.url)}${songDto.url}"
            )
        }
    }


    @After
    fun tearDown() {
        activityScenario.close()
        stopKoin()
    }
}