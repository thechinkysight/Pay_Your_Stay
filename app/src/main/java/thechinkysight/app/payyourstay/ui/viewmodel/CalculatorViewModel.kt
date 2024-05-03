package thechinkysight.app.payyourstay.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import thechinkysight.app.payyourstay.ui.enum.TextField

class CalculatorViewModel : ViewModel() {


    private val _previousElecMeterReading: MutableStateFlow<Int?> = MutableStateFlow(null)
    val previousElecMeterReading: StateFlow<Int?> = _previousElecMeterReading.asStateFlow()


    private val _currentElecMeterReading: MutableStateFlow<Int?> = MutableStateFlow(null)
    val currentElecMeterReading: StateFlow<Int?> = _currentElecMeterReading.asStateFlow()


    private val _electricityRatePerUnit: MutableStateFlow<Int?> = MutableStateFlow(null)
    val electricityRatePerUnit: StateFlow<Int?> = _electricityRatePerUnit.asStateFlow()


    private val _electricityExpense: MutableStateFlow<Int?> = MutableStateFlow(null)
    val electricityExpense: StateFlow<Int?> = _electricityExpense.asStateFlow()


    private val _waterFee: MutableStateFlow<Int?> = MutableStateFlow(null)
    val waterFee: StateFlow<Int?> = _waterFee.asStateFlow()


    private val _garbageFee: MutableStateFlow<Int?> = MutableStateFlow(null)
    val garbageFee: StateFlow<Int?> = _garbageFee.asStateFlow()


    private val _rent: MutableStateFlow<Int?> = MutableStateFlow(null)
    val rent: StateFlow<Int?> = _rent.asStateFlow()


    private val _total: MutableStateFlow<Int?> = MutableStateFlow(null)
    val total: StateFlow<Int?> = _total.asStateFlow()


    /**
     * This function validates the input and updates the respective StateFlow.
     *
     * @param currentValue The current value entered by the user as a String.
     * @param oldValue The previous value stored in the StateFlow as an Int.
     * @param textField The TextField that the user is currently interacting with.
     *
     * The function first checks if the `currentValue` is empty. If it is, it returns `null`.
     *
     * If `currentValue` is not empty, it checks if all characters in `currentValue` are digits using the `all { it.isDigit() }` function.
     * If they are, it then converts `currentValue` to a `Long` using `toLong()`. This is done because `toInt()` would throw a `NumberFormatException`
     * if `currentValue` represents a number larger than `Int.MAX_VALUE` (2,147,483,647). By first converting to a `Long`, we can safely check if the number
     * is within the range of an `Int` without risking an exception.
     *
     * If the `Long` value is less than or equal to `Int.MAX_VALUE`, it is safe to convert `currentValue` to an `Int` using `toInt()`.
     * If the `Long` value is greater than `Int.MAX_VALUE`, or if `currentValue` contains non-digit characters, the function ignores the update
     * and reverts to the old value (`oldValue`).
     *
     * Finally, the function calls `updateValue` with the new value (or the old value if the input was invalid) and the `textField` parameter to update the appropriate StateFlow.
     */
    fun validateAndUpdateTextFieldValue(currentValue: String, oldValue: Int?, textField: TextField) {
        val value = if (currentValue.isEmpty()) {
            null
        } else if (currentValue.all { it.isDigit() } && currentValue.toLong() <= Int.MAX_VALUE) {
            currentValue.toInt()
        } else {
            oldValue
        }

        updateTextFieldValue(value, textField)

    }


    private fun updateTextFieldValue(value: Int?, textField: TextField) {
        when (textField) {
            TextField.PreviousElecMeterReading -> _previousElecMeterReading.update { value }
            TextField.CurrentElecMeterReading -> _currentElecMeterReading.update { value }
            TextField.ElectricityRatePerUnit -> _electricityRatePerUnit.update { value }
            TextField.WaterFee -> _waterFee.update { value }
            TextField.GarbageFee -> _garbageFee.update { value }
            TextField.Rent -> _rent.update { value }
        }
    }


    fun calculateTotal(
        previousElecMeterReading: Int,
        currentElecMeterReading: Int,
        electricityRatePerUnit: Int,
        waterFee: Int,
        garbageFee: Int,
        rent: Int
    ) {

        if (previousElecMeterReading < 0) {
            throw IllegalArgumentException("Previous meter reading cannot be negative.")
        }
        if (currentElecMeterReading < 0) {
            throw IllegalArgumentException("Current meter reading cannot be negative.")
        }
        if (electricityRatePerUnit < 0) {
            throw IllegalArgumentException("Electricity rate per unit cannot be negative.")
        }
        if (waterFee < 0) {
            throw IllegalArgumentException("Water fee cannot be negative.")
        }
        if (garbageFee < 0) {
            throw IllegalArgumentException("Garbage fee cannot be negative.")
        }
        if (rent < 0) {
            throw IllegalArgumentException("Rent cannot be negative.")
        }

        if (currentElecMeterReading < previousElecMeterReading) {
            throw IllegalArgumentException("Current elec. meter reading cannot be less than previous elec. meter reading.")
        }

        val electricityExpense = calculateElectricityExpense(
            previousElecMeterReading = previousElecMeterReading,
            currentElecMeterReading = currentElecMeterReading,
            electricityRatePerUnit = electricityRatePerUnit
        )

        _electricityExpense.update { electricityExpense }

        _total.update { electricityExpense + waterFee + garbageFee + rent }
    }

    private fun calculateElectricityExpense(
        previousElecMeterReading: Int,
        currentElecMeterReading: Int,
        electricityRatePerUnit: Int,
    ): Int {

        val electricityUnit: Int = currentElecMeterReading - previousElecMeterReading

        return electricityUnit * electricityRatePerUnit
    }

}