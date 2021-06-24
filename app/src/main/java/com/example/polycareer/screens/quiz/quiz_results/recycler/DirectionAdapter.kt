package com.example.polycareer.screens.quiz.quiz_results.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.domain.model.Direction

class DirectionAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<DirectionItemViewHolder>() {
    private val directions: MutableList<Direction> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionItemViewHolder {
        return DirectionItemViewHolder(inflater.inflate(R.layout.simple_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: DirectionItemViewHolder, position: Int) {
        holder.bind(position + 1, directions[position])
    }

    override fun getItemCount() = directions.size

    fun showDirections(directions: List<Direction>) {
        this.directions.clear()
        this.directions.addAll(directions)
        notifyDataSetChanged()
    }
}