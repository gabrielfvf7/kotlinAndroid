package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.tictactoe.R.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
    }

    fun btnClick(view: View) {
        val btnSelected = view as Button
        var cellID = 0
        when (btnSelected.id) {
            id.btn1 -> cellID = 1
            id.btn2 -> cellID = 2
            id.btn3 -> cellID = 3
            id.btn4 -> cellID = 4
            id.btn5 -> cellID = 5
            id.btn6 -> cellID = 6
            id.btn7 -> cellID = 7
            id.btn8 -> cellID = 8
            id.btn9 -> cellID = 9
        }

        playGame(cellID, btnSelected)
    }

    var activePlayer = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    fun playGame(cellId: Int, btnSelected: Button) {

        if (activePlayer == 1) {
            btnSelected.text = "X"
            btnSelected.setBackgroundResource(R.color.blue)
            player1.add(cellId)
            activePlayer = 2
        } else {
            btnSelected.text = "O"
            btnSelected.setBackgroundResource(R.color.red)
            player2.add(cellId)
            activePlayer = 1
        }

        btnSelected.isEnabled = false
        val result = checkWinner()
        if (result != -1){
        Toast.makeText(this, "Player $result won the game", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkWinner(): Int {
        var winner = -1

        //row 1
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winner = 1
            return(winner)
        } else if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winner = 2
            return(winner)
        }

        //row 2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner = 1
            return(winner)
        } else if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winner = 2
            return(winner)
        }

        //row 3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner = 1
            return(winner)
        } else if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winner = 2
            return(winner)
        }

        //column 1
        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner = 1
            return(winner)
        } else if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winner = 2
            return(winner)
        }

        //column 2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner = 1
            return(winner)
        } else if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winner = 2
            return(winner)
        }

        //column 3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner = 1
            return(winner)
        } else if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winner = 2
            return(winner)
        }

        return(winner)
    }
}
