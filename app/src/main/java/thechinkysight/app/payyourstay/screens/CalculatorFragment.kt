package thechinkysight.app.payyourstay.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import thechinkysight.app.payyourstay.R
import thechinkysight.app.payyourstay.databinding.FragmentCalculatorBinding
import thechinkysight.app.payyourstay.viewmodels.CalculatorViewModel


class CalculatorFragment : Fragment() {

    private lateinit var _binding: FragmentCalculatorBinding
    private val _calculatorViewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        _binding.calculatorViewModel = _calculatorViewModel
        _binding.lifecycleOwner = viewLifecycleOwner

        return _binding.root
    }

}