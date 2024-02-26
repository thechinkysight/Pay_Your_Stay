package thechinkysight.app.payyourstay.screens

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
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
            binding.previousElecMeterReadingTextFieldTextInputLayout,
            R.string.error_message_for_empty_previous_reading,
            currentElecMeterReadingTextFieldTextInputLayout = binding.currentElecMeterReadingTextFieldTextInputLayout
        )
        showProperErrorOnTextField(
            binding.currentElecMeterReadingTextFieldTextInputLayout,
            R.string.error_message_for_empty_current_reading,
            previousElecMeterReadingTextFieldTextInputLayout = binding.previousElecMeterReadingTextFieldTextInputLayout
        )
        showProperErrorOnTextField(
            binding.electricityRatePerUnitTextFieldTextInputLayout,
            R.string.error_message_for_empty_electricity_rate
        )



        showProperErrorOnTextField(
            binding.waterFeeTextFieldTextInputLayout,
            R.string.error_message_for_empty_water_fee
        )
        showProperErrorOnTextField(
            binding.garbageFeeTextFieldTextInputLayout,
            R.string.error_message_for_empty_garbage_fee
        )



        showProperErrorOnTextField(
            binding.rentTextFieldTextInputLayout,
            R.string.error_message_for_empty_rent
        )
    }

    private fun showProperErrorOnTextField(
        textInputLayout: TextInputLayout,
        @StringRes errorMessageId: Int,
        previousElecMeterReadingTextFieldTextInputLayout: TextInputLayout? = null,
        currentElecMeterReadingTextFieldTextInputLayout: TextInputLayout? = null
    ) {

        val isItPreviousElecMeterReadingTextField: Boolean =
            currentElecMeterReadingTextFieldTextInputLayout != null

        val isItCurrentElecMeterReadingTextField: Boolean =
            previousElecMeterReadingTextFieldTextInputLayout != null


        textInputLayout.editText?.apply {

            setOnFocusChangeListener { _: View, hasFocus: Boolean ->
                showErrorWhenTextFieldIsEmptyAfterFocus(
                    errorMessageId,
                    textInputLayout,
                    hasFocus
                )

                if (isItPreviousElecMeterReadingTextField || isItCurrentElecMeterReadingTextField) {

                    if (isItPreviousElecMeterReadingTextField) {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            textInputLayout,
                            currentElecMeterReadingTextFieldTextInputLayout!!
                        )
                    } else {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            previousElecMeterReadingTextFieldTextInputLayout!!,
                            textInputLayout
                        )
                    }
                }
            }

            doAfterTextChanged { text: Editable? ->
                showErrorWhenTextFieldIsEmptyAfterTextChange(
                    textInputLayout,
                    errorMessageId,
                    text
                )

                if (isItPreviousElecMeterReadingTextField || isItCurrentElecMeterReadingTextField) {

                    if (isItPreviousElecMeterReadingTextField) {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            textInputLayout,
                            currentElecMeterReadingTextFieldTextInputLayout!!
                        )
                    } else {
                        showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
                            previousElecMeterReadingTextFieldTextInputLayout!!,
                            textInputLayout
                        )
                    }
                }

            }
        }
    }


    private fun showErrorWhenTextFieldIsEmptyAfterFocus(
        @StringRes errorMessageId: Int,
        textInputLayout: TextInputLayout,
        hasFocus: Boolean
    ) {
        if (!hasFocus) {
            val isTextFieldEmpty: Boolean = textInputLayout.editText?.text?.isEmpty() == true
            if (isTextFieldEmpty) {
                textInputLayout.error = getString(errorMessageId)
            } else {
                textInputLayout.error = null
            }
        }
    }


    private fun showErrorWhenTextFieldIsEmptyAfterTextChange(
        textInputLayout: TextInputLayout,
        @StringRes errorMessageId: Int,
        text: Editable?
    ) {
        if (text.toString().isEmpty()) {
            textInputLayout.error =
                getString(errorMessageId)
        } else {
            textInputLayout.error = null
        }
    }


    private fun showErrorWhenCurrentElecMeterReadingIsLessThanPreviousElecMeterReading(
        previousElecMeterReadingTextFieldTextInputLayout: TextInputLayout,
        currentElecMeterReadingTextFieldTextInputLayout: TextInputLayout
    ) {
        val previousElecMeterReading: String =
            previousElecMeterReadingTextFieldTextInputLayout.editText?.text.toString()

        val currentElecMeterReading: String =
            currentElecMeterReadingTextFieldTextInputLayout.editText?.text.toString()

        if (currentElecMeterReading.isNotEmpty()) {

            if (currentElecMeterReading.toInt() < (previousElecMeterReading.toIntOrNull()
                    ?: 0)
            ) {
                currentElecMeterReadingTextFieldTextInputLayout.error =
                    getString(R.string.error_message_for_current_reading_less_than_previous)
            } else {
                currentElecMeterReadingTextFieldTextInputLayout.error = null
            }

        }

    }

}