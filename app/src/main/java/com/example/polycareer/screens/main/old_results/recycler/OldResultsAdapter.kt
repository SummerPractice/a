package com.example.polycareer.screens.main.old_results.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.domain.model.UserResult

class OldResultsAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<OldResultsViewHolder>() {
    val results: MutableList<UserResult> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldResultsViewHolder {
        return OldResultsViewHolder(inflater.inflate(R.layout.simple_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: OldResultsViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount() = results.count()
}