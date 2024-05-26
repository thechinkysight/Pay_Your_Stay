package thechinkysight.app.payyourstay

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@RunWith(AndroidJUnit4::class)
class CalculatorPageTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PayYourStayTheme {
                CalculatorPage(
                    modifier = Modifier.verticalScroll(
                        rememberScrollState()
                    ), calculatorViewModel = calculatorViewModel
                )
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


    // Success path

    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithValidInput_updatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("1909")

        assertEquals(1909, calculatorViewModel.previousElecMeterReading.value)
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithValidInput_updatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("2091")

        assertEquals(2091, calculatorViewModel.currentElecMeterReading.value)
    }


    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithValidInput_updatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("14")

        assertEquals(14, calculatorViewModel.electricityRatePerUnit.value)
    }


    @Test
    fun calculatorPage_waterFeeTextFieldWithValidInput_updatesTheWaterFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("200")

        assertEquals(200, calculatorViewModel.waterFee.value)
    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithValidInput_updatesTheGarbageFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("125")

        assertEquals(125, calculatorViewModel.garbageFee.value)
    }

    @Test
    fun calculatorPage_rentTextFieldWithValidInput_updatesTheRentVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000")

        assertEquals(15000, calculatorViewModel.rent.value)
    }


    // Error Path

    // Tests to test the behaviour when the input to the text fields is greater than `Int.MAX_VALUE`
    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithValueGreaterThanINTMAXVALUE_updatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.previousElecMeterReading.value)
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithValueGreaterThanINTMAXVALUE_updatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.currentElecMeterReading.value)

    }

    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithValueGreaterThanINTMAXVALUE_updatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

    }

    @Test
    fun calculatorPage_waterFeeTextFieldWithValueGreaterThanINTMAXVALUE_updatesTheWaterFeeVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.waterFee.value)

    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithValueGreaterThanINTMAXVALUE_updatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.garbageFee.value)

    }

    @Test
    fun calculatorPage_rentTextFieldWithValueGreaterThanINTMAXVALUE_updatesTheRentVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.rent.value)

    }


    // Tests to test the behaviour when the input to the text fields contains non-digit elements
    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithNonDigitInput_updatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("1909,")

        assertEquals(null, calculatorViewModel.previousElecMeterReading.value)
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithNonDigitInput_updatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("2091-")

        assertEquals(null, calculatorViewModel.currentElecMeterReading.value)
    }

    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithNonDigitInput_updatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("14.")

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)
    }

    @Test
    fun calculatorPage_waterFeeTextFieldWithNonDigitInput_updatesTheWaterFeeVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("200,")

        assertEquals(null, calculatorViewModel.waterFee.value)
    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithNonDigitInput_updatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("125-")

        assertEquals(null, calculatorViewModel.garbageFee.value)
    }

    @Test
    fun calculatorPage_rentTextFieldWithNonDigitInput_updatesTheRentVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000.")

        assertEquals(null, calculatorViewModel.rent.value)
    }

    @Test
    fun calculatorPage_initialization_noErrorsInTextFields() {
        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Previous reading"
                )
            )
        ).assertDoesNotExist()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        ).assertDoesNotExist()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        ).assertDoesNotExist()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Water fee"
                )
            )
        ).assertDoesNotExist()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        ).assertDoesNotExist()


        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Rent"
                )
            )
        ).assertDoesNotExist()

        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        ).assertDoesNotExist()
    }

    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val previousElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Previous reading"
                )
            )
        )

        previousElecMeterReadingTextField.performClick()

        errorText.assertExists()


        // Changing the focus to "Current Elec Meter Reading" text field via taking the focus away from "Previous Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performClick()


        errorText.assertExists()

        previousElecMeterReadingTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        currentElecMeterReadingTextField.performClick()

        errorText.assertExists()

        // Changing the focus to "Electricity Rate Per Unit" text field via taking the focus away from "Current Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performClick()

        errorText.assertExists()

        currentElecMeterReadingTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val electricityRatePerUnitTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        )

        electricityRatePerUnitTextField.performClick()

        errorText.assertExists()

        // Changing the focus to "Water Fee" text field via taking the focus away from "Electricity Rate Per Unit" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performClick()

        errorText.assertExists()

        electricityRatePerUnitTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_waterFeeTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val waterFeeTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Water fee"
                )
            )
        )

        waterFeeTextField.performClick()

        errorText.assertExists()

        // Changing the focus to "Garbage Fee" text field via taking the focus away from "Water Fee" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performClick()

        errorText.assertExists()

        waterFeeTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val garbageFeeTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        )

        garbageFeeTextField.performClick()

        errorText.assertExists()

        // Changing the focus to "Rent" text field via taking the focus away from "Garbage Fee" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performClick()

        errorText.assertExists()

        garbageFeeTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_rentTextFieldWithNullAsValue_showsErrorOnTheTextFieldOnInitialFocusAndUnfocusedAfterInitialFocusAndRefocus() {

        val rentTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Rent"
                )
            )
        )

        rentTextField.performClick()

        errorText.assertExists()

        // Changing the focus to "Garbage Fee" text field via taking the focus away from "Rent" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performClick()

        errorText.assertExists()

        rentTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_theValueOfCurrentElecMeterReadingTextFieldIsSmallerThanTheValueOfPreviousElecMeterReadingTextField_showsErrorOnCurrentElecMeterReadingTextFieldWhenFocusedAndUnfocusedAndRefocused() {


        val currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        )

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("500")

        currentElecMeterReadingTextField.performTextInput("400")

        errorText.assertExists()


        // Changing the focus to "Electricity Rate Per Unit" text field via taking the focus away from "Current Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performClick()

        errorText.assertExists()

        currentElecMeterReadingTextField.performClick()

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_theValueOfCurrentElecMeterReadingTextFieldIsSmallerThanTheValueOfPreviousElecMeterReadingTextFieldAfterUpdatingPreviousElecMeterReadingTextFieldValue_showsErrorOnCurrentElecMeterReadingTextField() {

        val previousElecMeterReading = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        )

        previousElecMeterReading.performTextInput("500")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("600")

        errorText.assertDoesNotExist()

        previousElecMeterReading.performTextClearance()

        previousElecMeterReading.performTextInput("700")

        errorText.assertExists()
    }

    @Test
    fun calculatorPage_theValueOfCurrentElecMeterReadingTextFieldIsSmallerThanTheValueOfPreviousElecMeterReadingTextFieldAfterUpdatingCurrentElecMeterReadingTextFieldValue_showsErrorOnCurrentElecMeterReadingTextField() {

        val currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        val errorText = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        )

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("500")

        currentElecMeterReadingTextField.performTextInput("600")

        errorText.assertDoesNotExist()

        currentElecMeterReadingTextField.performTextClearance()

        currentElecMeterReadingTextField.performTextInput("400")

        errorText.assertExists()
    }


    // Boundary case

    // With minimum case
    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithMinimumValidInput_updatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.previousElecMeterReading.value)
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithMinimumValidInput_updatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.currentElecMeterReading.value)
    }


    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithMinimumValidInput_updatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.electricityRatePerUnit.value)
    }


    @Test
    fun calculatorPage_waterFeeTextFieldWithMinimumValidInput_updatesTheWaterFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.waterFee.value)
    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithMinimumValidInput_updatesTheGarbageFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.garbageFee.value)
    }

    @Test
    fun calculatorPage_rentTextFieldWithMinimumValidInput_updatesTheRentVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("0")

        assertEquals(0, calculatorViewModel.rent.value)
    }


    // With maximum case
    @Test
    fun calculatorPage_previousElecMeterReadingTextFieldWithMaximumValidInput_updatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.previousElecMeterReading.value)
    }

    @Test
    fun calculatorPage_currentElecMeterReadingTextFieldWithMaximumValidInput_updatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.currentElecMeterReading.value)
    }


    @Test
    fun calculatorPage_electricityRatePerUnitTextFieldWithMaximumValidInput_updatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.electricityRatePerUnit.value)
    }


    @Test
    fun calculatorPage_waterFeeTextFieldWithMaximumValidInput_updatesTheWaterFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.waterFee.value)
    }

    @Test
    fun calculatorPage_garbageFeeTextFieldWithMaximumValidInput_updatesTheGarbageFeeVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.garbageFee.value)
    }

    @Test
    fun calculatorPage_rentTextFieldWithMaximumValidInput_updatesTheRentVariableInTheCalculatorViewModel() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput(Int.MAX_VALUE.toString())

        assertEquals(Int.MAX_VALUE, calculatorViewModel.rent.value)
    }
}