package thechinkysight.app.payyourstay.ui.utility

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.ui.extension.surfaceContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier, scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.calculator).uppercase()
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ), scrollBehavior = scrollBehavior
    )
}