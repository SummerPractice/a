package com.example.polycareer.screens.quiz.quiz_results.charts

import android.content.Context
import android.graphics.Color
import android.widget.ListView
import com.example.polycareer.domain.model.LegendLabel
import com.example.polycareer.domain.model.Profession
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.*


class PieChartAdapter(
    private val context: Context,
    private val chart: PieChart,
    private val legend: ListView,
    private val isFirstRender: Boolean
) : Chart {
    private val pastelColors = listOf(
        Color.rgb(0, 185, 197),
        Color.rgb(0, 174, 132),
        Color.rgb(240, 86, 35)
    )

    override fun render(professions: List<Profession>) {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(20f, 0f, 20f, 0f)

        chart.dragDecelerationFrictionCoef = 1f

        chart.isDrawHoleEnabled = true

        chart.setHoleColor(Color.TRANSPARENT)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 45f
        chart.transparentCircleRadius = 45f

        chart.setDrawCenterText(true)

        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        if (isFirstRender) {
            chart.animateY(1100, Easing.EaseInOutQuad)
        }

        chart.legend.isEnabled = false

        chart.setEntryLabelColor(Color.rgb(112, 112, 112))
        chart.setEntryLabelColor(Color.alpha(1))
        chart.invalidate()
        setData(professions)

        val legendAdapter = LegendAdapter(context, chart.legend.entries
            .filter { entry ->  entry.label.isNotEmpty()}
            .map {
                legendEntry -> LegendLabel(color = legendEntry.formColor, text = legendEntry.label)
        })
        legend.adapter = legendAdapter
    }

    private fun setData(data1: List<Profession>) {
        val entries = ArrayList<PieEntry>()

        for (profession in data1) {
            entries.add(
                PieEntry(
                    profession.percent.toFloat(),
                    profession.title
                )
            )
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = pastelColors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        chart.highlightValues(null)

    }
}