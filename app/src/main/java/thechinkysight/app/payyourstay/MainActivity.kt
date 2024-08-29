package thechinkysight.app.payyourstay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.InvoicePage
import thechinkysight.app.payyourstay.ui.enums.Screen
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.utility.TopAppBar
import thechinkysight.app.payyourstay.ui.viewmodel.CalculatorViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayYourStayApp()
        }
    }
}

@Composable
fun PayYourStayApp(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    PayYourStayTheme {

        Scaffold(modifier = modifier.fillMaxSize(), topBar = {
            TopAppBar(
                navController = navController
            )
        }) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navController, startDestination = Screen.CalculatorPage.name
                ) {
                    composable(route = Screen.CalculatorPage.name) {
                        CalculatorPage(
                            calculatorViewModel = calculatorViewModel, navController = navController
                        )
                    }
                    composable(route = Screen.InvoicePage.name) {
                        InvoicePage(
                            calculatorViewModel = calculatorViewModel
                        )
                    }
                }
            }
        }
    }
}