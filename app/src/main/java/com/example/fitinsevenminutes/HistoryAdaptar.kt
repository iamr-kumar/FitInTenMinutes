package com.example.fitinsevenminutes

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitinsevenminutes.databinding.ItemHistoryRowBinding

class HistoryAdaptar(val context: Context, val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdaptar.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHistoryRowBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder) {
           with(items[position]) {
               binding.tvPosition.text = (position + 1).toString()
               binding.tvItem.text = this
               if(position % 2 == 0) {
                   binding.llHistoryItenMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
               }
               else {
                   binding.llHistoryItenMain.setBackgroundColor((Color.parseColor("#FFFFFF")))
               }
           }
       }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}