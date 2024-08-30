package thechinkysight.app.payyourstay.calculatorpage

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.PayYourStayApp
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.enums.Screen
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@RunWith(AndroidJUnit4::class)
class CalculateButton {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()
    private lateinit var testNavController: TestNavHostController


    @Before
    fun setUp() {
        composeTestRule.setContent {

            testNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            PayYourStayTheme {
                PayYourStayApp(
                    calculatorViewModel = calculatorViewModel, navController = testNavController
                )
            }
        }
    }


    private fun searchTextField(@StringRes textField: Int): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    textField
                )
            )
        )
    }

    private fun calculateButtonSemanticsNodeInteraction(): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.calculate).uppercase()
            )
        )
    }

    private fun assertCalculateButtonIsDisabled() {
        calculateButtonSemanticsNodeInteraction().assertIsNotEnabled()
    }


    // Success path

    @Test
    fun calculateButton_Exist() {
        calculateButtonSemanticsNodeInteraction().assertExists()
    }

    @Test
    fun calculateButton_Initialization_IsDisabled() {
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_CalculatesResultProperly() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performTextInput("15000")

        calculateButtonSemanticsNodeInteraction().assertIsEnabled()

        calculateButtonSemanticsNodeInteraction().performClick()

        assertEquals(1050, calculatorViewModel.previousElecMeterReading.value)

        assertEquals(1100, calculatorViewModel.currentElecMeterReading.value)

        assertEquals(15, calculatorViewModel.electricityRatePerUnit.value)

        assertEquals(300, calculatorViewModel.waterFee.value)

        assertEquals(200, calculatorViewModel.garbageFee.value)

        assertEquals(15000, calculatorViewModel.rent.value)

        assertEquals(750, calculatorViewModel.electricityExpense.value)

        assertEquals(16250, calculatorViewModel.total.value)

        assertEquals(
            Screen.InvoicePage.name, testNavController.currentBackStackEntry?.destination?.route
        )
    }


    // Error path

    // The following tests assert that the `Calculate` button remains disabled
    // when at least one text field contains an error.
    @Test
    fun calculateButton_PreviousElecMeterReadingTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performClick()
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performTextInput("15000")

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_CurrentElecMeterReadingTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performClick()
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performTextInput("15000")

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_ElectricityRatePerUnitTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performClick()
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performTextInput("15000")

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_WaterFeeTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performClick()
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performTextInput("15000")

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_GarbageFeeTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performClick()
        searchTextField(R.string.rent).performTextInput("15000")

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_RentTextFieldHasError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        searchTextField(R.string.water_fee).performTextInput("300")
        searchTextField(R.string.garbage_fee).performTextInput("200")
        searchTextField(R.string.rent).performClick()

        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_AllTextFieldsHaveError_IsDisabled() {

        searchTextField(R.string.previous_elec_meter_reading).performClick()
        searchTextField(R.string.current_elec_meter_reading).performClick()
        searchTextField(R.string.electricity_rate_per_unit).performClick()
        searchTextField(R.string.water_fee).performClick()
        searchTextField(R.string.garbage_fee).performClick()
        searchTextField(R.string.rent).performClick()

        assertCalculateButtonIsDisabled()
    }


    // Upon initialization of the `CalculatorPage`, all text fields are empty,
    // and the `Calculate` button is disabled. Even after entering a value into
    // a text field, the `Calculate` button will remain disabled until all text
    // fields are filled with valid, error-free input.
    // The following tests validate this behavior.
    @Test
    fun calculateButton_OnlyPreviousElecMeterReadingTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.previous_elec_meter_reading).performTextInput("1050")
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_OnlyCurrentElecMeterReadingTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.current_elec_meter_reading).performTextInput("1100")
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_OnlyElectricityRatePerUnitTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.electricity_rate_per_unit).performTextInput("15")
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_OnlyWaterFeeTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.water_fee).performTextInput("300")
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_OnlyGarbageFeeTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.garbage_fee).performTextInput("200")
        assertCalculateButtonIsDisabled()
    }

    @Test
    fun calculateButton_OnlyRentTextFieldHasValueAfterInitialisation_IsDisabled() {
        searchTextField(R.string.rent).performTextInput("15000")
        assertCalculateButtonIsDisabled()
    }

}