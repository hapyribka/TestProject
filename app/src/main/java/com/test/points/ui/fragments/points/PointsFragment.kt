package com.test.points.ui.fragments.points

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.points.databinding.FragmentPointsBinding
import com.test.points.ui.utils.AppTextWatcher
import com.test.points.ui.utils.buildErrorMessage
import org.koin.android.ext.android.inject
import kotlin.getValue

class PointsFragment : Fragment(), AppTextWatcher {
    private var _binding: FragmentPointsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PointsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onResume() {
        super.onResume()
        initListeners()
        subscribeForData()
        viewModel.onResume()
    }

    private fun initListeners() {
        binding.countEditText.addTextChangedListener(this@PointsFragment)
        binding.startButton.setOnClickListener {
            if (viewModel.isValidCountText(binding.countEditText.text)) {
                val count = binding.countEditText.text.toString().trim().toInt()
                viewModel.getPoints(findNavController(), count)
            }
        }
    }

    private fun subscribeForData() {
        viewModel.showLoaderLiveData.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }
        viewModel.enableButtonLiveData.observe(viewLifecycleOwner) {
            binding.startButton.isEnabled = it
        }
        viewModel.pointsCountLiveData.observe(viewLifecycleOwner) {
            binding.countEditText.text = Editable.Factory.getInstance().newEditable(it.toString())
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorText = buildErrorMessage(requireContext(), it.first, it.second)
            if (!errorText.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_LONG).show()
                viewModel.cleanError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun afterTextChanged(editable: Editable?) {
        viewModel.checkEnableButton(editable)
    }
}
