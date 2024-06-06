package thechinkysight.app.payyourstay.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.enum.TextField
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@Composable
fun CalculatorPage(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel = viewModel()
) {

    val fillMaxWidthModifier: Modifier = Modifier.fillMaxWidth()
    val isCurrentElecMeterReadingLessThanPreviousElecMeterReading =
        calculatorViewModel.isCurrentElecMeterReadingLessThanPreviousElecMeterReading.collectAsState().value

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(30.dp))
        ElectricityDataInputTextFields(modifier = fillMaxWidthModifier,
            previousElecMeterReadingValue = calculatorViewModel.previousElecMeterReading.collectAsState().value,
            currentElecMeterReadingValue = calculatorViewModel.currentElecMeterReading.collectAsState().value,
            electricityRatePerUnitValue = calculatorViewModel.electricityRatePerUnit.collectAsState().value,
            isPreviousElecMeterReadingTextFieldInError = calculatorViewModel.isPreviousElecMeterReadingTextFieldInError.collectAsState().value,
            errorTextForPreviousElecMeterReadingTextField = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Previous reading"
                    )
                )
            },
            isCurrentElecMeterReadingTextFieldInError = calculatorViewModel.isCurrentElecMeterReadingTextFieldInError.collectAsState().value,
            errorTextForCurrentElecMeterReadingTextField = if (isCurrentElecMeterReadingLessThanPreviousElecMeterReading) {
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
            updateElecMeterReadingComparison = calculatorViewModel::updateElecMeterReadingComparison,
            validateTextFieldValue = calculatorViewModel::validateTextFieldValue,
            isCurrentElecMeterReadingLessThanPreviousElecMeterReading = isCurrentElecMeterReadingLessThanPreviousElecMeterReading,
            isElectricityRatePerUnitTextFieldInError = calculatorViewModel.isElectricityRatePerUnitTextFieldInError.collectAsState().value,
            errorTextForElectricityRatePerUnitTextField = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Electricity rate"
                    )
                )
            },
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField
        )
        Spacer(modifier = Modifier.height(50.dp))
        OtherUtilitiesDataInputTextFields(
            modifier = fillMaxWidthModifier,
            garbageFeeValue = calculatorViewModel.garbageFee.collectAsState().value,
            waterFeeValue = calculatorViewModel.waterFee.collectAsState().value,
            isWaterFeeTextFieldInError = calculatorViewModel.isWaterFeeTextFieldInError.collectAsState().value,
            errorTextForWaterFeeTextField = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Water fee"
                    )
                )
            },
            isGarbageFeeTextFieldInError = calculatorViewModel.isGarbageFeeTextFieldInError.collectAsState().value,
            errorTextForGarbageFeeTextField = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Garbage fee"
                    )
                )
            },
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField
        )
        Spacer(modifier = Modifier.height(50.dp))
        RentDataInputTextField(
            modifier = fillMaxWidthModifier,
            rentValue = calculatorViewModel.rent.collectAsState().value,
            isRentTextFieldInError = calculatorViewModel.isRentTextFieldInError.collectAsState().value,
            errorTextForRentTextField = {
                Text(
                    text = stringResource(
                        id = R.string.error_text_field_is_empty, "Rent"
                    )
                )
            },
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue,
            updateErrorStatusForTextField = calculatorViewModel::updateErrorStatusForTextField
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {},
            modifier = fillMaxWidthModifier.height(56.dp),
            shape = RoundedCornerShape(4.dp),
            enabled = !(calculatorViewModel.isPreviousElecMeterReadingTextFieldInError.collectAsState().value || calculatorViewModel.isCurrentElecMeterReadingTextFieldInError.collectAsState().value || calculatorViewModel.isElectricityRatePerUnitTextFieldInError.collectAsState().value || calculatorViewModel.isWaterFeeTextFieldInError.collectAsState().value || calculatorViewModel.isGarbageFeeTextFieldInError.collectAsState().value || calculatorViewModel.isRentTextFieldInError.collectAsState().value)
        ) {
            Text(text = stringResource(id = R.string.calculate).uppercase())
        }
        Spacer(modifier = Modifier.height(30.dp))
    }

}

@Composable
private fun ElectricityDataInputTextFields(
    modifier: Modifier = Modifier,
    previousElecMeterReadingValue: Int?,
    currentElecMeterReadingValue: Int?,
    electricityRatePerUnitValue: Int?,
    onValueChange: (String, Int?, TextField) -> Unit,
    isPreviousElecMeterReadingTextFieldInError: Boolean,
    errorTextForPreviousElecMeterReadingTextField: @Composable (() -> Unit),
    isCurrentElecMeterReadingTextFieldInError: Boolean,
    errorTextForCurrentElecMeterReadingTextField: @Composable (() -> Unit),
    updateElecMeterReadingComparison: (Boolean) -> Unit,
    validateTextFieldValue: (String, Int?) -> Int?,
    isCurrentElecMeterReadingLessThanPreviousElecMeterReading: Boolean,
    isElectricityRatePerUnitTextFieldInError: Boolean,
    errorTextForElectricityRatePerUnitTextField: @Composable (() -> Unit),
    updateErrorStatusForTextField: (TextField, Boolean) -> Unit
) {

    Column {

        DataInputTextField(
            modifier = modifier,
            value = previousElecMeterReadingValue?.toString() ?: "",
            onValueChange = {
                onValueChange(
                    it, previousElecMeterReadingValue, TextField.PreviousElecMeterReading
                )

                val validatedPreviousElecMeterReadingValue =
                    validateTextFieldValue(it, previousElecMeterReadingValue)

                if (validatedPreviousElecMeterReadingValue != null && currentElecMeterReadingValue != null) {

                    if (validatedPreviousElecMeterReadingValue > currentElecMeterReadingValue) {

                        updateErrorStatusForTextField(TextField.CurrentElecMeterReading, true)

                        updateElecMeterReadingComparison(true)

                    } else {
                        updateErrorStatusForTextField(TextField.CurrentElecMeterReading, false)
                        updateElecMeterReadingComparison(false)
                    }
                } else {
                    updateElecMeterReadingComparison(false)
                }
            },
            isError = isPreviousElecMeterReadingTextFieldInError,
            errorText = errorTextForPreviousElecMeterReadingTextField,
            textField = TextField.PreviousElecMeterReading,
            updateErrorStatusForTextField = updateErrorStatusForTextField,
            labelStringResourceId = R.string.previous_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
        )

        Spacer(modifier = Modifier.height(35.dp))

        DataInputTextField(
            modifier = modifier,
            value = currentElecMeterReadingValue?.toString() ?: "",
            onValueChange = {
                onValueChange(
                    it, currentElecMeterReadingValue, TextField.CurrentElecMeterReading
                )

                val validatedCurrentElecMeterReadingValue =
                    validateTextFieldValue(it, currentElecMeterReadingValue)

                if (previousElecMeterReadingValue != null && validatedCurrentElecMeterReadingValue != null) {

                    if (validatedCurrentElecMeterReadingValue < previousElecMeterReadingValue) {

                        updateErrorStatusForTextField(TextField.CurrentElecMeterReading, true)

                        updateElecMeterReadingComparison(true)

                    } else {
                        updateErrorStatusForTextField(TextField.CurrentElecMeterReading, false)
                        updateElecMeterReadingComparison(false)
                    }
                } else {
                    updateElecMeterReadingComparison(false)
                }

            },
            isCurrentElecMeterReadingLessThanPreviousElecMeterReading = isCurrentElecMeterReadingLessThanPreviousElecMeterReading,
            isError = isCurrentElecMeterReadingTextFieldInError,
            errorText = errorTextForCurrentElecMeterReadingTextField,
            textField = TextField.CurrentElecMeterReading,
            updateErrorStatusForTextField = updateErrorStatusForTextField,
            labelStringResourceId = R.string.current_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
        )

        Spacer(modifier = Modifier.height(35.dp))

        DataInputTextField(
            modifier = modifier,
            value = electricityRatePerUnitValue?.toString() ?: "",
            onValueChange = {
                onValueChange(
                    it, electricityRatePerUnitValue, TextField.ElectricityRatePerUnit
                )
            },
            isError = isElectricityRatePerUnitTextFieldInError,
            errorText = errorTextForElectricityRatePerUnitTextField,
            textField = TextField.ElectricityRatePerUnit,
            updateErrorStatusForTextField = updateErrorStatusForTextField,
            labelStringResourceId = R.string.electricity_rate_per_unit,
            leadingIcon = R.drawable.payment_24,
        )
    }
}

@Composable
private fun OtherUtilitiesDataInputTextFields(
    modifier: Modifier = Modifier,
    waterFeeValue: Int?,
    garbageFeeValue: Int?,
    onValueChange: (String, Int?, TextField) -> Unit,
    isWaterFeeTextFieldInError: Boolean,
    errorTextForWaterFeeTextField: @Composable (() -> Unit),
    isGarbageFeeTextFieldInError: Boolean,
    errorTextForGarbageFeeTextField: @Composable (() -> Unit),
    updateErrorStatusForTextField: (TextField, Boolean) -> Unit
) {
    DataInputTextField(
        modifier = modifier,
        value = waterFeeValue?.toString() ?: "",
        onValueChange = {
            onValueChange(
                it, waterFeeValue, TextField.WaterFee
            )
        },
        isError = isWaterFeeTextFieldInError,
        errorText = errorTextForWaterFeeTextField,
        textField = TextField.WaterFee,
        updateErrorStatusForTextField = updateErrorStatusForTextField,
        labelStringResourceId = R.string.water_fee,
        leadingIcon = R.drawable.water_drop_24,
    )
    Spacer(modifier = Modifier.height(35.dp))
    DataInputTextField(
        modifier = modifier,
        value = garbageFeeValue?.toString() ?: "",
        onValueChange = {
            onValueChange(
                it, garbageFeeValue, TextField.GarbageFee
            )
        },
        isError = isGarbageFeeTextFieldInError,
        errorText = errorTextForGarbageFeeTextField,
        textField = TextField.GarbageFee,
        updateErrorStatusForTextField = updateErrorStatusForTextField,
        labelStringResourceId = R.string.garbage_fee,
        leadingIcon = R.drawable.delete_24,
    )
}

@Composable
private fun RentDataInputTextField(
    modifier: Modifier = Modifier,
    rentValue: Int?,
    onValueChange: (String, Int?, TextField) -> Unit,
    isRentTextFieldInError: Boolean,
    errorTextForRentTextField: @Composable (() -> Unit),
    updateErrorStatusForTextField: (TextField, Boolean) -> Unit
) {
    DataInputTextField(
        modifier = modifier,
        value = rentValue?.toString() ?: "",
        onValueChange = {
            onValueChange(
                it, rentValue, TextField.Rent
            )
        },
        textField = TextField.Rent,
        updateErrorStatusForTextField = updateErrorStatusForTextField,
        isError = isRentTextFieldInError,
        errorText = errorTextForRentTextField,
        labelStringResourceId = R.string.rent,
        leadingIcon = R.drawable.payment_24,
        imeAction = ImeAction.Done
    )
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
    @StringRes labelStringResourceId: Int,
    @DrawableRes leadingIcon: Int,
    imeAction: ImeAction = ImeAction.Next
) {
    var isTextFieldInInitialFocusEventChange = remember {
        true
    }

    val isTextFieldCurrentElecMeterReading = textField == TextField.CurrentElecMeterReading

    OutlinedTextField(
        modifier = modifier.onFocusEvent {

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
            // I think I need to disable the reset text field icon when the text field is null.
            if (!isError) IconButton(modifier = Modifier.testTag(stringResource(id = R.string.reset_text_field_button)),
                onClick = {}) {
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
        ),
    )
}