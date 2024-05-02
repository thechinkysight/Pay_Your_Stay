package thechinkysight.app.payyourstay.calculatorviewmodel.validateandupdatevaluefunction

import junit.framework.TestCase.assertEquals
import org.junit.Test
import thechinkysight.app.payyourstay.ui.enum.TextField
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

class ValidateAndUpdateValueFunctionWithWaterFeeProperty {

    private val calculatorViewModel = CalculatorViewModel()

    // Success path

    /*
     This test mimics the initial state where the `oldValue` represents the initial value of `waterFee`,
     which is null. After user updates or changes the value of the "WaterFeeTextField", the changes will be
     reflected on `waterFee` property.
     */
    @Test
    fun validateAndUpdateValue_validInputWithOldValueAsNull_updatesWaterFeeWithCurrentValue() {

        val currentValue = "5060"

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = null, TextField.WaterFee
        )

        assertEquals(
            currentValue.toInt(), calculatorViewModel.waterFee.value
        )

    }

    /*
    This test mimics the state where the user has already input some valid value (represented by the `oldValue`)
    to the "WaterFeeTextField", and now we're clearing the value and asserting `waterFee`
    to be null which is it's initial state.
    */
    @Test
    fun validateAndUpdateValue_validInputWithEmptyCurrentValue_updatesWaterFeeWithNull() {

        calculatorViewModel.validateAndUpdateValue(
            currentValue = "", oldValue = 5060, TextField.WaterFee
        )

        assertEquals(null, calculatorViewModel.waterFee.value)

    }


    /*
    This test mimics the state where the user input is equal to `Int.MAX_VALUE` and updates the `waterFee` with
    `currentValue` which is `Int.MAX_VALUE`.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsEqualToINTMAXVALUE_updatesWaterFeeWithCurrentValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val currentValue = Int.MAX_VALUE.toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.WaterFee
        )

        assertEquals(
            currentValue.toInt(), calculatorViewModel.waterFee.value
        )

    }

    /*
    This test mimics the state where the user input is less than `Int.MAX_VALUE` and updates the `waterFee` with
    `currentValue` which is `Int.MAX_VALUE`.
  */
    @Test
    fun validateAndUpdateValue_currentValueIsLessThanINTMAXVALUE_updatesWaterFeeWithCurrentValue() {

        val currentValue = (Int.MAX_VALUE - 1).toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.WaterFee
        )

        assertEquals(
            currentValue.toInt(), calculatorViewModel.waterFee.value
        )
    }


    // Error path

    /*
       This test mimics the state where the `currentValue` has non digit characters. In this case, the `oldValue` will be returned by simply
       ignoring the non digit character.
     */
    @Test
    fun validateAndUpdateValue_invalidInputWithCommaInCurrentValue_updatesWaterFeeWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue,"

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.WaterFee
        )

        assertEquals(oldValue, calculatorViewModel.waterFee.value)

    }

    @Test
    fun validateAndUpdateValue_invalidInputWithDecimalPointInCurrentValue_updatesWaterFeeWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue."

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.WaterFee
        )

        assertEquals(oldValue, calculatorViewModel.waterFee.value)

    }

    /*
       This test mimics the state where the `currentValue` is greater than `Int.MAX_VALUE` which is 2147483647.
       In this case, the `oldValue` will be returned by simply ignoring the value.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsGreaterThanINTMAXVALUE_updatesWaterFeeWithOldValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val oldValue = Int.MAX_VALUE

        // The value of `currentValue` is 2147483648 which is greater than `Int.MAX_VALUE`
        val currentValue = (oldValue.toLong() + 1).toString()

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.WaterFee
        )

        assertEquals(oldValue, calculatorViewModel.waterFee.value)

    }
}