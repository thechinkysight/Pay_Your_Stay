package thechinkysight.app.payyourstay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import thechinkysight.app.payyourstay.ui.theme.PayYourStayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayYourStayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PayYourStayApp()
                }
            }
        }
    }
}

@Composable
fun PayYourStayApp() {



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PayYourStayTheme {
        PayYourStayApp()
    }
}