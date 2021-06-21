package com.example.polycareer.screens.main.quiz_results

import android.graphics.Color
import com.example.polycareer.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import java.util.ArrayList

class RadarChartAdapter(
    private val chart: RadarChart
) : Chart {

    override fun render(professions: Map<String, Int>) {
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.webLineWidth = 1.5f
        chart.webColor = R.color.black
        chart.webLineWidthInner = 1.5f
        chart.webColorInner = R.color.black
        chart.webAlpha = 100

        val entries1 = ArrayList<RadarEntry>()

        for (i in professions.values) {
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
            private val mActivities = professions.keys

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
}