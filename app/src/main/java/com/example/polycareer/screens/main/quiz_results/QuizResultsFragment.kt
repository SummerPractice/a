package com.example.polycareer.screens.main.quiz_results

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.screens.main.grades.GradesViewModel
import com.github.mikephil.charting.charts.RadarChart
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizResultsFragment : Fragment() {
    private lateinit var chart: RadarChart
    private lateinit var recommendedDirections: RecyclerView
    private lateinit var head: TextView
    private lateinit var res: TextView
    private lateinit var gl: TextView
    private lateinit var error: TextView

    private val viewModel: QuizResultsViewModel by viewModel()

    private val stateObserver = Observer<QuizResultsViewModel.QuizResultState> { state ->
        if (state.error.isNotEmpty()) showError(state.error)
        else {
            showChart(state.professions)
            showDirections(state.directions)
        }
    }

    private fun showDirections(directions: Map<String, Int>) {
        Log.d(this::class.java.name, directions.toString())
    }

    private fun showError(message: String) {
        Log.d(this::class.java.name, message)
    }

    private fun showChart(data: Map<String, Int>) {
        Log.d(this::class.java.name, data.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__quiz_results, container, false)

        chart = rootView.findViewById(R.id.fragment__main__quiz_results__graph_rc)
        recommendedDirections = rootView.findViewById(R.id.fragment__main__quiz_results__rv)
        head = rootView.findViewById(R.id.fragment__main__quiz_results__head_tv)
        res = rootView.findViewById(R.id.fragment__main__quiz_results__res_tv)
        gl = rootView.findViewById(R.id.fragment__main__quiz_results__gl_tv)
        error = rootView.findViewById(R.id.fragment__main__quiz_results__error_tv)

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()
    }
}