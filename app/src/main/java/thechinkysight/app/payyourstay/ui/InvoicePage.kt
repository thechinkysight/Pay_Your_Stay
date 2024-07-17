package thechinkysight.app.payyourstay.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InvoicePage(
    modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel
) {

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "NP"))

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ElectricityMeterReadingSection(
            modifier = Modifier.padding(top = 35.dp), calculatorViewModel = calculatorViewModel
        )

        UtilitiesSection(
            modifier = Modifier.padding(top = 35.dp),
            calculatorViewModel = calculatorViewModel,
            currencyFormat = currencyFormat
        )

        RentSection(
            modifier = Modifier.padding(top = 35.dp),
            calculatorViewModel = calculatorViewModel,
            currencyFormat = currencyFormat
        )

        Spacer(modifier = Modifier.height(30.dp))

        HorizontalDivider(modifier = Modifier.padding(horizontal = 9.27.dp))

        Spacer(modifier = Modifier.height(35.dp))

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 9.27.dp),
                text = stringResource(id = R.string.total).uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = currencyFormat.format(calculatorViewModel.total.value ?: 0),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = modifier
                .height(56.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.share_24px), contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.share).uppercase())
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
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
                contentValue = previousElecMeterReading ?: 0
            )
        }, {
            InvoiceContent(
                leadingIcon = R.drawable.bolt_24,
                contentTitle = R.string.current_elec_meter_reading,
                contentValue = currentElecMeterReading ?: 0
            )
        })
    )
}

@Composable
private fun UtilitiesSection(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel,
    currencyFormat: NumberFormat
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
                currencyFormat = currencyFormat,
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.electricity_rate_per_unit,
                contentValue = electricityRatePerUnit ?: 0
            )
        }, {
            InvoiceContent(
                currencyFormat = currencyFormat,
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.electricity_expense,
                contentValue = electricityExpense ?: 0
            )
        }, {
            InvoiceContent(
                currencyFormat = currencyFormat,
                leadingIcon = R.drawable.water_drop_24,
                contentTitle = R.string.water_fee,
                contentValue = waterFee ?: 0
            )
        }, {
            InvoiceContent(
                currencyFormat = currencyFormat,
                leadingIcon = R.drawable.delete_24,
                contentTitle = R.string.garbage_fee,
                contentValue = garbageFee ?: 0
            )
        })
    )
}


@Composable
private fun RentSection(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel,
    currencyFormat: NumberFormat
) {

    val rent by calculatorViewModel.rent.collectAsState()

    InvoiceSection(
        modifier = modifier,
        sectionTitle = R.string.rent,
        contents = arrayOf<@Composable () -> Unit>({
            InvoiceContent(
                currencyFormat = currencyFormat,
                leadingIcon = R.drawable.payment_24,
                contentTitle = R.string.monthly_rent,
                contentValue = rent ?: 0
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
        Text(
            text = stringResource(id = sectionTitle).uppercase(),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(18.dp))

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
    currencyFormat: NumberFormat? = null,
    @DrawableRes leadingIcon: Int,
    @StringRes contentTitle: Int,
    contentValue: Int
) {

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = leadingIcon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = contentTitle),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = if (currencyFormat == null) contentValue.toString() else currencyFormat.format(
                contentValue
            ), style = MaterialTheme.typography.bodyLarge
        )
    }
}
