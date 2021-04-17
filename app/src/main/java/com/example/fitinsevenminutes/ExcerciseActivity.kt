package com.example.fitinsevenminutes

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitinsevenminutes.databinding.ActivityExcerciseBinding
import com.example.fitinsevenminutes.databinding.CustomDialogBinding
import org.w3c.dom.Text
import java.lang.Exception
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

    private var player: MediaPlayer? = null

    private var adapter: ExcerciseStatusAdapter? = null

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
            customDialogForBackButton()
        }

        tts = TextToSpeech(this, this)

        excerciseList = Constants.defaultExcerciseList()
        setupRestView()

        setupExcerciseStatusRecyclerView()

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
        if(player != null) {
            player!!.stop()
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
                excerciseList!![currentExcercisePosition].setIsSelected(true)
                adapter!!.notifyDataSetChanged()
                setupExcerciseView()
            }
        }.start()
    }

    private fun setupRestView() {

        try {
            player = MediaPlayer.create(this, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
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
                    excerciseList!![currentExcercisePosition].setIsSelected(false)
                    excerciseList!![currentExcercisePosition].setIsCompleted(true)
                    adapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else {
                    finish()
                    val intent = Intent(this@ExcerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
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

    private fun setupExcerciseStatusRecyclerView() {
        adapter = ExcerciseStatusAdapter(excerciseList!!, this)
        binding.rvExcerciseStatus.adapter = adapter
        binding.rvExcerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun customDialogForBackButton() {
        val dialog = Dialog(this)
        val dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnConfirm.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}