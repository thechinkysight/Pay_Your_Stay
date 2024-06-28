package thechinkysight.app.payyourstay

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
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@RunWith(AndroidJUnit4::class)
class PreviousElecMeterReadingTextFieldTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    private lateinit var previousElecMeterReadingTextField: SemanticsNodeInteraction
    private lateinit var previousElecMeterReadingTextFieldIsEmptyErrorMessage: SemanticsNodeInteraction

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

        previousElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.previous_elec_meter_reading
                )
            )
        )

        previousElecMeterReadingTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Previous reading"
                )
            )
        )

        resetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.previous_elec_meter_reading
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
    fun previousElecMeterReadingTextField_Initialization_ExistWithDisabledResetTextFieldIconButton() {

        previousElecMeterReadingTextField.assertExists()

        assertThatResetTextFieldIconButtonIsDisabled()

    }


    @Test
    fun previousElecMeterReadingTextField_WithValidInput_ResetTextFieldIconButtonEnabled() {

        previousElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()

    }


    @Test
    fun previousElecMeterReadingTextField_WithValidInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {

        previousElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(1909, calculatorViewModel.previousElecMeterReading.value)
    }


    @Test
    fun previousElecMeterReadingTextField_WithValidInput_ResetTextFieldIconButtonClicked_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithNull() {

        previousElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled().performClick()

        assertEquals(null, calculatorViewModel.previousElecMeterReading.value)

    }

    // Error Path

    // Test to test the behaviour of the text field when it's focused for the first time, then unfocused, and then refocus.
    @Test
    fun previousElecMeterReadingTextField_WithNoValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val currentElecMeterReadingTextFiledErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        previousElecMeterReadingTextField.performClick()

        previousElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()


        // Changing the focus to "Current Elec Meter Reading" text field via taking the focus away from "Previous Elec Meter Reading" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performClick()

        previousElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        currentElecMeterReadingTextFiledErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)


        // Refocusing to "Previous Elec Meter Reading" text field.
        previousElecMeterReadingTextField.performClick()

        previousElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        currentElecMeterReadingTextFiledErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)
    }


    // Test to test the behaviour when the input to the text field is greater than `Int.MAX_VALUE`.
    @Test
    fun previousElecMeterReadingTextField_WithValueGreaterThanINTMAXVALUE_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        previousElecMeterReadingTextField.performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.previousElecMeterReading.value)

    }


    // Test to test the behaviour when the input to the text field contains non-digit elements.
    @Test
    fun previousElecMeterReadingTextField_WithNonDigitInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        previousElecMeterReadingTextField.performTextInput("1909,")

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.previousElecMeterReading.value)
    }


    // Boundary case
    @Test
    fun previousElecMeterReadingTextField_WithMinimumValidInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {

        previousElecMeterReadingTextField.performTextInput("0")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(0, calculatorViewModel.previousElecMeterReading.value)

    }

    @Test
    fun previousElecMeterReadingTextField_WithMaximumValidInput_UpdatesThePreviousElecMeterReadingVariableInTheCalculatorViewModel() {

        previousElecMeterReadingTextField.performTextInput(Int.MAX_VALUE.toString())

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(Int.MAX_VALUE, calculatorViewModel.previousElecMeterReading.value)

    }
}