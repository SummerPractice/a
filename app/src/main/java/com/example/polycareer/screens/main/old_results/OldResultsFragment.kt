package com.example.polycareer.screens.main.old_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.screens.main.old_results.recycler.OldResultsAdapter
import org.koin.android.ext.android.inject

class OldResultsFragment : Fragment() {
    private val viewModel: OldResultsViewModel by inject()
    private lateinit var adapter: OldResultsAdapter

    private val stateObserver = Observer<OldResultsViewModel.OldResultsState> { state ->
        adapter.resultsInfo.addAll(state.resultsInfo)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_old_results, container, false)

        val recycler = rootView.findViewById<RecyclerView>(R.id.fragment__main__old__result__rv)
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = OldResultsAdapter(inflater)
        recycler.adapter = adapter

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.getData()
        return rootView
    }
}