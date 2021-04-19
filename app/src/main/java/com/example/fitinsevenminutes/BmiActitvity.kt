package com.example.fitinsevenminutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.fitinsevenminutes.databinding.ActivityBmiActitvityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActitvity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiActitvityBinding

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiActitvityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"

        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnCalculateUnits.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if (!validateMetricUnits()) {
                    Toast.makeText(this, "Please enter valid information", Toast.LENGTH_SHORT).show()
                } else {
                    val heightValue: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()
                    val bmi: Float = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                }
            }
            else {
                if (!validateUsUnits()) {
                    Toast.makeText(this, "Please enter valid information", Toast.LENGTH_SHORT).show()
                } else {
                    val inchValue: String = binding.etUsUnitHeightInch.text.toString()
                    val feetValue: String = binding.etUsUnitHeightFeet.text.toString()
                    val weightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()
                    val heightValue = inchValue.toFloat() + feetValue.toFloat() * 12
                    val bmi: Float = 703 * (weightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                }
            }

        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            }
            else {
                makeVisibleUscUnitsView()
            }
        }

    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        binding.tilUsUnitWeight.visibility = View.GONE
        binding.llUsUnitsHeight.visibility = View.GONE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()

        binding.llBmiResult.visibility = View.GONE
    }


    private fun makeVisibleUscUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUnitHeight.visibility = View.GONE

        binding.etUsUnitWeight.text!!.clear()
        binding.etUsUnitHeightInch.text!!.clear()
        binding.etUsUnitHeightFeet.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llUsUnitsHeight.visibility = View.VISIBLE

        binding.llBmiResult.visibility = View.GONE
    }


    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits():Boolean {
        var isValid =  true
        if(binding.etUsUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }
        else if(binding.etUsUnitHeightFeet.text.toString().isEmpty()) {
            isValid = false
        }
        else if(binding.etUsUnitHeightInch.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llBmiResult.visibility = View.VISIBLE

//        binding.tvBmi.visibility = View.VISIBLE
//        binding.tvBmiValue.visibility = View.VISIBLE
//        binding.tvBmiType.visibility = View.VISIBLE
//        binding.tvBmiDesc.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.tvBmiValue.text = bmiValue
        binding.tvBmiType.text = bmiLabel
        binding.tvBmiDesc.text = bmiDescription

    }



}