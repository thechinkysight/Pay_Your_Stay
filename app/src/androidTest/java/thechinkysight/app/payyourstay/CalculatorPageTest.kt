package thechinkysight.app.payyourstay

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
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

    // Success path

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

    @Test
    fun calculatorPage_PreviousElecMeterReadingTextFieldWithValidInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {
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
    fun calculatorPage_CurrentElecMeterReadingTextFieldWithValidInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {
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
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithValidInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {
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
    fun calculatorPage_WaterFeeTextFieldWithValidInput_UpdatesTheWaterFeeVariableInTheCalculatorViewModel() {
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
    fun calculatorPage_GarbageFeeTextFieldWithValidInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModel() {
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
    fun calculatorPage_RentTextFieldWithValidInput_UpdatesTheRentVariableInTheCalculatorViewModel() {
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

    @Test
    fun calculatorPage_Initialization_NoErrorsInTextFields() {
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

    // Tests to test the behaviours of the text fields when they're focused for the first time, then unfocused, and then refocus.
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_PreviousElecMeterReadingTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val previousElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        )

        val previousElecMeterReadingTextFiledErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Previous reading"
                )
            )
        )

        val currentElecMeterReadingTextFiledErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )

        previousElecMeterReadingTextField.performClick()


        previousElecMeterReadingTextFiledErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )


        // Changing the focus to "Current Elec Meter Reading" text field via taking the focus away from "Previous Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performClick()


        previousElecMeterReadingTextFiledErrorMessage.assertExists()

        currentElecMeterReadingTextFiledErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        previousElecMeterReadingTextField.performClick()


        previousElecMeterReadingTextFiledErrorMessage.assertExists()

        currentElecMeterReadingTextFiledErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_CurrentElecMeterReadingTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        val currentElecMeterReadingTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        val electricityRatePerUnitTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )

        currentElecMeterReadingTextField.performClick()


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Electricity Rate Per Unit" text field via taking the focus away from "Current Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performClick()


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        electricityRatePerUnitTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        currentElecMeterReadingTextField.performClick()


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        electricityRatePerUnitTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val electricityRatePerUnitTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        )

        val electricityRatePerUnitTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        )

        val waterFeeTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Water fee"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )


        electricityRatePerUnitTextField.performClick()


        electricityRatePerUnitTextFieldErrorMessage.assertExists()
        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Water Fee" text field via taking the focus away from "Electricity Rate Per Unit" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performClick()


        electricityRatePerUnitTextFieldErrorMessage.assertExists()

        waterFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        electricityRatePerUnitTextField.performClick()


        electricityRatePerUnitTextFieldErrorMessage.assertExists()

        waterFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_WaterFeeTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val waterFeeTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        )

        val waterFeeTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Water fee"
                )
            )
        )

        val garbageFeeTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )


        waterFeeTextField.performClick()


        waterFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Garbage Fee" text field via taking the focus away from "Water Fee" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performClick()


        waterFeeTextFieldErrorMessage.assertExists()

        garbageFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        waterFeeTextField.performClick()


        waterFeeTextFieldErrorMessage.assertExists()

        garbageFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_GarbageFeeTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val garbageFeeTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        )

        val garbageFeeTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        )

        val rentTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Rent"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )


        garbageFeeTextField.performClick()


        garbageFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Rent" text field via taking the focus away from "Garbage Fee" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performClick()


        garbageFeeTextFieldErrorMessage.assertExists()

        rentTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        garbageFeeTextField.performClick()


        garbageFeeTextFieldErrorMessage.assertExists()

        rentTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_RentTextFieldWithNullAsValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val rentTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        )

        val rentTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Rent"
                )
            )
        )

        val garbageFeeTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )


        rentTextField.performClick()


        rentTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Garbage Fee" text field via taking the focus away from "Rent" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performClick()


        rentTextFieldErrorMessage.assertExists()

        garbageFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        rentTextField.performClick()


        rentTextFieldErrorMessage.assertExists()

        garbageFeeTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    // Tests to test the behaviour when the input to the text fields is greater than `Int.MAX_VALUE`
    @Test
    fun calculatorPage_PreviousElecMeterReadingTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_CurrentElecMeterReadingTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_WaterFeeTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheWaterFeeVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_GarbageFeeTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_RentTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheRentVariableInTheCalculatorViewModelWithOldValue() {

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
    fun calculatorPage_PreviousElecMeterReadingTextFieldWithNonDigitInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {
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
    fun calculatorPage_CurrentElecMeterReadingTextFieldWithNonDigitInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {
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
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithNonDigitInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {
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
    fun calculatorPage_WaterFeeTextFieldWithNonDigitInput_UpdatesTheWaterFeeVariableInTheCalculatorViewModelWithOldValue() {
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
    fun calculatorPage_GarbageFeeTextFieldWithNonDigitInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {
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
    fun calculatorPage_RentTextFieldWithNonDigitInput_UpdatesTheRentVariableInTheCalculatorViewModelWithOldValue() {
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000.")

        assertEquals(null, calculatorViewModel.rent.value)
    }

    // Tests for testing the behaviours when the value of "Current Elec. Meter Reading" text field is smaller than the value of "Previous Elec. Meter Reading" text field.
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_CurrentElecMeterReadingIsSmallerThanPreviousElecMeterReading_ShowsErrorOnCurrentElecMeterReadingTextFieldWhenFocusedAndUnfocusedAndRefocused() {

        val currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        val currentElecMeterReadingTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        )

        val electricityRateUnitTextFieldErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
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


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )

        // Changing the focus to "Electricity Rate Per Unit" text field via taking the focus away from "Current Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performClick()


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        electricityRateUnitTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )


        currentElecMeterReadingTextField.performClick()


        currentElecMeterReadingTextFieldErrorMessage.assertExists()

        electricityRateUnitTextFieldErrorMessage.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 2
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_currentElecMeterReadingIsSmallerThanPreviousElecMeterReadingAfterUpdatingPreviousElecMeterReading_showsErrorOnCurrentElecMeterReadingTextField() {

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
                    R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                )
            )
        )

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
            )
        )

        previousElecMeterReadingTextField.performTextInput("500")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performTextInput("600")


        errorText.assertDoesNotExist()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 0
        )

        previousElecMeterReadingTextField.performTextClearance()

        previousElecMeterReadingTextField.performTextInput("700")

        errorText.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_CurrentElecMeterReadingIsSmallerThanPreviousElecMeterReadingAfterUpdatingCurrentElecMeterReading_ShowsErrorOnCurrentElecMeterReadingTextField() {

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

        val errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.error_icon
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

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 0
        )

        currentElecMeterReadingTextField.performTextClearance()

        currentElecMeterReadingTextField.performTextInput("400")


        errorText.assertExists()

        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, 1
        )
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