package thechinkysight.app.payyourstay

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.screens.Calculator

@RunWith(AndroidJUnit4::class)
class CalculatorTest {
    @Test
    fun isCalculatorTextVisible() {
        launchFragmentInContainer<Calculator>()
        onView(ViewMatchers.withText(R.string.calculator))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}