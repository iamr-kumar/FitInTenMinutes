package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import com.example.fitinsevenminutes.databinding.ActivityExcerciseBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExcerciseBinding;

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress = 0

    private var excerciseList: ArrayList<ExcerciseModel>? = null
    private var currentExcercisePosition = -1

    private var tts: TextToSpeech? = null

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

        tts = TextToSpeech(this, this)

        excerciseList = Constants.defaultExcerciseList()
        setupRestView()



    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if(excerciseTimer != null) {
            excerciseTimer!!.cancel()
            excerciseProgress = 0
        }
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()

    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            var result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language Error!", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, "Text to Speech Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        binding.tvNextExcercise.text = excerciseList!![currentExcercisePosition + 1].getName()
        restTimer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExcercisePosition++
                setupExcerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        binding.llRestView.visibility = View.VISIBLE
        binding.llExcericeView.visibility = View.GONE
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
                binding.tvExcerciseTimer.text = (30 - excerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExcercisePosition < excerciseList?.size!! - 1) {
                    setupRestView()
                }
                else {
                    Toast.makeText(
                            this@ExcerciseActivity,
                            "Congratulations, you have completed the 7 minutes workout routine",
                            Toast.LENGTH_SHORT
                    ).show()
                }
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

        speakOut(excerciseList!![currentExcercisePosition].getName())
        setExcerciseProgressBar()

        binding.ivImage.setImageResource(excerciseList!![currentExcercisePosition].getImage())
        binding.tvExcerciseName.text = excerciseList!![currentExcercisePosition].getName()

    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}