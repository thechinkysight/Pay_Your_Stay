package thechinkysight.app.payyourstay

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.screens.Settings

@RunWith(AndroidJUnit4::class)
class SettingsTest {

    @Test
    fun isSettingsTextVisible() {

        launchFragmentInContainer<Settings>()

        onView(withText(R.string.settings))
            .check(matches(isDisplayed()))

    }

}