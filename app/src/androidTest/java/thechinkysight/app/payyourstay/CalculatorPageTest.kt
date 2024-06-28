package thechinkysight.app.payyourstay

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isNotEnabled
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

    private lateinit var electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var rentTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher

    private lateinit var errorIconSemanticsMatcher: SemanticsMatcher

    @OptIn(ExperimentalTestApi::class)
    private fun assertTheExistenceOfAllResetTextFieldIconButtons() {

        composeTestRule.waitUntilNodeCount(
            electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )
        composeTestRule.waitUntilNodeCount(
            waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )
        composeTestRule.waitUntilNodeCount(
            garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )
        composeTestRule.waitUntilNodeCount(
            rentTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )

    }

    private fun assertThatAllResetTextFieldIconButtonsAreDisabled() {
        composeTestRule.onNode(
            electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
        composeTestRule.onNode(
            waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
        composeTestRule.onNode(
            garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
        composeTestRule.onNode(
            rentTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
    }

    private fun assertThatAllResetTextFieldIconButtonsAreEnabled() {

        composeTestRule.onNode(
            electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isEnabled()
        )
        composeTestRule.onNode(
            waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isEnabled()
        )
        composeTestRule.onNode(
            garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isEnabled()
        )
        composeTestRule.onNode(
            rentTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isEnabled()
        )
    }


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

        electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.electricity_rate_per_unit
            )
        )
        waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.water_fee
            )
        )
        garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.garbage_fee
            )
        )
        rentTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.rent
            )
        )

        errorIconSemanticsMatcher = hasContentDescription(
            composeTestRule.activity.getString(
                R.string.text_field_error_icon
            )
        )
    }

    // Success path


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
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithValidInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("14")

        assertEquals(14, calculatorViewModel.electricityRatePerUnit.value)

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
    }

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

        assertTheExistenceOfAllResetTextFieldIconButtons()

        assertThatAllResetTextFieldIconButtonsAreDisabled()
    }

    @Test
    fun calculatorPage_TextFieldsAreNotEmpty_ResetTextFieldIconButtonEnabled() {


        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("13")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("200")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("160")

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000")

        assertTheExistenceOfAllResetTextFieldIconButtons()

        assertThatAllResetTextFieldIconButtonsAreEnabled()
    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_ClickedTheResetTextFieldIconButtonOfElectricityRatePerUnitTextFieldWithValidInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithNull() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput("13")

        // First I am checking whether the node is present or not, and then I am performing the action e.g. click in this case.
        composeTestRule.waitUntilNodeCount(
            electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )

        composeTestRule.onNode(
            electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).performClick()


        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_ClickedTheResetTextFieldIconButtonOfWaterFeeTextFieldWithValidInput_UpdatesTheWaterFeeVariableInTheCalculatorViewModelWithNull() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.water_fee
                )
            )
        ).performTextInput("200")

        // First I am checking whether the node is present or not, and then I am performing the action e.g. click in this case.
        composeTestRule.waitUntilNodeCount(
            waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )

        composeTestRule.onNode(
            waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).performClick()


        assertEquals(null, calculatorViewModel.waterFee.value)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_ClickedTheResetTextFieldIconButtonOfGarbageFeeTextFieldWithValidInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithNull() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        ).performTextInput("160")

        // First I am checking whether the node is present or not, and then I am performing the action e.g. click in this case.
        composeTestRule.waitUntilNodeCount(
            garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )

        composeTestRule.onNode(
            garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).performClick()


        assertEquals(null, calculatorViewModel.garbageFee.value)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun calculatorPage_ClickedTheResetTextFieldIconButtonOfRentTextFieldWithValidInput_UpdatesTheRentVariableInTheCalculatorViewModelWithNull() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.rent
                )
            )
        ).performTextInput("15000")

        // First I am checking whether the node is present or not, and then I am performing the action e.g. click in this case.
        composeTestRule.waitUntilNodeCount(
            rentTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )

        composeTestRule.onNode(
            rentTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).performClick()


        assertEquals(null, calculatorViewModel.rent.value)

    }

    @Test
    fun calculatorPage_ClickedTheCalculateButton_CalculatesResultProperly() {

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

// Error Path


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
    fun calculatorPage_ElectricityRatePerUnitTextFieldWithValueGreaterThanINTMAXVALUE_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {

        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        ).performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

        assertTheExistenceOfAllResetTextFieldIconButtons()

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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()

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

        assertTheExistenceOfAllResetTextFieldIconButtons()

    }

    // Tests to test the behaviour when the input to the text fields contains non-digit elements
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
    }

    // Tests for testing the behaviours when the value of "Current Elec. Meter Reading" text field is smaller than the value of "Previous Elec. Meter Reading" text field.




// Boundary case

    // With minimum case

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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
    }


    // With maximum case

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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
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

        assertTheExistenceOfAllResetTextFieldIconButtons()
    }
}