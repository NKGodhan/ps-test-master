package com.ps.test.code.mobilesdeexercise.presentation.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ps.test.code.mobilesdeexercise.R
import com.ps.test.code.mobilesdeexercise.databinding.FragmentMainBinding
import com.ps.test.code.mobilesdeexercise.presentation.main.ui.adapter.RecyclerViewAdapter
import com.ps.test.code.mobilesdeexercise.presentation.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null

    private val _mainViewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isDestinationLoaded = true
    private var shipmentsList: List<String> = listOf()
    private var driversList: List<String> = listOf()

    private lateinit var rvAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialize() {
        initViews()
        initViewsListeners()
        initAdapter()
        initDataObservers()
    }

    private fun initViews() {
        val dividerItemDecoration = DividerItemDecoration(
            requireActivity(),
            LinearLayoutManager.VERTICAL
        )
        try {
            dividerItemDecoration.setDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.recycler_view_item_divider,
                    null
                )!!
            )

            binding.recyclerView.addItemDecoration(dividerItemDecoration)
        } catch (e: NullPointerException) {
            e.stackTrace
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Drawable cannot be null. Check the resources again!")
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun initViewsListeners() {
        binding.filterView.setOnClickListener { view ->
            val changedDrawableIcon = ContextCompat.getDrawable(
                requireContext(),
                if (isDestinationLoaded) R.drawable.ic_driver_profile else R.drawable.ic_destination
            )
            (view as AppCompatTextView).setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                changedDrawableIcon,
                null
            )
            updateAdapterData(if (isDestinationLoaded) driversList else shipmentsList)
            isDestinationLoaded = !isDestinationLoaded
        }
    }

    private fun initAdapter() {
        rvAdapter = RecyclerViewAdapter(emptyList())
        binding.recyclerView.adapter = rvAdapter
    }

    private fun updateAdapterData(data: List<String>) {
        rvAdapter.setData(data)
    }

    private fun initDataObservers() {
        _mainViewModel.getShipmentData().observe(viewLifecycleOwner) { result ->
            result.shipments?.apply {
                shipmentsList = this
                updateAdapterData(shipmentsList)
            }
        }

        _mainViewModel.getDriverData().observe(viewLifecycleOwner) { result ->
            result.drivers?.apply {
                driversList = this
            }
        }
    }
}