package thechinkysight.app.payyourstay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import thechinkysight.app.payyourstay.ui.CalculatorPage
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme
import thechinkysight.app.payyourstay.ui.utility.TopAppBar

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayYourStayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = { TopAppBar(scrollBehavior = scrollBehavior) }) {
                        CalculatorPage(
                            modifier = Modifier
                                .padding(it)
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
        }
    }
}