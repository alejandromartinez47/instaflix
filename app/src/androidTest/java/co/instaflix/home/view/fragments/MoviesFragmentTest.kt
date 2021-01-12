package co.instaflix.home.view.fragments

import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.instaflix.navigation.view.NavigationActivity
import org.junit.Rule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import co.instaflix.R
import co.instaflix.home.adapter.ContentMoviesAdapter
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MoviesFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)
    private val listItemInTest = 4

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.rvMoviesTop)).check(matches(isDisplayed()))
    }

    @Test
    fun test_title_home_isTitlesFragmentVisible() {
        // Validamos que el titulo de la lista en el home es visible
        onView(withId(R.id.tvTitleTop)).check(matches(withText(R.string.top)))
    }

    //Deshabilitar animaciones en el dispositivo que se va a probar o en el archivo extensions documentar la linea 32
    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.rvMoviesTop))
            .perform(
                actionOnItemAtPosition<ContentMoviesAdapter.ViewHolder>(
                    listItemInTest,
                    click()
                )
            )
        // Validamos que el fragment de detalle se muestra
        onView(withId(R.id.tvTitleMovieDetail)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isGoBack() {
        onView(withId(R.id.rvMoviesTop))
            .perform(
                actionOnItemAtPosition<ContentMoviesAdapter.ViewHolder>(
                    listItemInTest,
                    click()
                )
            )
        onView(withId(R.id.tvTitleMovieDetail)).check(matches(isDisplayed()))

        pressBack()
        // Validamos que navega bien atras desde el detalle
        onView(withId(R.id.clToolbarMovies)).check(matches(isDisplayed()))
    }

}