package thechinkysight.app.payyourstay.extension

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import thechinkysight.app.payyourstay.ui.theme.surfaceBrightDark
import thechinkysight.app.payyourstay.ui.theme.surfaceBrightLight
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerDark
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerHighDark
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerHighLight
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerHighestDark
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerHighestLight
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerLight
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerLowDark
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerLowLight
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerLowestDark
import thechinkysight.app.payyourstay.ui.theme.surfaceContainerLowestLight
import thechinkysight.app.payyourstay.ui.theme.surfaceDimDark
import thechinkysight.app.payyourstay.ui.theme.surfaceDimLight

val ColorScheme.surfaceDim: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceDimLight else surfaceDimDark


val ColorScheme.surfaceBright: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceBrightLight else surfaceBrightDark


val ColorScheme.surfaceContainerLowest: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceContainerLowestLight else surfaceContainerLowestDark

val ColorScheme.surfaceContainerLow: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceContainerLowLight else surfaceContainerLowDark

val ColorScheme.surfaceContainer: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceContainerLight else surfaceContainerDark

val ColorScheme.surfaceContainerHigh: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceContainerHighLight else surfaceContainerHighDark

val ColorScheme.surfaceContainerHighest: Color
    @Composable @ReadOnlyComposable get() = if (!isSystemInDarkTheme()) surfaceContainerHighestLight else surfaceContainerHighestDark