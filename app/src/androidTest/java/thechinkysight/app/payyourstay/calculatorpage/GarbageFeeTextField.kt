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
class GarbageFeeTextField {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    private lateinit var garbageFeeTextField: SemanticsNodeInteraction
    private lateinit var garbageFeeTextFieldIsEmptyErrorMessage: SemanticsNodeInteraction

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

        garbageFeeTextField = composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee
                )
            )
        )

        garbageFeeTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.garbage_fee, "Garbage fee"
                )
            )
        )

        resetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.garbage_fee
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
    fun garbageFeeTextField_Initialization_ExistWithDisabledResetTextFieldIconButton() {

        garbageFeeTextField.assertExists()

        assertThatResetTextFieldIconButtonIsDisabled()

    }


    @Test
    fun garbageFeeTextField_WithValidInput_ResetTextFieldIconButtonEnabled() {

        garbageFeeTextField.performTextInput("1909")

        assertThatResetTextFieldIconButtonIsEnabled()
    }


    @Test
    fun garbageFeeTextField_WithValidInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModel() {

        garbageFeeTextField.performTextInput("200")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(200, calculatorViewModel.garbageFee.value)
    }


    @Test
    fun garbageFeeTextField_WithValidInput_ResetTextFieldIconButtonClicked_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithNull() {

        garbageFeeTextField.performTextInput("200")

        assertThatResetTextFieldIconButtonIsEnabled().performClick()

        assertEquals(null, calculatorViewModel.garbageFee.value)

    }

    // Error Path

    // Test to test the behaviour of the text field when it's focused for the first time, then unfocused, and then refocus.
    @Test
    fun garbageFeeTextField_WithNoValue_ShowsErrorOnTheTextFieldOnInitialFocusAndUnfocusAndRefocus() {

        val currentElecMeterReadingTextFieldIsEmptyErrorMessage = composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.error_text_field_is_empty, "Current reading"
                )
            )
        )

        garbageFeeTextField.performClick()

        garbageFeeTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons()


        // Changing the focus to "Current Elec Meter Reading" text field via taking the focus away from "Garbage Fee" text field.
        composeTestRule.onNode(
            hasSetTextAction() and hasText(
                composeTestRule.activity.getString(
                    R.string.current_elec_meter_reading
                )
            )
        ).performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        garbageFeeTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)


        // Refocusing to "Garbage Fee" text field.
        garbageFeeTextField.performClick()

        currentElecMeterReadingTextFieldIsEmptyErrorMessage.assertExists()

        garbageFeeTextFieldIsEmptyErrorMessage.assertExists()

        assertTheExistenceOfNumberOfErrorIcons(count = 2)
    }


    // Test to test the behaviour when the input to the text field is greater than `Int.MAX_VALUE`.
    @Test
    fun garbageFeeTextField_WithValueGreaterThanINTMAXVALUE_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {

        garbageFeeTextField.performTextInput((Int.MAX_VALUE.toLong() + 1).toString())

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.garbageFee.value)

    }


    // Test to test the behaviour when the input to the text field contains non-digit elements.
    @Test
    fun garbageFeeTextField_WithNonDigitInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModelWithOldValue() {

        garbageFeeTextField.performTextInput("200,")

        assertThatResetTextFieldIconButtonIsDisabled()

        assertEquals(null, calculatorViewModel.garbageFee.value)
    }


    // Boundary case
    @Test
    fun garbageFeeTextField_WithMinimumValidInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModel() {

        garbageFeeTextField.performTextInput("0")

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(0, calculatorViewModel.garbageFee.value)

    }

    @Test
    fun garbageFeeTextField_WithMaximumValidInput_UpdatesTheGarbageFeeVariableInTheCalculatorViewModel() {

        garbageFeeTextField.performTextInput(Int.MAX_VALUE.toString())

        assertThatResetTextFieldIconButtonIsEnabled()

        assertEquals(Int.MAX_VALUE, calculatorViewModel.garbageFee.value)

    }

}