package com.example.polycareer.screens.main.old_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.domain.model.UserResultInfo
import com.example.polycareer.screens.main.old_results.recycler.OldResultsAdapter
import org.koin.android.ext.android.inject

class OldResultsFragment : Fragment(), OldResultsAdapter.OnResultItemClickListener {
    private val viewModel: OldResultsViewModel by inject()
    private lateinit var adapter: OldResultsAdapter
    private lateinit var noItemsView: View
    private lateinit var withItemsView: View


    private val stateObserver = Observer<OldResultsViewModel.OldResultsState> { state ->
        when {
            state.error -> showText(R.string.error, R.drawable.ic_error)
            state.resultsInfo.isNotEmpty() -> showItems(state.resultsInfo)
            else -> showText(R.string.no_items, R.drawable.ic_no_items)
        }
    }

    private fun showText(@StringRes text: Int, @DrawableRes iconRes: Int) {
        withItemsView.isVisible = false
        noItemsView.isVisible = true

        val icon = noItemsView.findViewById<ImageView>(R.id.screen_without_items__icon)
        icon.setImageResource(iconRes)

        val textView = noItemsView.findViewById<TextView>(R.id.screen_without_items__text)
        textView.setText(text)

        val button = noItemsView.findViewById<Button>(R.id.screen_without_items_)
        button.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.popBackStack(R.id.mainFragment, false)
        }
    }

    private fun showItems(items: List<UserResultInfo>) {
        adapter.resultsInfo.clear()
        adapter.resultsInfo.addAll(items)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_old_results, container, false)

        noItemsView = rootView.findViewById(R.id.screen_without_items)
        withItemsView = rootView.findViewById(R.id.screen_with_items)

        val recycler = withItemsView.findViewById<RecyclerView>(R.id.fragment__main__old__result__rv)
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = OldResultsAdapter(inflater, this)
        recycler.adapter = adapter

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.getData()

        return rootView
    }

    override fun onItemClick(tryNumber: Long) {
        val bundle = Bundle()
        bundle.putLong(App.TRY_NUMBER, tryNumber)
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_oldResultsFragment2_to_quizResultsFragment, bundle)
    }
}