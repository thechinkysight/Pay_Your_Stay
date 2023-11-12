package thechinkysight.app.payyourstay

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.screens.Settings

@RunWith(AndroidJUnit4::class)
class SettingsTest {

    @Test
    fun isSettingsTextVisible() {

        launchFragmentInContainer<Settings>()

    onView(ViewMatchers.withText(R.string.settings))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}