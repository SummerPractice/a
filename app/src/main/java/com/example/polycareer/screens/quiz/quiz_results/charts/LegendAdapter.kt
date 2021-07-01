package com.example.polycareer.screens.quiz.quiz_results.charts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import com.example.polycareer.R
import com.example.polycareer.domain.model.LegendLabel


class LegendAdapter(
    private val context: Context,
    private val objects: List<LegendLabel>
): BaseAdapter() {
    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = layoutInflater.inflate(R.layout.fragment__main__chart_legend, parent, false)

        val l: LegendLabel = objects[position]

        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.circle)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, l.color)

        view?.findViewById<AppCompatImageView>(R.id.legend_circle)?.background = wrappedDrawable

        view?.findViewById<AppCompatTextView>(R.id.legend_text)?.text = l.text

        return view
    }
}