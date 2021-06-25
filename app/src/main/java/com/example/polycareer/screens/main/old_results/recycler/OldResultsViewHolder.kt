package com.example.polycareer.screens.main.old_results.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.domain.model.UserResultInfo

class OldResultsViewHolder(itemView: View, private val listener : OldResultsAdapter.OnResultItemClickListener) :RecyclerView.ViewHolder(itemView) {
    private var number: TextView = itemView.findViewById(R.id.dir_item__tv_num1)
    private val title: TextView = itemView.findViewById(R.id.dir_item__tv_dest)
    private var dateOfTry: TextView = itemView.findViewById(R.id.dir_item__tv_descr)

    fun bind(result: UserResultInfo) {
        number.text = result.tryNumber.toString()
        title.text = itemView.context.getString(R.string.try_title, result.tryNumber)
        dateOfTry.text = result.date
        itemView.setOnClickListener { listener.onItemClick(result.tryNumber) }
    }
}