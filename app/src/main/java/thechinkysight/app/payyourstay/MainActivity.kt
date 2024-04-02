package thechinkysight.app.payyourstay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayYourStayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold {
                        PayYourStayApp(
                            modifier = Modifier
                                .padding(it)
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PayYourStayApp(modifier: Modifier = Modifier) {

    val fillMaxWidthModifier: Modifier = Modifier.fillMaxWidth()

    Column(modifier = modifier) {
        ElectricityDataInputTextFields(modifier = fillMaxWidthModifier)
        Spacer(modifier = Modifier.height(60.dp))
        OtherUtilitiesDataInputTextFields(modifier = fillMaxWidthModifier)
        Spacer(modifier = Modifier.height(60.dp))
        RentDataInputTextFields(modifier = fillMaxWidthModifier)
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {},
            modifier = fillMaxWidthModifier.height(56.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = stringResource(id = R.string.calculate).uppercase())
        }
    }

}

@Composable
private fun ElectricityDataInputTextFields(modifier: Modifier = Modifier) {

    Column {

        DataInputTextField(
            modifier = modifier, placeHolderStringResourceId = R.string.previous_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
        )

        Spacer(modifier = Modifier.height(40.dp))

        DataInputTextField(
            modifier = modifier, placeHolderStringResourceId = R.string.current_elec_meter_reading,
            leadingIcon = R.drawable.bolt_24,
        )

        Spacer(modifier = Modifier.height(40.dp))

        DataInputTextField(
            modifier = modifier, placeHolderStringResourceId = R.string.electricity_rate_per_unit,
            leadingIcon = R.drawable.payment_24,
        )
    }


}

@Composable
private fun OtherUtilitiesDataInputTextFields(modifier: Modifier = Modifier) {
    DataInputTextField(
        modifier = modifier, placeHolderStringResourceId = R.string.water_fee,
        leadingIcon = R.drawable.water_drop_24,
    )
    Spacer(modifier = Modifier.height(40.dp))
    DataInputTextField(
        modifier = modifier, placeHolderStringResourceId = R.string.garbage_fee,
        leadingIcon = R.drawable.delete_24,
    )
}

@Composable
private fun RentDataInputTextFields(modifier: Modifier = Modifier) {
    DataInputTextField(
        modifier = modifier,
        placeHolderStringResourceId = R.string.rent,
        leadingIcon = R.drawable.payment_24,

        imeAction = ImeAction.Done
    )
}

@Composable
private fun DataInputTextField(
    modifier: Modifier,
    @StringRes placeHolderStringResourceId: Int,
    @DrawableRes leadingIcon: Int,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(modifier = modifier,
        value = "",
        onValueChange = {},
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon), contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal, imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = stringResource(placeHolderStringResourceId)
            )
        })
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PayYourStayTheme {
//        PayYourStayApp()
//    }
//}