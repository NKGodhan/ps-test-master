package com.ps.test.code.mobilesdeexercise.presentation.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.ps.test.code.mobilesdeexercise.utils.isLengthEvenOrOdd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), RecyclerViewAdapter.RecyclerViewItemClickListener {
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

    override fun onItemClicked(index: Int) {
        _mainViewModel.getAddressDriverAssigned(shipmentsList[index].isLengthEvenOrOdd())
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
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Drawable cannot be null. Check the resources again!")
        } catch (e: Exception) {
            e.printStackTrace()
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
        rvAdapter = RecyclerViewAdapter(emptyList(), this)
        binding.recyclerView.adapter = rvAdapter
    }

    private fun updateAdapterData(data: List<String>) {
        rvAdapter.setData(data)
    }

    private fun initDataObservers() {
        _mainViewModel.getShipmentData().observe(viewLifecycleOwner) { result ->
            try {
                shipmentsList = result
                updateAdapterData(shipmentsList)
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                // Also can show error messages here accordingly
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        _mainViewModel.getDriverData().observe(viewLifecycleOwner) { result ->
            try {
                driversList = result
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                // Also can show error messages here accordingly
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        _mainViewModel._assignedDriverLiveData.observe(viewLifecycleOwner) { driverName ->
            if (driverName == null) {
                Toast.makeText(
                    requireActivity(),
                    "Something went wrong! Driver list is empty.",
                    Toast.LENGTH_LONG
                )
                    .show()
                return@observe
            }

            Toast.makeText(requireActivity(), "Assigned driver is: $driverName", Toast.LENGTH_SHORT)
                .show()
        }
    }
}