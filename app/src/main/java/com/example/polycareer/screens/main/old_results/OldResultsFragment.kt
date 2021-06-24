package com.example.polycareer.screens.main.old_results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.polycareer.R
import org.koin.android.ext.android.inject

class OldResultsFragment : Fragment() {
    private val viewModel: OldResultsViewModel by inject()

    private val stateObserver = Observer<OldResultsViewModel.OldResultsState> { state ->
        Log.d("old result repo", state.oldResults.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.getData()
        return inflater.inflate(R.layout.fragment_old_results, container, false)
    }
}