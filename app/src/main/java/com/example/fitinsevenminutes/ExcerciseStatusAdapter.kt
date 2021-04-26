package com.example.fitinsevenminutes

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitinsevenminutes.databinding.ExcerciseStatusBinding

class ExcerciseStatusAdapter(val items: ArrayList<ExcerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExcerciseStatusAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ExcerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExcerciseStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvItem.text = this.getId().toString()
                if(this.getIsSelected() == true) {
                    binding.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_accent_border)
                    binding.tvItem.setTextColor(Color.parseColor("#212121"))
                }
                else if(this.getIsCompleted() == true) {
                    binding.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
                    binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
                }
                else {
                    binding.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
                    binding.tvItem.setTextColor(Color.parseColor("#212121"))
                }
            }
        }
    }

}