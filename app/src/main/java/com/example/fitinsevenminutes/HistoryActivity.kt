package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitinsevenminutes.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExcerciseActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }

        binding.toolbarExcerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllDates()
    }

    private fun getAllDates() {
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDateList = dbHandler.getAllCompleteDateList()

        if (allCompletedDateList.size > 0) {
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE

            binding.rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdaptar = HistoryAdaptar(this, allCompletedDateList)
            binding.rvHistory.adapter = historyAdaptar

        } else {
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }

    }

}