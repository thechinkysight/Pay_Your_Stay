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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(30.dp))
        ElectricityDataInputTextFields(
            modifier = fillMaxWidthModifier,
            previousElecMeterReadingValue = calculatorViewModel.previousElecMeterReading.collectAsState().value,
            currentElecMeterReadingValue = calculatorViewModel.currentElecMeterReading.collectAsState().value,
            electricityRatePerUnitValue = calculatorViewModel.electricityRatePerUnit.collectAsState().value,
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue
        )
        Spacer(modifier = Modifier.height(50.dp))
        OtherUtilitiesDataInputTextFields(
            modifier = fillMaxWidthModifier,
            garbageFeeValue = calculatorViewModel.garbageFee.collectAsState().value,
            waterFeeValue = calculatorViewModel.waterFee.collectAsState().value,
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue
        )
        Spacer(modifier = Modifier.height(50.dp))
        RentDataInputTextFields(
            modifier = fillMaxWidthModifier,
            rentValue = calculatorViewModel.rent.collectAsState().value,
            onValueChange = calculatorViewModel::validateAndUpdateTextFieldValue
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {},
            modifier = fillMaxWidthModifier.height(56.dp),
            shape = RoundedCornerShape(4.dp)
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
    onValueChange: (String, Int?, TextField) -> Unit
) {

    Column {

        DataInputTextField(
            modifier = modifier,
            value = previousElecMeterReadingValue?.toString() ?: "",
            onValueChange = {
                onValueChange(
                    it, previousElecMeterReadingValue, TextField.PreviousElecMeterReading
                )
            },
            placeHolderStringResourceId = R.string.previous_elec_meter_reading,
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
            },
            placeHolderStringResourceId = R.string.current_elec_meter_reading,
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
            placeHolderStringResourceId = R.string.electricity_rate_per_unit,
            leadingIcon = R.drawable.payment_24,
        )
    }


}

@Composable
private fun OtherUtilitiesDataInputTextFields(
    modifier: Modifier = Modifier,
    waterFeeValue: Int?,
    garbageFeeValue: Int?,
    onValueChange: (String, Int?, TextField) -> Unit
) {
    DataInputTextField(
        modifier = modifier,
        value = waterFeeValue?.toString() ?: "",
        onValueChange = {
            onValueChange(
                it, waterFeeValue, TextField.WaterFee
            )
        },
        placeHolderStringResourceId = R.string.water_fee,
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
        placeHolderStringResourceId = R.string.garbage_fee,
        leadingIcon = R.drawable.delete_24,
    )
}

@Composable
private fun RentDataInputTextFields(
    modifier: Modifier = Modifier, rentValue: Int?, onValueChange: (String, Int?, TextField) -> Unit
) {
    DataInputTextField(
        modifier = modifier,
        value = rentValue?.toString() ?: "",
        onValueChange = {
            onValueChange(
                it, rentValue, TextField.Rent
            )
        },
        placeHolderStringResourceId = R.string.rent,
        leadingIcon = R.drawable.payment_24,
        imeAction = ImeAction.Done
    )
}


@Composable
private fun DataInputTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes placeHolderStringResourceId: Int,
    @DrawableRes leadingIcon: Int,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon), contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number, imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = stringResource(placeHolderStringResourceId)
            )
        })
}