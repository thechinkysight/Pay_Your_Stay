package thechinkysight.app.payyourstay.calculatorViewmodel.validateAndUpdateValue

import junit.framework.TestCase.assertEquals
import org.junit.Test
import thechinkysight.app.payyourstay.ui.enum.TextField
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

class ValidateAndUpdateValueFunctionWithElectricityRatePerUnitProperty {

    private val calculatorViewModel = CalculatorViewModel()

    // Success path

    /*
     This test mimics the initial state where the `oldValue` represents the initial value of `electricityRatePerUnit`,
     which is null. After user updates or changes the value of the "ElectricityRatePerUnitTextField", the changes will be
     reflected on `electricityRatePerUnit` property.
     */
    @Test
    fun validateAndUpdateValue_validInputWithOldValueAsNull_updatesElectricityRatePerUnitWithCurrentValue() {

        val currentValue = "5060"

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = null, TextField.ElectricityRatePerUnit
        )

        assertEquals(currentValue.toInt(), calculatorViewModel.electricityRatePerUnit.value)

    }

    /*
    This test mimics the state where the user has already input some valid value (represented by the `oldValue`)
    to the "ElectricityRatePerUnitTextField", and now we're clearing the value and asserting `electricityRatePerUnit`
    to be null which is it's initial state.
    */
    @Test
    fun validateAndUpdateValue_validInputWithEmptyCurrentValue_updatesElectricityRatePerUnitWithNull() {

        calculatorViewModel.validateAndUpdateValue(
            currentValue = "", oldValue = 5060, TextField.ElectricityRatePerUnit
        )

        assertEquals(null, calculatorViewModel.electricityRatePerUnit.value)

    }


    /*
    This test mimics the state where the user input is equal to `Int.MAX_VALUE` and updates the `electricityRatePerUnit` with
    `currentValue` which is `Int.MAX_VALUE`.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsEqualToINTMAXVALUE_updatesElectricityRatePerUnitWithCurrentValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val currentValue = Int.MAX_VALUE.toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.ElectricityRatePerUnit
        )

        assertEquals(currentValue.toInt(), calculatorViewModel.electricityRatePerUnit.value)

    }

    /*
    This test mimics the state where the user input is less than `Int.MAX_VALUE` and updates the `electricityRatePerUnit` with
    `currentValue` which is `Int.MAX_VALUE`.
  */
    @Test
    fun validateAndUpdateValue_currentValueIsLessThanINTMAXVALUE_updatesElectricityRatePerUnitWithCurrentValue() {

        val currentValue = (Int.MAX_VALUE - 1).toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.ElectricityRatePerUnit
        )

        assertEquals(currentValue.toInt(), calculatorViewModel.electricityRatePerUnit.value)
    }


    // Error path

    /*
       This test mimics the state where the `currentValue` has non digit characters. In this case, the `oldValue` will be returned by simply
       ignoring the non digit character.
     */
    @Test
    fun validateAndUpdateValue_invalidInputWithCommaInCurrentValue_updatesElectricityRatePerUnitWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue,"

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.ElectricityRatePerUnit
        )

        assertEquals(oldValue, calculatorViewModel.electricityRatePerUnit.value)

    }

    @Test
    fun validateAndUpdateValue_invalidInputWithDecimalPointInCurrentValue_updatesElectricityRatePerUnitWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue."

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.ElectricityRatePerUnit
        )

        assertEquals(oldValue, calculatorViewModel.electricityRatePerUnit.value)

    }

    /*
       This test mimics the state where the `currentValue` is greater than `Int.MAX_VALUE` which is 2147483647.
       In this case, the `oldValue` will be returned by simply ignoring the value.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsGreaterThanINTMAXVALUE_updatesElectricityRatePerUnitWithOldValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val oldValue = Int.MAX_VALUE

        // The value of `currentValue` is 2147483648 which is greater than `Int.MAX_VALUE`
        val currentValue = (oldValue.toLong() + 1).toString()

        calculatorViewModel.validateAndUpdateValue(
            currentValue = currentValue, oldValue = oldValue, TextField.ElectricityRatePerUnit
        )

        assertEquals(oldValue, calculatorViewModel.electricityRatePerUnit.value)

    }

}