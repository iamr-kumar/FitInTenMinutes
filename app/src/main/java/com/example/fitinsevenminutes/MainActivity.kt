package com.example.fitinsevenminutes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fitinsevenminutes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvStart.setOnClickListener {
            val intent = Intent(this, ExcerciseActivity::class.java)
            startActivity(intent);
        }

        binding.tvBmi.setOnClickListener {
            val intent = Intent(this, BmiActitvity::class.java)
            startActivity(intent)
        }
    }
}