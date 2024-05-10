package thechinkysight.app.payyourstay

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme

@RunWith(AndroidJUnit4::class)
class CalculatorPageTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PayYourStayTheme {
                CalculatorPage(modifier = Modifier.verticalScroll(rememberScrollState()))
            }
        }
    }


    @Test
    fun calculatorPage_PreviousElecMeterReadingTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).assertExists()    
    }

    @Test
    fun calculatorPage_CurrentElecMeterReadingTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).assertExists()
    }

    @Test
    fun calculatorPage_ElectricityRatePerUnitTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).assertExists()
    }

    @Test
    fun calculatorPage_WaterFeeTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).assertExists()
    }

    @Test
    fun calculatorPage_GarbageFeeTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).assertExists()
    }

    @Test
    fun calculatorPage_RentTextField_Exist() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).assertExists()
    }

    @Test
    fun calculatorPage_CalculateButton_Exist() {
        composeTestRule.onNode(
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.calculate).uppercase()
            )
        ).assertExists()
    }
}


