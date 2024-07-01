package thechinkysight.app.payyourstay.calculatorpage

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
class ElectricityRatePerUnitTextField {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    private lateinit var electricityRatePerUnitTextField: SemanticsNodeInteraction
    private lateinit var electricityRatePerUnitTextFieldIsEmptyErrorMessage: SemanticsNodeInteraction

    private lateinit var resetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var errorIconSemanticsMatcher: SemanticsMatcher


    @OptIn(ExperimentalTestApi::class)
    private fun assertTheExistenceOfResetTextFieldIconButton() {
        composeTestRule.waitUntilNodeCount(
            resetTextFieldIconButtonSemanticsMatcher, 1
        )
    }

    private fun assertThatResetTextFieldIconButtonIsDisabled() {
        assertTheExistenceOfResetTextFieldIconButton()
        composeTestRule.onNode(
            resetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
    }

    private fun assertThatResetTextFieldIconButtonIsEnabled(): SemanticsNodeInteraction {
        assertTheExistenceOfResetTextFieldIconButton()
        return composeTestRule.onNode(
            resetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isEnabled()
        )
    }

    @OptIn(ExperimentalTestApi::class)
    private fun assertTheExistenceOfNumberOfErrorIcons(count: Int = 1) {
        composeTestRule.waitUntilNodeCount(
            errorIconSemanticsMatcher, count
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

        electricityRatePerUnitTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.electricity_rate_per_unit
                )
            )
        )

        electricityRatePerUnitTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Electricity rate"
                )
            )
        )

        resetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.electricity_rate_per_unit
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
    fun electricityRatePerUnitTextField_Initialization_ExistWithDisabledResetTextFieldIconButton() {

        electricityRatePerUnitTextField.assertExists()

        assertThatResetTextFieldIconButtonIsDisabled()

    }


    @Test
    fun electricityRatePerUnitTextField_WithValidInput_ResetTextFieldIconButtonEnabled() {

        electricityRatePerUnitTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()

    }


    @Test
    fun electricityRatePerUnitTextField_WithValidInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModel() {

        electricityRatePerUnitTextField.performTextInput("13")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(13, calculatorViewModel.electricityRatePerUnit.value)
    }


    @Test
    fun electricityRatePerUnitTextField_WithValidInput_ResetTextFieldIconButtonClicked_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithNull() {

        electricityRatePerUnitTextField.performTextInput("13")

        assertThatResetTextFieldIconButtonIsEnabled().performClick()

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

    }

    // Error Path

    // Test to test the behaviour of the text field when it's focused for the first time, then unfocused, and then refocus.
    @Test
    fun electricityRatePerUnitTextField_WithNoValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val currentElecMeterReadingTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        electricityRatePerUnitTextField.performClick()

        electricityRatePerUnitTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()


        // Changing the focus to "Current Elec Meter Reading" text field via taking the focus away from "Electricity Rate Per Unit" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        electricityRatePerUnitTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)


        // Refocusing to "Electricity Rate Per Unit" text field.
        electricityRatePerUnitTextField.performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        electricityRatePerUnitTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)
    }


    // Test to test the behaviour when the input to the text field is greater than `Int.MAX_VALUE`.
    @Test
    fun electricityRatePerUnitTextField_WithValueGreaterThanINTMAXVALUE_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {

        electricityRatePerUnitTextField.performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

    }


    // Test to test the behaviour when the input to the text field contains non-digit elements.
    @Test
    fun electricityRatePerUnitTextField_WithNonDigitInput_UpdatesTheElectricityRatePerUnitVariableInTheCalculatorViewModelWithOldValue() {

        electricityRatePerUnitTextField.performTextInput("13,")

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)
    }


    // Boundary case
    @Test
    fun electricityRatePerUnitTextField_WithMinimumValidInput_UpdatesTheElectricityRatePerUnitTextFieldVariableInTheCalculatorViewModel() {

        electricityRatePerUnitTextField.performTextInput("0")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(0, calculatorViewModel.electricityRatePerUnit.value)

    }

    @Test
    fun electricityRatePerUnitTextField_WithMaximumValidInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {

        electricityRatePerUnitTextField.performTextInput(Int.MAX_VALUE.toString())

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(Int.MAX_VALUE, calculatorViewModel.electricityRatePerUnit.value)

    }

}