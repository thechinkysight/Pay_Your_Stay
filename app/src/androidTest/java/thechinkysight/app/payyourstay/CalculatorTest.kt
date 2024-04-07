package thechinkysight.app.payyourstay

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme

class CalculatorTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun areAllComponentsPresent() {

        composeTestRule.setContent {
            PayYourStayTheme {
                PayYourStayApp(modifier = Modifier.verticalScroll(rememberScrollState()))
            }
        }

        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.previous_elec_meter_reading)))
            .assertExists()
        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.current_elec_meter_reading)))
            .assertExists()
        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.electricity_rate_per_unit)))
            .assertExists()

        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.water_fee)))
            .assertExists()
        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.garbage_fee)))
            .assertExists()

        composeTestRule.onNode(hasSetTextAction() and hasText(composeTestRule.activity.getString(R.string.rent)))
            .assertExists()

        composeTestRule.onNode(
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.calculate).uppercase()
            )
        ).assertExists()
    }
}



