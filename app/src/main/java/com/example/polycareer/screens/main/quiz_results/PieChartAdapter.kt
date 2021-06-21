package com.example.polycareer.screens.main.quiz_results

import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.*

class PieChartAdapter(
    private val chart: PieChart
) : Chart {
    override fun render(professions: Map<String, Int>) {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 40f, 0f)

        chart.dragDecelerationFrictionCoef = 1f

        chart.isDrawHoleEnabled = true

        chart.setTransparentCircleColor(Color.GRAY)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 61f
        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)

        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        chart.animateY(1100, Easing.EaseInOutQuad)

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 1f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        chart.setEntryLabelColor(Color.rgb(112, 112, 112))
        chart.setEntryLabelTextSize(12f)
        chart.setEntryLabelColor(Color.alpha(1))
        setData(professions)
        chart.invalidate()
    }

    private fun setData(data1: Map<String, Int>) {
        val entries = ArrayList<PieEntry>()

        for (i in data1.entries) {
            entries.add(
                PieEntry(
                    (i).value.toFloat(),
                    (i).key
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = pastelColors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.rgb(112, 112, 112))
        chart.data = data

        chart.highlightValues(null)
    }

    private val pastelColors = listOf(
        Color.rgb(242, 158, 76), Color.rgb(241, 196, 83), Color.rgb(239, 234, 90),
        Color.rgb(185, 231, 105), Color.rgb(22, 219, 147), Color.rgb(136, 212, 171),
        Color.rgb(131, 227, 119), Color.rgb(242, 232, 207)
    )

}