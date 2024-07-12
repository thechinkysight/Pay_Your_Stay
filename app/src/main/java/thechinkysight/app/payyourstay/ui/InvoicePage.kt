package thechinkysight.app.payyourstay.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel

@Composable
fun InvoicePage(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        ElectricityMeterReadingSection(
            modifier = Modifier.padding(top = 35.dp), calculatorViewModel = calculatorViewModel
        )
        UtilitiesSection(
            modifier = Modifier.padding(top = 35.dp), calculatorViewModel = calculatorViewModel
        )
        RentSection(
            modifier = Modifier.padding(top = 35.dp), calculatorViewModel = calculatorViewModel
        )
    }

}

@Composable
private fun ElectricityMeterReadingSection(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {

    val previousElecMeterReading by calculatorViewModel.previousElecMeterReading.collectAsState()
    val currentElecMeterReading by calculatorViewModel.currentElecMeterReading.collectAsState()


    InvoiceSection(
        modifier = modifier,
        sectionTitle = R.string.electricity_meter_reading,
        contents = arrayOf<@Composable () -> Unit>({
            InvoiceContent(
                leadingIcon = R.drawable.bolt_24,
                contentTitle = R.string.previous_elec_meter_reading,
                contentValue = if (previousElecMeterReading != null) previousElecMeterReading.toString() else "0"
            )
        }, {
            InvoiceContent(
                leadingIcon = R.drawable.bolt_24,
                contentTitle = R.string.current_elec_meter_reading,
                contentValue = if (currentElecMeterReading != null) currentElecMeterReading.toString() else "0"
            )
        })
    )
}

@Composable
private fun UtilitiesSection(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {
    val electricityRatePerUnit by calculatorViewModel.electricityRatePerUnit.collectAsState()
    val electricityExpense by calculatorViewModel.electricityExpense.collectAsState()
    val waterFee by calculatorViewModel.waterFee.collectAsState()
    val garbageFee by calculatorViewModel.garbageFee.collectAsState()

    InvoiceSection(
        modifier = modifier,
        sectionTitle = R.string.utilities,
        contents = arrayOf<@Composable () -> Unit>({
            InvoiceContent(
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.electricity_rate_per_unit,
                contentValue = if (electricityRatePerUnit != null) electricityRatePerUnit.toString() else "0"
            )
        }, {
            InvoiceContent(
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.electricity_expense,
                contentValue = if (electricityRatePerUnit != null) electricityExpense.toString() else "0"
            )
        }, {
            InvoiceContent(
                leadingIcon = R.drawable.water_drop_24,
                contentTitle = R.string.water_fee,
                contentValue = if (electricityRatePerUnit != null) waterFee.toString() else "0"
            )
        }, {
            InvoiceContent(
                leadingIcon = R.drawable.delete_24,
                contentTitle = R.string.garbage_fee,
                contentValue = if (electricityRatePerUnit != null) garbageFee.toString() else "0"
            )
        })
    )
}


@Composable
private fun RentSection(modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel) {

    val rent by calculatorViewModel.rent.collectAsState()

    InvoiceSection(
        modifier = modifier,
        sectionTitle = R.string.rent,
        contents = arrayOf<@Composable () -> Unit>({
            InvoiceContent(
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.monthly_rent,
                contentValue = if (rent != null) rent.toString() else "0"
            )
        })
    )
}

@Composable
private fun InvoiceSection(
    modifier: Modifier = Modifier,
    @StringRes sectionTitle: Int,
    vararg contents: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(text = stringResource(id = sectionTitle).uppercase())

        Box(modifier = Modifier.height(18.dp))

        Box(modifier = Modifier.padding(start = 9.27.dp)) {
            Column {
                contents.forEachIndexed { index, content ->
                    content()
                    if (index < contents.size - 1) {
                        Box(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun InvoiceContent(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int,
    @StringRes contentTitle: Int,
    contentValue: String
) {

    Row(modifier = modifier.fillMaxWidth()) {
        Icon(
            painter = painterResource(id = leadingIcon), contentDescription = null,
        )
        Box(modifier = Modifier.padding(start = 22.93.dp))
        Text(text = stringResource(id = contentTitle))
        Box(modifier = Modifier.weight(1f))
        Text(text = "NPR $contentValue")
    }

}
