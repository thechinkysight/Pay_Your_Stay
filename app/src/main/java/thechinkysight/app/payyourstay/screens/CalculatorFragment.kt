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

        showProperErrorOnTextField(
            binding.previousElecMeterReadingTextField.editText,
            R.string.error_message_for_empty_previous_reading,
            currentElecMeterReadingTextFieldTextInputEditText = binding.currentElecMeterReadingTextField.editText
        )
        showProperErrorOnTextField(
            binding.currentElecMeterReadingTextField.editText,
            R.string.error_message_for_empty_current_reading,
            previousElecMeterReadingTextFieldTextInputEditText = binding.previousElecMeterReadingTextField.editText
        )
        showProperErrorOnTextField(
            binding.electricityRatePerUnitTextField.editText,
            R.string.error_message_for_empty_electricity_rate
        )



        showProperErrorOnTextField(
            binding.waterFeeTextField.editText,
            R.string.error_message_for_empty_water_fee
        )
        showProperErrorOnTextField(
            binding.garbageFeeTextField.editText,
            R.string.error_message_for_empty_garbage_fee
        )



        showProperErrorOnTextField(
            binding.rentTextField.editText,
            R.string.error_message_for_empty_rent
        )
    }

    private fun showProperErrorOnTextField(
        textInputEditText: EditText?,
        @StringRes errorMessageId: Int,
        previousElecMeterReadingTextFieldTextInputEditText: EditText? = null,
        currentElecMeterReadingTextFieldTextInputEditText: EditText? = null
    ) {

        val isItPreviousElecMeterReadingTextField: Boolean =
            currentElecMeterReadingTextFieldTextInputEditText != null

        val isItCurrentElecMeterReadingTextField: Boolean =
            previousElecMeterReadingTextFieldTextInputEditText != null


        textInputEditText?.apply {

            setOnFocusChangeListener { v: View, hasFocus: Boolean ->
                showErrorWhenTextFieldIsEmptyAfterFocus(
                    errorMessageId,
                    v,
                    hasFocus
                )

                if (isItPreviousElecMeterReadingTextField || isItCurrentElecMeterReadingTextField) {

                    if (isItPreviousElecMeterReadingTextField) {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            this,
                            currentElecMeterReadingTextFieldTextInputEditText
                        )
                    } else {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            previousElecMeterReadingTextFieldTextInputEditText,
                            this
                        )
                    }
                }
            }

            doAfterTextChanged { text: Editable? ->
                showErrorWhenTextFieldIsEmptyAfterTextChange(
                    textInputEditText,
                    errorMessageId,
                    text
                )

                if (isItPreviousElecMeterReadingTextField || isItCurrentElecMeterReadingTextField) {

                    if (isItPreviousElecMeterReadingTextField) {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            this,
                            currentElecMeterReadingTextFieldTextInputEditText
                        )
                    } else {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            previousElecMeterReadingTextFieldTextInputEditText,
                            this
                        )
                    }
                }

            }
        }
    }


    private fun showErrorWhenTextFieldIsEmptyAfterFocus(
        @StringRes errorMessageId: Int,
        v: View,
        hasFocus: Boolean
    ) {
        if (!hasFocus) {
            val isTextFieldEmpty: Boolean = (v as TextInputEditText).text?.isEmpty() == true
            if (isTextFieldEmpty) {
                v.error = getString(errorMessageId)
            } else {
                v.error = null
            }
        }
    }


    private fun showErrorWhenTextFieldIsEmptyAfterTextChange(
        textInputEditText: EditText?,
        @StringRes errorMessageId: Int,
        text: Editable?
    ) {
        if (text.toString().isEmpty()) {
            textInputEditText?.error =
                getString(errorMessageId)
        } else {
            textInputEditText?.error = null
        }
    }


    private fun showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
        previousElecMeterReadingTextFieldTextInputEditText: EditText?,
        currentElecMeterReadingTextFieldTextInputEditText: EditText?
    ) {
        val previousElecMeterReading: String =
            previousElecMeterReadingTextFieldTextInputEditText?.text.toString()

        val currentElecMeterReading: String =
            currentElecMeterReadingTextFieldTextInputEditText?.text.toString()

        if (currentElecMeterReading.isNotEmpty()) {

            if (currentElecMeterReading.toInt() < (previousElecMeterReading.toIntOrNull()
                    ?: 0)
            ) {
                currentElecMeterReadingTextFieldTextInputEditText?.error =
                    getString(R.string.error_message_for_current_reading_less_than_previous)
            } else {
                currentElecMeterReadingTextFieldTextInputEditText?.error = null
            }

        }

    }

}