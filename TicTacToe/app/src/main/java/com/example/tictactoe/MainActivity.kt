package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tictactoe.R.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

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
    var player1Wins = 0
    var player2Wins = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    fun playGame(cellId: Int, btnSelected: Button) {

        if (activePlayer == 1) {
            btnSelected.text = "X"
            btnSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            player1.add(cellId)
            activePlayer = 2
            autoPlay()
        } else {
            btnSelected.text = "O"
            btnSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            player2.add(cellId)
            activePlayer = 1
        }

        btnSelected.isEnabled = false
        val result = checkWinner()
        if (result != -1){
        Toast.makeText(this, "Player $result won the game", Toast.LENGTH_SHORT).show()
            if (result == 1) {
                player1Wins++
            } else { player2Wins++ }
            restartGame()
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

    fun autoPlay() {
        val emptyCells = ArrayList<Int>()
        for (cellId in 1..9) {
            if(!(player1.contains(cellId) || player2.contains(cellId))) {
                emptyCells.add(cellId)
            }
        }

        if (emptyCells.size == 0) {
            restartGame()
        }

        val r = java.util.Random()
        val randIndex = r.nextInt(emptyCells.size-0) + 0
        val cellId = emptyCells[randIndex]

        val btnSelected: Button?
        btnSelected = when(cellId) {
            1 -> btn1
            2 -> btn2
            3 -> btn3
            4 -> btn4
            5 -> btn5
            6 -> btn6
            7 -> btn7
            8 -> btn8
            9 -> btn9
            else -> { btn1 }
        }
        playGame(cellId, btnSelected)
    }

    fun restartGame() {
        activePlayer = 1
        player1.clear()
        player2.clear()

        for(cellId in 1..9) {
            var btnSelected: Button?
            btnSelected = when(cellId) {
                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> { btn1 }
            }
            btnSelected.text = ""
            btnSelected.isEnabled = true
            btnSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }

        Toast.makeText(this, "Player 1 won $player1Wins times and player 2 won $player2Wins times", Toast.LENGTH_SHORT).show()
    }
}
