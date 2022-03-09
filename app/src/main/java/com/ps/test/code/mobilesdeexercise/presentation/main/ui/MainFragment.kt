package com.ps.test.code.mobilesdeexercise.presentation.main.ui

import android.os.Bundle
import android.util.Log
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
import com.ps.test.code.mobilesdeexercise.utils.Utilities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), RecyclerViewAdapter.RecyclerViewItemClickListener {
    private val TAG = MainFragment::class.java.simpleName

    private var _binding: FragmentMainBinding? = null

    private val _mainViewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!

    private var isDestinationLoaded = true
    private var shipmentsList: List<String> = listOf()
    private var driversList: List<String> = listOf()
    private var associatedDriversList: List<String> = listOf()
    private var associatedAddressList: List<String> = listOf()

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
        val message: String = if (isDestinationLoaded) {
            "Assigned to the driver: ${associatedDriversList[index]}"
        } else {
            "Assigned address is: ${associatedAddressList[index]}"
        }

        Utilities.showToastMessage(requireContext(), message)
    }

    /**
     * Function to initialize presentation components
     */
    private fun initialize() {
        initViews()
        initViewsListeners()
        initAdapter()
        initDataObservers()
    }

    /**
     * Function to initialize inner views/components related to RecyclerView; including Filter text come button view
     */
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
        } catch (npe: NullPointerException) {
            npe.printStackTrace()
        } catch (iae: IllegalArgumentException) {
            iae.printStackTrace()
            Log.e(TAG, resources.getString(R.string.filter_drawable_error_message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Function to initialize event listeners for views
     */
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

    /**
     * Function to initialize RecyclerView adapter
     */
    private fun initAdapter() {
        rvAdapter = RecyclerViewAdapter(emptyList(), this)
        binding.recyclerView.adapter = rvAdapter
    }

    /**
     * Function to update data in the adapter
     */
    private fun updateAdapterData(data: List<String>) {
        rvAdapter.setData(data)
    }

    /**
     * Function to subscribe all the data observers to populate data and communicate through different UI portion, if needed.
     */
    private fun initDataObservers() {
        _mainViewModel.getShipmentData().observe(viewLifecycleOwner) { result ->
            try {
                shipmentsList = result
                updateAdapterData(shipmentsList)
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                Log.e(TAG, resources.getString(R.string.shipping_list_empty_error_message))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        _mainViewModel.getDriverData().observe(viewLifecycleOwner) { result ->
            try {
                driversList = result
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                Log.e(TAG, resources.getString(R.string.driver_list_empty_error_message))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        _mainViewModel.getAddressAssociatedDriversList().observe(viewLifecycleOwner) { result ->
            try {
                associatedDriversList = result
                _mainViewModel.subscribeAddressObserver()
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                Log.e(TAG, resources.getString(R.string.list_mapping_error_message))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        _mainViewModel._associatedAddressLiveData.observe(viewLifecycleOwner) { result ->
            try {
                associatedAddressList = result
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                Log.e(TAG, resources.getString(R.string.list_mapping_error_message))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}