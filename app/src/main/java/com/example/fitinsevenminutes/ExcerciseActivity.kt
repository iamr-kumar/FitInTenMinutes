package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitinsevenminutes.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExcerciseActivity)
        val actionBar = supportActionBar;
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExcerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}