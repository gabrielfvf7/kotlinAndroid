package com.example.findmyage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetAge.setOnClickListener {
            btnClick()
        }
    }

    fun btnClick() {
        val yob: Int = Integer.parseInt(txtInput.text.toString())
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val userAge = currentYear - yob
        txtResult.text = "Your age is $userAge Years"
    }
}
