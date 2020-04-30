package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberClick(view: View) {
        if (isNewOp) {
            edtShow.setText("")
        }
        isNewOp = false
        val btnClicked = view as Button
        var btnClickValue: String = edtShow.text.toString()
        when (btnClicked) {
            btn0 -> {
                btnClickValue += "0"
            }
            btn1 -> {
                btnClickValue += "1"
            }
            btn2 -> {
                btnClickValue += "2"
            }
            btn3 -> {
                btnClickValue += "3"
            }
            btn4 -> {
                btnClickValue += "4"
            }
            btn5 -> {
                btnClickValue += "5"
            }
            btn6 -> {
                btnClickValue += "6"
            }
            btn7 -> {
                btnClickValue += "7"
            }
            btn8 -> {
                btnClickValue += "8"
            }
            btn9 -> {
                btnClickValue += "9"
            }
            btnDot -> {
                btnClickValue += "."
            }
            btnPlusMinus -> {
                btnClickValue = "-$btnClickValue"
            }
        }
        edtShow.setText(btnClickValue)
    }

    private var op = "*"
    private var oldNumber = ""
    private var isNewOp = true
    fun opClick(view: View) {
        when (view as Button) {
            btnX -> {
                op = "*"
            }
            btnPlus -> {
                op = "+"
            }
            btnMinus -> {
                op = "-"
            }
            btnDivide -> {
                op = "/"
            }
        }
        oldNumber = edtShow.text.toString()
        isNewOp = true
    }

    fun equalClick(view: View) {
        val newNumber = edtShow.text.toString()
        var finalNumber: Double? = null
        when (op) {
            "*" -> {
                finalNumber = oldNumber.toDouble() * newNumber.toDouble()
            }
            "/" -> {
                finalNumber = oldNumber.toDouble() / newNumber.toDouble()
            }
            "+" -> {
                finalNumber = oldNumber.toDouble() + newNumber.toDouble()
            }
            "-" -> {
                finalNumber = oldNumber.toDouble() - newNumber.toDouble()
            }
        }
        edtShow.setText(finalNumber.toString())
        isNewOp = true
    }

    fun percentClick(view: View) {
        val number = edtShow.text.toString().toDouble() / 100
        edtShow.setText(number.toString())
    }

    fun btnClean(view: View) {
        edtShow.setText("0")
        isNewOp = true
    }

}
