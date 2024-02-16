package thechinkysight.app.payyourstay.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thechinkysight.app.payyourstay.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private lateinit var _binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater)
        return _binding.root
    }

}