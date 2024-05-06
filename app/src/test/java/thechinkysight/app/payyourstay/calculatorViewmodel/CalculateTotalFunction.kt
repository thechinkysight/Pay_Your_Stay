package thechinkysight.app.payyourstay.calculatorViewmodel

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

class CalculateTotalFunction {

    private val calculatorViewModel = CalculatorViewModel()

    // Success path

    @Test
    fun calculateTotal_withValidInput_updatesElectricityExpenseAndTotalCorrectly() {

        calculatorViewModel.calculateTotal(
            previousElecMeterReading = 1050,
            currentElecMeterReading = 1100,
            electricityRatePerUnit = 15,
            waterFee = 300,
            garbageFee = 200,
            rent = 15000
        )

        assertEquals(750, calculatorViewModel.electricityExpense.value)

        assertEquals(16250, calculatorViewModel.total.value)

    }

    // Error path

    @Test
    fun calculateTotal_withNegativePreviousElecMeterReading_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = -1,
                currentElecMeterReading = 100,
                electricityRatePerUnit = 15,
                waterFee = 300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Previous meter reading cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateTotal_withNegativeCurrentElecMeterReading_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 100,
                currentElecMeterReading = -1,
                electricityRatePerUnit = 15,
                waterFee = 300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Current meter reading cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateTotal_withNegativeElectricityRatePerUnit_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 100,
                currentElecMeterReading = 150,
                electricityRatePerUnit = -15,
                waterFee = 300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Electricity rate per unit cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateTotal_withNegativeWaterFee_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 100,
                currentElecMeterReading = 150,
                electricityRatePerUnit = 15,
                waterFee = -300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Water fee cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateTotal_withNegativeGarbageFee_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 100,
                currentElecMeterReading = 150,
                electricityRatePerUnit = 15,
                waterFee = 300,
                garbageFee = -200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Garbage fee cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateTotal_withNegativeRent_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 100,
                currentElecMeterReading = 150,
                electricityRatePerUnit = 15,
                waterFee = 300,
                garbageFee = 200,
                rent = -15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals("Rent cannot be negative.", e.message)

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }

    @Test
    fun calculateElectricityExpense_withCurrentElecMeterReadingLessThanPreviousElecMeterReading_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 150,
                currentElecMeterReading = 100,
                electricityRatePerUnit = 15,
                waterFee = 300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")

        } catch (e: IllegalArgumentException) {

            assertEquals(
                "Current elec. meter reading cannot be less than previous elec. meter reading.",
                e.message
            )

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)

        }
    }

    @Test
    fun calculateElectricityExpense_withIntMAXVALUEAsElectricityRatePerUnit_throwsIllegalArgumentExceptionAndDoesNotUpdateElectricityExpenseAndTotal() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = 10,
                currentElecMeterReading = 150,
                electricityRatePerUnit = Int.MAX_VALUE,
                waterFee = 300,
                garbageFee = 200,
                rent = 15000
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals(
                "The electricity expense exceeds the maximum integer value.", e.message
            )

            assertEquals(null, calculatorViewModel.electricityExpense.value)

            assertEquals(null, calculatorViewModel.total.value)
        }
    }


    // Boundary case

    @Test
    fun calculateTotal_withZeroAsArgumentsToTheParameters_updatesElectricityExpenseAndTotalWithZero() {
        calculatorViewModel.calculateTotal(
            previousElecMeterReading = 0,
            currentElecMeterReading = 0,
            electricityRatePerUnit = 0,
            waterFee = 0,
            garbageFee = 0,
            rent = 0
        )

        assertEquals(0, calculatorViewModel.electricityExpense.value)

        assertEquals(0, calculatorViewModel.total.value)

    }

    @Test
    fun calculateTotal_withINTMAXVALUEAsArgumentsToTheParameters_throwsIllegalArgumentExceptionAndDoesNotUpdateTotalButDoesUpdateElectricityExpense() {
        try {
            calculatorViewModel.calculateTotal(
                previousElecMeterReading = Int.MAX_VALUE,
                currentElecMeterReading = Int.MAX_VALUE,
                electricityRatePerUnit = Int.MAX_VALUE,
                waterFee = Int.MAX_VALUE,
                garbageFee = Int.MAX_VALUE,
                rent = Int.MAX_VALUE
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {

            assertEquals(
                "The total exceeds the maximum integer value.", e.message
            )

            assertEquals(null, calculatorViewModel.total.value)

            assertEquals(0, calculatorViewModel.electricityExpense.value)
        }

    }
}