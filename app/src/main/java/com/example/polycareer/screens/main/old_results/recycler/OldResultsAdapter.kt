package com.example.polycareer.screens.main.old_results.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.domain.model.UserResultInfo

class OldResultsAdapter(private val inflater: LayoutInflater, private val listener: OnResultItemClickListener) : RecyclerView.Adapter<OldResultsViewHolder>() {
    val resultsInfo: MutableList<UserResultInfo> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldResultsViewHolder {
        return OldResultsViewHolder(inflater.inflate(R.layout.simple_rv_item, parent, false), listener)
    }

    override fun onBindViewHolder(holder: OldResultsViewHolder, position: Int) {
        holder.bind(resultsInfo[position])
    }

    override fun getItemCount() = resultsInfo.count()

    interface OnResultItemClickListener {
        fun onItemClick(tryNumber: Long)
    }
}