package thechinkysight.app.payyourstay.calculatorpage

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@RunWith(AndroidJUnit4::class)
class CalculatorPage {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val calculatorViewModel = CalculatorViewModel()

    private lateinit var previousElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var currentElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var electricityRatePerUnitTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var waterFeeTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var garbageFeeTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher
    private lateinit var rentTextFieldResetTextFieldIconButtonSemanticsMatcher: SemanticsMatcher


    @OptIn(ExperimentalTestApi::class)
    private fun assertTheExistenceOfAllResetTextFieldIconButtons() {
        composeTestRule.waitUntilNodeCount(
            previousElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )
        composeTestRule.waitUntilNodeCount(
            currentElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher, 1
        )
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
            previousElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
        composeTestRule.onNode(
            currentElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher
        ).assert(
            isNotEnabled()
        )
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
        previousElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.previous_elec_meter_reading
            )
        )
        currentElecMeterReadingTextFieldResetTextFieldIconButtonSemanticsMatcher = hasTestTag(
            composeTestRule.activity.getString(
                R.string.current_elec_meter_reading
            )
        )
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


}