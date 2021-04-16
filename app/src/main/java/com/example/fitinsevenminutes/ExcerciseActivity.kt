package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.fitinsevenminutes.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcerciseBinding;

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExcerciseActivity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExcerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()

    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                setupExcerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setExcerciseProgressBar() {
        binding.progressBarExcerice.progress = excerciseProgress
        excerciseTimer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                excerciseProgress++
                binding.progressBarExcerice.progress = 30 - excerciseProgress
                binding.tvTimerExcercie.text = (30 - excerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExcerciseActivity, "Timer finished", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    private fun setupExcerciseView() {
        binding.llRestView.visibility = View.GONE
        binding.llExcericeView.visibility = View.VISIBLE
        if(excerciseTimer != null) {
            excerciseTimer!!.cancel()
            excerciseProgress = 0
        }
        setExcerciseProgressBar()
    }
}