package thechinkysight.app.payyourstay.ui.utility

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.enums.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier, navController: NavHostController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.CalculatorPage.name
    )

    CenterAlignedTopAppBar(modifier = modifier, title = {
        Text(
            text = stringResource(currentScreen.title).uppercase()
        )
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    ), navigationIcon = {
        if (navController.previousBackStackEntry != null) {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        }
    })
}



