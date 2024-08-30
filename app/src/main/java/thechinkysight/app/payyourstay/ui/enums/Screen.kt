package thechinkysight.app.payyourstay.ui.enums

import androidx.annotation.StringRes
import thechinkysight.app.payyourstay.R

enum class Screen(@StringRes val title: Int) {
    CalculatorPage(title = R.string.calculator), InvoicePage(title = R.string.invoice)
}