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
class CurrentElecMeterReadingTextFieldTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    private lateinit var currentElecMeterReadingTextField: SemanticsNodeInteraction
    private lateinit var currentElecMeterReadingTextFieldIsEmptyErrorMessage: SemanticsNodeInteraction
    private lateinit var currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage: SemanticsNodeInteraction
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

        currentElecMeterReadingTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        )

        currentElecMeterReadingTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage =
            composeTestRule.onNode(
                hasText(
                    composeTestRule.activity.getString(
                        R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                    )
                )
            )

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
                R.string.current_elec_meter_reading
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
    fun currentElecMeterReadingTextField_Initialization_ExistWithDisabledResetTextFieldIconButton() {

        currentElecMeterReadingTextField.assertExists()

        assertThatResetTextFieldIconButtonIsDisabled()

    }


    @Test
    fun currentElecMeterReadingTextField_WithValidInput_ResetTextFieldIconButtonEnabled() {

        currentElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()

    }


    @Test
    fun currentElecMeterReadingTextField_WithValidInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {

        currentElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(1909, calculatorViewModel.currentElecMeterReading.value)
    }


    @Test
    fun currentElecMeterReadingTextField_WithValidInput_ResetTextFieldIconButtonClicked_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithNull() {

        currentElecMeterReadingTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled().performClick()

        assertEquals(null, calculatorViewModel.currentElecMeterReading.value)

    }

    // Error Path

    // Test to test the behaviour of the text field when it's focused for the first time, then unfocused, and then refocus.
    @Test
    fun currentElecMeterReadingTextField_WithNoValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        currentElecMeterReadingTextField.performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()


        // Changing the focus to "Previous Elec Meter Reading" text field via taking the focus away from "Current Elec Meter Reading" text field.
        previousElecMeterReadingTextField.performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        previousElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)


        // Refocusing to "Current Elec Meter Reading" text field.
        currentElecMeterReadingTextField.performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        previousElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)
    }


    // Test to test the behaviour when the input to the text field is greater than `Int.MAX_VALUE`.
    @Test
    fun currentElecMeterReadingTextField_WithValueGreaterThanINTMAXVALUE_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        currentElecMeterReadingTextField.performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.currentElecMeterReading.value)

    }


    // Test to test the behaviour when the input to the text field contains non-digit elements.
    @Test
    fun currentElecMeterReadingTextField_WithNonDigitInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModelWithOldValue() {

        currentElecMeterReadingTextField.performTextInput("1909,")

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.currentElecMeterReading.value)
    }


    // The followings are the tests for testing the behaviours when the value of "Current Elec. Meter Reading" text field is smaller than the value
    // of "Previous Elec. Meter Reading" text field.
    @Test
    fun currentElecMeterReadingTextField_WithValueSmallerThanPreviousElecMeterReading_ShowsErrorOnCurrentElecMeterReadingTextFieldWhenFocusedAndUnfocusedAndRefocused() {

        previousElecMeterReadingTextField.performTextInput("500")

        currentElecMeterReadingTextField.performTextInput("400")


        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()

        // Changing the focus to "Previous Elec. Meter Reading" text field via taking the focus away from "Current Elec Meter Reading" text field.
        previousElecMeterReadingTextField.performClick()


        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()


        currentElecMeterReadingTextField.performClick()

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()
    }

    @Test
    fun currentElecMeterReadingTextField_WithValueSmallerThanPreviousElecMeterReading_AfterUpdatingPreviousElecMeterReading_ShowsErrorOnCurrentElecMeterReadingTextField() {

        previousElecMeterReadingTextField.performTextInput("500")

        currentElecMeterReadingTextField.performTextInput("600")

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertDoesNotExist()

        assertThatResetTextFieldIconButtonIsEnabled()


        previousElecMeterReadingTextField.performTextClearance()

        previousElecMeterReadingTextField.performTextInput("700")

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()
    }

    @Test
    fun calculatorPage_CurrentElecMeterReadingIsSmallerThanPreviousElecMeterReadingAfterUpdatingCurrentElecMeterReading_ShowsErrorOnCurrentElecMeterReadingTextField() {

        previousElecMeterReadingTextField.performTextInput("500")

        currentElecMeterReadingTextField.performTextInput("600")

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertDoesNotExist()

        assertThatResetTextFieldIconButtonIsEnabled()


        currentElecMeterReadingTextField.performTextClearance()

        currentElecMeterReadingTextField.performTextInput("400")

        currentElecMeterReadingIsLessThanPreviousElecMeterReadingErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()
    }

    // Boundary case
    @Test
    fun currentElecMeterReadingTextField_WithMinimumValidInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {

        currentElecMeterReadingTextField.performTextInput("0")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(0, calculatorViewModel.currentElecMeterReading.value)

    }

    @Test
    fun currentElecMeterReadingTextField_WithMaximumValidInput_UpdatesTheCurrentElecMeterReadingVariableInTheCalculatorViewModel() {

        currentElecMeterReadingTextField.performTextInput(Int.MAX_VALUE.toString())

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(Int.MAX_VALUE, calculatorViewModel.currentElecMeterReading.value)

    }
}