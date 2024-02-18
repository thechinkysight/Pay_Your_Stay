package thechinkysight.app.payyourstay.screens

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.databinding.FragmentCalculatorBinding
import thechinkysight.app.payyourstay.viewmodels.CalculatorViewModel


class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding
    private val calculatorViewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        binding.calculatorViewModel = calculatorViewModel
        binding.calculatorFragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showErrorWhenTextFieldIsEmpty(
            binding.previousElecMeterReadingTextField.editText,
            R.string.error_message_for_empty_previous_reading
        )
        showErrorWhenTextFieldIsEmpty(
            binding.currentElecMeterReadingTextField.editText,
            R.string.error_message_for_empty_current_reading
        )
        showErrorWhenTextFieldIsEmpty(
            binding.electricityRatePerUnitTextField.editText,
            R.string.error_message_for_empty_electricity_rate
        )



        showErrorWhenTextFieldIsEmpty(
            binding.waterFeeTextField.editText,
            R.string.error_message_for_empty_water_fee
        )
        showErrorWhenTextFieldIsEmpty(
            binding.garbageFeeTextField.editText,
            R.string.error_message_for_empty_garbage_fee
        )

        

        showErrorWhenTextFieldIsEmpty(
            binding.rentTextField.editText,
            R.string.error_message_for_empty_rent
        )
    }

    private fun showErrorWhenTextFieldIsEmpty(
        textInputEditText: EditText?,
        @StringRes errorMessageId: Int
    ) {
        showErrorWhenTextFieldIsEmptyAfterFocus(textInputEditText, errorMessageId)
        showErrorWhenTextFieldIsEmptyAfterTextChange(textInputEditText, errorMessageId)
    }

    private fun showErrorWhenTextFieldIsEmptyAfterFocus(
        textInputEditText: EditText?,
        @StringRes errorMessageId: Int
    ) {

        textInputEditText?.setOnFocusChangeListener { v: View, hasFocus: Boolean ->

            if (!hasFocus) {
                val isTextFieldEmpty: Boolean = (v as TextInputEditText).text?.isEmpty() == true
                if (isTextFieldEmpty) {
                    v.error = getString(errorMessageId)
                } else {
                    v.error = null
                }
            }

        }

    }


    private fun showErrorWhenTextFieldIsEmptyAfterTextChange(
        textInputEditText: EditText?,
        @StringRes errorMessageId: Int
    ) {
        textInputEditText?.doAfterTextChanged { text: Editable? ->

            if (text.toString().isEmpty()) {
                textInputEditText.error =
                    getString(errorMessageId)
            } else {
                textInputEditText.error = null
            }

        }
    }

}