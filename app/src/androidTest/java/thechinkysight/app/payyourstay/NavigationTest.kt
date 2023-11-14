package thechinkysight.app.payyourstay

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun bottomNavigationTests() {

        val testNavController =
            TestNavHostController(ApplicationProvider.getApplicationContext())

        activityScenarioRule.scenario.onActivity {
            testNavController.setGraph(R.navigation.nav_graph)
        }

        isBottomNavigationVisible()

        isCalculatorFragmentIsStartDestination()

        doesCalculatorMenuNavigatesToCalculatorFragment()

        doesAnalysisMenuNavigatesToAnalysisFragment()

        doesHistoryMenuNavigatesToHistoryFragment()

        doesSettingsMenuNavigatesToSettingsFragment()

    }


    private fun isBottomNavigationVisible() =
        onView(withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()))

    private fun isCalculatorFragmentIsStartDestination() =
        onView(withId(R.id.calculator_fragment)).check(matches(isDisplayed()))

    private fun doesCalculatorMenuNavigatesToCalculatorFragment() {

        onView(
            withId(R.id.calculator_fragment)
        ).perform(click())

        onView(withId(R.id.calculator_fragment)).check(matches(isDisplayed()))
    }

    private fun doesAnalysisMenuNavigatesToAnalysisFragment() {

        onView(
            withId(R.id.analysis_fragment)
        ).perform(click())

        onView(withId(R.id.analysis_fragment)).check(matches(isDisplayed()))

    }

    private fun doesHistoryMenuNavigatesToHistoryFragment() {

        onView(
            withId(R.id.history_fragment)
        ).perform(click())

        onView(withId(R.id.history_fragment)).check(matches(isDisplayed()))

    }

    private fun doesSettingsMenuNavigatesToSettingsFragment() {

        onView(
            withId(R.id.settings_fragment)
        ).perform(click())

        onView(withId(R.id.settings_fragment)).check(matches(isDisplayed()))

    }
}