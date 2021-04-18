package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitinsevenminutes.databinding.ActivityBmiActitvityBinding

class BmiActitvity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiActitvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiActitvityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}