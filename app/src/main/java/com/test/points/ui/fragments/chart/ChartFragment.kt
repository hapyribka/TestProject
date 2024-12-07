package com.test.points.ui.fragments.chart

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.points.business.models.Point
import com.test.points.databinding.FragmentChartBinding
import com.test.points.ui.adapters.PointsAdapter
import com.test.points.ui.utils.buildErrorMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChartViewModel by inject()
    private val pointsAdapter = PointsAdapter()
    private val dispatcherMain = Dispatchers.Main
    private val dispatcherIO = Dispatchers.IO
    private val scope = CoroutineScope(dispatcherIO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListener()
        subscribeForData()
        arguments?.let { arg ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arg.getParcelableArray<Point>("points", Point::class.java)?.let {
                    viewModel.setPoints(it)
                }
            } else {
                @Suppress("DEPRECATION")
                arg.getParcelableArray("points")?.let {
                    viewModel.setPoints(it as Array<Point>)
                }
            }
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.pointsList.layoutManager = layoutManager
        binding.pointsList.adapter = pointsAdapter
    }

    private fun initListener() {
        binding.saveButton.setOnClickListener {
            viewModel.saveViewAsBitmap(binding.chart)
        }
    }

    private fun subscribeForData() {
        viewModel.pointsListLiveData.observe(viewLifecycleOwner) {
            pointsAdapter.setItems(it)
            updateChartView(it)
        }
        viewModel.showLoaderLiveData.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorText = buildErrorMessage(requireContext(), it.first, it.second)
            if (!errorText.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_LONG).show()
                viewModel.cleanError()
            }
        }
    }

    private fun updateChartView(points: ArrayList<Point>) {
        scope.launch {
            delay(200)
            withContext(dispatcherMain) {
                if (binding.zoomLayout.width > 0) {
                    val layoutParams = binding.chart.layoutParams
                    layoutParams.width = binding.zoomLayout.width
                    layoutParams.height = binding.zoomLayout.height
                    binding.chart.layoutParams = layoutParams
                }
                binding.saveButton.isEnabled = true
                binding.chart.setPoints(points)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scope.cancel()
    }
}
