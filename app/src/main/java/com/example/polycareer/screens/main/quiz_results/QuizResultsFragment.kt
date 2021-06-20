package com.example.polycareer.screens.main.quiz_results


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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

    private fun showChart(data1: Map<String, Int>) {
        Log.d(this::class.java.name, data1.toString())

        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.webLineWidth = 1.5f
        chart.webColor = R.color.black
        chart.webLineWidthInner = 1.5f
        chart.webColorInner = R.color.black
        chart.webAlpha = 100

        val entries1 = ArrayList<RadarEntry>()

        for (i in data1.values) {
            entries1.add(RadarEntry(i.toFloat()))
        }

        val set1 = RadarDataSet(entries1, "")
        set1.color = Color.rgb(44, 189, 99)
        set1.fillColor = Color.rgb(44, 189, 99)
        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.isDrawHighlightCircleEnabled = true
        set1.setDrawHighlightIndicators(false)

        val sets = ArrayList<IRadarDataSet>()
        sets.add(set1)

        val data = RadarData(sets)
        data.setValueTextSize(10f)
        data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)

        chart.data = data
        chart.invalidate()
        chart.animateXY(1400, 1400)

        val xAxis = chart.xAxis
        xAxis.textSize = 10f
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        xAxis.valueFormatter = object : ValueFormatter() {
            private val mActivities = data1.keys

            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return mActivities.elementAt(value.toInt() % mActivities.size)
            }
        }
        xAxis.textColor = Color.BLACK

        val yAxis = chart.yAxis
        yAxis.setLabelCount(10, false)
        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 90f
        yAxis.setDrawLabels(false)

        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        chart.legend.form = Legend.LegendForm.EMPTY
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