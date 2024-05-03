package thechinkysight.app.payyourstay.calculatorViewmodel.validateAndUpdateValue

import org.junit.Assert
import org.junit.Test
import thechinkysight.app.payyourstay.ui.enum.TextField
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

class ValidateAndUpdateTextFieldValueFunctionWithPreviousElecMeterReadingProperty {

    private val calculatorViewModel = CalculatorViewModel()

    // Success path

    /*
     This test mimics the initial state where the `oldValue` represents the initial value of `previousElecMeterReading`,
     which is null. After user updates or changes the value of the "PreviousElecMeterReadingTextField", the changes will be
     reflected on `previousElecMeterReading` property.
     */
    @Test
    fun validateAndUpdateValue_validInputWithOldValueAsNull_updatesPreviousElecMeterReadingWithCurrentValue() {

        val currentValue = "5060"

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = null, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(
            currentValue.toInt(), calculatorViewModel.previousElecMeterReading.value
        )

    }

    /*
    This test mimics the state where the user has already input some valid value (represented by the `oldValue`)
    to the "PreviousElecMeterReadingTextField", and now we're clearing the value and asserting `previousElecMeterReading`
    to be null which is it's initial state.
    */
    @Test
    fun validateAndUpdateValue_validInputWithEmptyCurrentValue_updatesPreviousElecMeterReadingWithNull() {

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = "", oldValue = 5060, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(null, calculatorViewModel.previousElecMeterReading.value)

    }


    /*
    This test mimics the state where the user input is equal to `Int.MAX_VALUE` and updates the `previousElecMeterReading` with
    `currentValue` which is `Int.MAX_VALUE`.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsEqualToINTMAXVALUE_updatesPreviousElecMeterReadingWithCurrentValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val currentValue = Int.MAX_VALUE.toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = oldValue, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(
            currentValue.toInt(), calculatorViewModel.previousElecMeterReading.value
        )

    }

    /*
    This test mimics the state where the user input is less than `Int.MAX_VALUE` and updates the `previousElecMeterReading` with
    `currentValue` which is `Int.MAX_VALUE`.
  */
    @Test
    fun validateAndUpdateValue_currentValueIsLessThanINTMAXVALUE_updatesPreviousElecMeterReadingWithCurrentValue() {

        val currentValue = (Int.MAX_VALUE - 1).toString()

        val oldValue = 5060

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = oldValue, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(
            currentValue.toInt(), calculatorViewModel.previousElecMeterReading.value
        )
    }


    // Error path

    /*
       This test mimics the state where the `currentValue` has non digit characters. In this case, the `oldValue` will be returned by simply
       ignoring the non digit character.
     */
    @Test
    fun validateAndUpdateValue_invalidInputWithCommaInCurrentValue_updatesPreviousElecMeterReadingWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue,"

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = oldValue, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(oldValue, calculatorViewModel.previousElecMeterReading.value)

    }

    @Test
    fun validateAndUpdateValue_invalidInputWithDecimalPointInCurrentValue_updatesPreviousElecMeterReadingWithOldValue() {

        val oldValue = 5060
        val currentValue = "$oldValue."

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = oldValue, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(oldValue, calculatorViewModel.previousElecMeterReading.value)

    }

    /*
       This test mimics the state where the `currentValue` is greater than `Int.MAX_VALUE` which is 2147483647.
       In this case, the `oldValue` will be returned by simply ignoring the value.
     */
    @Test
    fun validateAndUpdateValue_currentValueIsGreaterThanINTMAXVALUE_updatesPreviousElecMeterReadingWithOldValue() {

        // The value of `Int.MAX_VALUE` is 2147483647
        val oldValue = Int.MAX_VALUE

        // The value of `currentValue` is 2147483648 which is greater than `Int.MAX_VALUE`
        val currentValue = (oldValue.toLong() + 1).toString()

        calculatorViewModel.validateAndUpdateTextFieldValue(
            currentValue = currentValue, oldValue = oldValue, TextField.PreviousElecMeterReading
        )

        Assert.assertEquals(oldValue, calculatorViewModel.previousElecMeterReading.value)

    }
}