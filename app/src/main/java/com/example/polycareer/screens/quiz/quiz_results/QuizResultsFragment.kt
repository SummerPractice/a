package com.example.polycareer.screens.quiz.quiz_results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.domain.model.Direction
import com.example.polycareer.domain.model.Profession
import com.example.polycareer.screens.quiz.quiz_results.charts.Chart
import com.example.polycareer.screens.quiz.quiz_results.charts.PieChartAdapter
import com.example.polycareer.screens.quiz.quiz_results.recycler.DirectionAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizResultsFragment : Fragment(), View.OnClickListener {
    private lateinit var chart: Chart
    private lateinit var recommendedDirections: DirectionAdapter

    private lateinit var correctScreen: View
    private lateinit var errorScreen: View
    private lateinit var loader: ProgressBar
    private lateinit var toMenuBtn: AppCompatButton

    private val viewModel: QuizResultsViewModel by viewModel()

    private val stateObserver = Observer<QuizResultsViewModel.QuizResultState> { state ->
        if (state.error.isEmpty() && !state.isLoad) {
            loader.isVisible = false
            correctScreen.isVisible = true
            showChart(state.professions)
            showDirections(state.directions)
        }
        loader.isVisible = state.isLoad
        errorScreen.isVisible = state.error.isNotEmpty()
    }

    private fun showDirections(directions: List<Direction>) {
        Log.d(this::class.java.name, directions.toString())
        recommendedDirections.showDirections(directions)
    }

    private fun showChart(data1: List<Profession>) {
        Log.d(this::class.java.name, data1.toString())
        chart.render(data1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__quiz_results, container, false)

        correctScreen = rootView.findViewById(R.id.result_screen)
        errorScreen = rootView.findViewById(R.id.error_screen)
        setReloadButton()
        loader = rootView.findViewById(R.id.fragment__quiz__quiz_results_progress_bar)
        toMenuBtn = rootView.findViewById(R.id.fragment__main__quiz_results__main_btn)

        chart = PieChartAdapter(
                requireContext(),
                correctScreen.findViewById(R.id.fragment__main__quiz_results__graph_rc),
                correctScreen.findViewById(R.id.fragment__main__quiz_results__legend_lv),
                viewModel.isFirstRender)

        createRecyclerView()

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.tryNumber = arguments?.getLong(App.TRY_NUMBER) ?: return null

        return rootView
    }

    private fun setReloadButton() {
        val button = errorScreen.findViewById<Button>(R.id.screen__result_error_btn)
        button.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun createRecyclerView() {
        val directionsView =
            correctScreen.findViewById<RecyclerView>(R.id.fragment__main__quiz_results__rv)
        directionsView.layoutManager = LinearLayoutManager(context)
        this.recommendedDirections = DirectionAdapter(layoutInflater)
        directionsView.adapter = this.recommendedDirections
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toMenuBtn.setOnClickListener(this)
        viewModel.loadData()
    }

    override fun onClick(v: View?) {
        val navController = NavHostFragment.findNavController(this)
        navController.popBackStack(R.id.mainFragment, false)
    }
}