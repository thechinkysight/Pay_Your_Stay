package thechinkysight.app.payyourstay.calculatorpage

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@RunWith(AndroidJUnit4::class)
class CalculateButton {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()


    @Before
    fun setUp() {
        composeTestRule.setContent {
            PayYourStayTheme {
                CalculatorPage(
                    calculatorViewModel = calculatorViewModel,
                    navController = rememberNavController()
                )
            }
        }
    }


    // Success path

    @Test
    fun calculateButton_Exist() {
        composeTestRule.onNode(
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.calculate).uppercase()
            )
        ).assertExists()
    }

    //  TODO: Update the following test to incorporate navigation test.
    @Test
    fun calculateButton_CalculatesResultProperly() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("1050")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("1100")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("15")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("300")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("200")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000")


        composeTestRule.onNode(
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.calculate).uppercase()
            )
        ).performClick()

        assertEquals(1050, calculatorViewModel.previousElecMeterReading.value)

        assertEquals(1100, calculatorViewModel.currentElecMeterReading.value)

        assertEquals(15, calculatorViewModel.electricityRatePerUnit.value)

        assertEquals(300, calculatorViewModel.waterFee.value)

        assertEquals(200, calculatorViewModel.garbageFee.value)

        assertEquals(15000, calculatorViewModel.rent.value)

        assertEquals(750, calculatorViewModel.electricityExpense.value)

        assertEquals(16250, calculatorViewModel.total.value)
    }

    /**
     * TODO: Update or write test to test whether the calculate button is disable on start.
     * TODO: Write tests to test whether exception is thrown or not in the unit test and tests to test the handling of the exception in the instrument test.
     * TODO: Write tests to test whether calculate button is enabled or not based on the text fields error status.
     */

}