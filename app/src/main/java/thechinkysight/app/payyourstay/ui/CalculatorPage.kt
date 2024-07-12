package thechinkysight.app.payyourstay.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.enums.Screen
import thechinkysight.app.payyourstay.ui.enums.TextField
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@Composable
fun CalculatorPage(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel,
    navController: NavHostController
) {

    val fillMaxWidthModifier: Modifier = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        ElectricityDataInputTextFields(
            modifier = fillMaxWidthModifier, calculatorViewModel = calculatorViewModel
        )
        Spacer(modifier = Modifier.height(50.dp))
        OtherUtilitiesDataInputTextFields(
            modifier = fillMaxWidthModifier, calculatorViewModel = calculatorViewModel
        )
        Spacer(modifier = Modifier.height(50.dp))
        RentDataInputTextField(
            modifier = fillMaxWidthModifier, calculatorViewModel = calculatorViewModel
        )
        Spacer(modifier = Modifier.height(50.dp))
        CalculateButton(modifier = fillMaxWidthModifier,
            calculatorViewModel = calculatorViewModel,
            onClick = {

                calculatorViewModel.calculateTotal(
                    previousElecMeterReading = calculatorViewModel.previousElecMeterReading.value
                        ?: 0,
                    currentElecMeterReading = calculatorViewModel.currentElecMeterReading.value
                        ?: 0,
                    electricityRatePerUnit = calculatorViewModel.electricityRatePerUnit.value ?: 0,
                    waterFee = calculatorViewModel.waterFee.value ?: 0,
                    garbageFee = calculatorViewModel.garbageFee.value ?: 0,
                    rent = calculatorViewModel.rent.value ?: 0
                )

                navController.navigate(route = Screen.InvoicePage.name)


            })
        Spacer(modifier = Modifier.height(30.dp))
    }

}

@Composable
private fun ElectricityDataInputTextFields(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {

    val previousElecMeterReading by calculatorViewModel.previousElecMeterReading.collectAsState()
    val currentElecMeterReading by calculatorViewModel.currentElecMeterReading.collectAsState()
    val electricityRatePerUnit by calculatorViewModel.electricityRatePerUnit.collectAsState()
    val isPreviousElecMeterReadingTextFieldInError by calculatorViewModel.isPreviousElecMeterReadingTextFieldInError.collectAsState()
    val isCurrentElecMeterReadingTextFieldInError by calculatorViewModel.isCurrentElecMeterReadingTextFieldInError.collectAsState()
    val isCurrentElecMeterReadingLessThanPreviousElecMeterReading by calculatorViewModel.isCurrentElecMeterReadingLessThanPreviousElecMeterReading.collectAsState()
    val isElectricityRatePerUnitTextFieldInError by calculatorViewModel.isElectricityRatePerUnitTextFieldInError.collectAsState()

    Column {

        DataInputTextField(
            modifier = modifier,
            value = previousElecMeterReading?.toString() ?: "",
            onValueChange = {
                calculatorViewModel.validateAndUpdateTextFieldValue(
                    it, previousElecMeterReading, TextField.PreviousElecMeterReading
                )

                val validatedPreviousElecMeterReadingValue =
                    calculatorViewModel.validateTextFieldValue(it, previousElecMeterReading)

                if (validatedPreviousElecMeterReadingValue != null && currentElecMeterReading != null) {

                    if (validatedPreviousElecMeterReadingValue > (currentElecMeterReading ?: 0)) {

                        calculatorViewModel.updateErrorStatusForTextField(
                            TextField.CurrentElecMeterReading, true
                        )

                        calculatorViewModel.updateElecMeterReadingComparison(true)

                    } else {
                        calculatorViewModel.updateErrorStatusForTextField(
                            TextField.CurrentElecMeterReading, false
                        )
                        calculatorViewModel.updateElecMeterReadingComparison(false)
                    }
                } else {
                    calculatorViewModel.updateElecMeterReadingComparison(false)
                }
            },
            isError = isPreviousElecMeterReadingTextFieldInError,
            errorText = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Previous reading"
                    )
                )
            },
            textField = TextField.PreviousElecMeterReading,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
            labelStringResourceId = R.string.previous_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
            onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue
        )

        Spacer(modifier = Modifier.height(35.dp))

        DataInputTextField(modifier = modifier,
            value = currentElecMeterReading?.toString() ?: "",
            onValueChange = {
                calculatorViewModel.validateAndUpdateTextFieldValue(
                    it, currentElecMeterReading, TextField.CurrentElecMeterReading
                )

                val validatedCurrentElecMeterReadingValue =
                    calculatorViewModel.validateTextFieldValue(it, currentElecMeterReading)

                if (previousElecMeterReading != null && validatedCurrentElecMeterReadingValue != null) {

                    if (validatedCurrentElecMeterReadingValue < (previousElecMeterReading ?: 0)) {

                        calculatorViewModel.updateErrorStatusForTextField(
                            TextField.CurrentElecMeterReading, true
                        )

                        calculatorViewModel.updateElecMeterReadingComparison(true)

                    } else {
                        calculatorViewModel.updateErrorStatusForTextField(
                            TextField.CurrentElecMeterReading, false
                        )
                        calculatorViewModel.updateElecMeterReadingComparison(false)
                    }
                } else {
                    calculatorViewModel.updateElecMeterReadingComparison(false)
                }

            },
            isCurrentElecMeterReadingLessThanPreviousElecMeterReading = isCurrentElecMeterReadingLessThanPreviousElecMeterReading,
            isError = isCurrentElecMeterReadingTextFieldInError,
            errorText = if (isCurrentElecMeterReadingLessThanPreviousElecMeterReading) {
                {
                    Text(
                        text = stringResource(
                            id = R.string.error_current_elec_meter_reading_is_less_than_previous_elec_meter_reading
                        )
                    )
                }
            } else {
                {
                    Text(
                        text = stringResource(
                            id = R.string.error_text_field_is_empty, "Current reading"
                        )
                    )
                }
            },
            textField = TextField.CurrentElecMeterReading,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
            labelStringResourceId = R.string.current_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
            onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue)

        Spacer(modifier = Modifier.height(35.dp))

        DataInputTextField(modifier = modifier,
            value = electricityRatePerUnit?.toString() ?: "",
            onValueChange = {
                calculatorViewModel.validateAndUpdateTextFieldValue(
                    it, electricityRatePerUnit, TextField.ElectricityRatePerUnit
                )
            },
            isError = isElectricityRatePerUnitTextFieldInError,
            errorText = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Electricity rate"
                    )
                )
            },
            textField = TextField.ElectricityRatePerUnit,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
            labelStringResourceId = R.string.electricity_rate_per_unit,
            leadingIcon = R.drawable.payment_24,
            onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue
        )
    }
}

@Composable
private fun OtherUtilitiesDataInputTextFields(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {

    val garbageFee by calculatorViewModel.garbageFee.collectAsState()
    val waterFee by calculatorViewModel.waterFee.collectAsState()
    val isWaterFeeTextFieldInError by calculatorViewModel.isWaterFeeTextFieldInError.collectAsState()
    val isGarbageFeeTextFieldInError by calculatorViewModel.isGarbageFeeTextFieldInError.collectAsState()

    DataInputTextField(modifier = modifier,
        value = waterFee?.toString() ?: "",
        onValueChange = {
            calculatorViewModel.validateAndUpdateTextFieldValue(
                it, waterFee, TextField.WaterFee
            )
        },
        isError = isWaterFeeTextFieldInError,
        errorText = {
            Text(
                text = stringResource(
                    id = R.string.error_text_field_is_empty, "Water fee"
                )
            )
        },
        textField = TextField.WaterFee,
        updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
        labelStringResourceId = R.string.water_fee,
        leadingIcon = R.drawable.water_drop_24,
        onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue
    )
    Spacer(modifier = Modifier.height(35.dp))
    DataInputTextField(modifier = modifier,
        value = garbageFee?.toString() ?: "",
        onValueChange = {
            calculatorViewModel.validateAndUpdateTextFieldValue(
                it, garbageFee, TextField.GarbageFee
            )
        },
        isError = isGarbageFeeTextFieldInError,
        errorText = {
            Text(
                text = stringResource(
                    id = R.string.error_text_field_is_empty, "Garbage fee"
                )
            )
        },
        textField = TextField.GarbageFee,
        updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
        labelStringResourceId = R.string.garbage_fee,
        leadingIcon = R.drawable.delete_24,
        onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue
    )
}

@Composable
private fun RentDataInputTextField(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {

    val rent by calculatorViewModel.rent.collectAsState()
    val isRentTextFieldInError by calculatorViewModel.isRentTextFieldInError.collectAsState()


    DataInputTextField(
        modifier = modifier,
        value = rent?.toString() ?: "",
        onValueChange = {
            calculatorViewModel.validateAndUpdateTextFieldValue(
                it, rent, TextField.Rent
            )
        },
        textField = TextField.Rent,
        onTrailingIconButtonClick = calculatorViewModel::validateAndUpdateTextFieldValue,
        updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField,
        isError = isRentTextFieldInError,
        errorText = {
            Text(
                text = stringResource(
                    id = R.string.error_text_field_is_empty, "Rent"
                )
            )
        },
        labelStringResourceId = R.string.rent,
        leadingIcon = R.drawable.payment_24,
        imeAction = ImeAction.Done
    )
}

@Composable
private fun CalculateButton(
    modifier: Modifier,
    calculatorViewModel: CalculatorViewModel,
    onClick: () -> Unit,
) {

    val isPreviousElecMeterReadingTextFieldInError by calculatorViewModel.isPreviousElecMeterReadingTextFieldInError.collectAsState()
    val previousElecMeterReading by calculatorViewModel.previousElecMeterReading.collectAsState()

    val isCurrentElecMeterReadingTextFieldInError by calculatorViewModel.isCurrentElecMeterReadingTextFieldInError.collectAsState()
    val currentElecMeterReading by calculatorViewModel.currentElecMeterReading.collectAsState()

    val isElectricityRatePerUnitTextFieldInError by calculatorViewModel.isElectricityRatePerUnitTextFieldInError.collectAsState()
    val electricityRatePerUnit by calculatorViewModel.electricityRatePerUnit.collectAsState()


    val isWaterFeeTextFieldInError by calculatorViewModel.isWaterFeeTextFieldInError.collectAsState()
    val waterFee by calculatorViewModel.waterFee.collectAsState()

    val isGarbageFeeTextFieldInError by calculatorViewModel.isGarbageFeeTextFieldInError.collectAsState()
    val garbageFee by calculatorViewModel.garbageFee.collectAsState()

    val isRentTextFieldInError by calculatorViewModel.isRentTextFieldInError.collectAsState()
    val rent by calculatorViewModel.rent.collectAsState()


    val shouldTheButtonBeEnable: Boolean =
        !((isPreviousElecMeterReadingTextFieldInError || previousElecMeterReading == null) || (isCurrentElecMeterReadingTextFieldInError || currentElecMeterReading == null) || (isElectricityRatePerUnitTextFieldInError || electricityRatePerUnit == null) || (isWaterFeeTextFieldInError || waterFee == null) || (isGarbageFeeTextFieldInError || garbageFee == null) || (isRentTextFieldInError || rent == null))


    Button(
        onClick = onClick, modifier = modifier.height(56.dp), shape = RoundedCornerShape(4.dp),
        // TODO: Write test for the enablility of the button
        enabled = shouldTheButtonBeEnable
    ) {
        Text(text = stringResource(id = R.string.calculate).uppercase())
    }
}


@Composable
private fun DataInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textField: TextField,
    isError: Boolean,
    isCurrentElecMeterReadingLessThanPreviousElecMeterReading: Boolean = false,
    errorText: @Composable (() -> Unit),
    updateErrorStatusForTextField: (TextField, Boolean) -> Unit,
    onTrailingIconButtonClick: (String, Int?, TextField) -> Unit,
    @StringRes labelStringResourceId: Int,
    @DrawableRes leadingIcon: Int,
    imeAction: ImeAction = ImeAction.Next
) {
    var isTextFieldInInitialFocusEventChange = remember {
        true
    }

    val isTextFieldCurrentElecMeterReading = textField == TextField.CurrentElecMeterReading

    OutlinedTextField(modifier = modifier.onFocusEvent {

        if (!isTextFieldInInitialFocusEventChange) {

            if (value.isEmpty() || (isTextFieldCurrentElecMeterReading && isCurrentElecMeterReadingLessThanPreviousElecMeterReading)) {
                updateErrorStatusForTextField(textField, true)
            } else {
                updateErrorStatusForTextField(textField, false)
            }

        } else {
            isTextFieldInInitialFocusEventChange = false
        }

    },
        value = value,
        label = { Text(text = stringResource(labelStringResourceId)) },
        onValueChange = {

            if (!isTextFieldInInitialFocusEventChange && it.isEmpty()) {
                updateErrorStatusForTextField(textField, true)
            } else {
                updateErrorStatusForTextField(textField, false)
            }

            onValueChange(it)
        },
        singleLine = true,
        isError = isError,
        supportingText = if (isError) {
            errorText
        } else null,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon), contentDescription = null
            )
        },
        trailingIcon = {
            if (!isError) IconButton(
                modifier = Modifier.testTag(stringResource(id = labelStringResourceId)), onClick = {

                    onTrailingIconButtonClick("", null, textField)

                    if (!isTextFieldInInitialFocusEventChange) {
                        updateErrorStatusForTextField(textField, true)
                    }

                }, enabled = value.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel_24px),
                    contentDescription = null
                )

            } else Icon(
                painter = painterResource(id = R.drawable.error_24),
                contentDescription = stringResource(
                    id = R.string.text_field_error_icon
                )
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number, imeAction = imeAction
        )
    )
}