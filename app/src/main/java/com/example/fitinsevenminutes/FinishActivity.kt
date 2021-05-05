package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fitinsevenminutes.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFinishBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase() {
        val calender: Calendar = Calendar.getInstance()
        val dateTime = calender.time

        Log.i("date", "" + dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())   //get simple data format
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("DATE", "Date Added")
    }
}