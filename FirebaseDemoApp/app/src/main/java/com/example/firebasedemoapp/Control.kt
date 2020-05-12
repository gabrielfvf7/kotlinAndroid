package com.example.firebasedemoapp

import android.graphics.Color.blue
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_control.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etEmail
import java.lang.Exception

class Control : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    private var myEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        val b: Bundle? = intent.extras
        myEmail = b!!.getString("email")
        incommingCalls()
    }

    fun btnRequestEvent(view: View) {
        val userEmail: String = etEmail.text.toString()
        myRef.child("Users").child(splitString(userEmail)).child("Request").push().setValue(myEmail)
        playerOnline("${splitString(myEmail!!)}${splitString(userEmail)}")
        playerSymbol = "X"
    }

    fun btnAcceptEvent(view: View) {
        val userEmail: String = etEmail.text.toString()
        myRef.child("Users").child(splitString(userEmail)).child("Request").push().setValue(myEmail)
        playerOnline("${splitString(userEmail)}${splitString(myEmail!!)}")
        playerSymbol = "O"
    }

    var player1= java.util.ArrayList<Int>()
    var player2= java.util.ArrayList<Int>()
    var activePlayer=1
    var sessionID: String? = null
    var playerSymbol: String? = null

    fun playerOnline(sessionID: String) {
        this.sessionID = sessionID
        myRef.child("PlayerOnline").removeValue()
        myRef.child("PlayerOnline").child(sessionID)
            .addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    try {
                        player1.clear()
                        player2.clear()
                        val td = p0.value as HashMap<String, Any>
                        if (td != null) {
                            var value: String
                            for (key in td.keys) {
                                value = td[key] as String
                                if (value != myEmail) {
                                    activePlayer = if (playerSymbol == "X") 1 else 2
                                } else {
                                    activePlayer = if (playerSymbol == "X") 2 else 1
                                }
                                autoPlay(key.toInt())
                            }
                        }
                    } catch (ex: Exception) {

                    }
                }

            })
    }

    var number = 0
    fun incommingCalls() {
        myRef.child("Users").child(splitString(myEmail!!)).child("Request")
            .addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    try {
                        val td = p0.value as HashMap<String, Any>
                        if (td != null) {
                            val value: String
                            for (key in td.keys) {
                                value = td[key] as String
                                etEmail.setText(value)
                                val notifyMe = Notifications()
                                notifyMe.notify(applicationContext, "$value wants to play!", number)
                                number++
                                myRef.child("Users").child(splitString(myEmail!!)).child("Request").setValue(true)
                                break
                            }
                        }
                    } catch (ex: Exception) {

                    }
                }

            })
    }

    fun splitString(str: String): String {
        val split = str.split("@")
        return split[0]
    }

    fun buClick(view: View) {
        val btnSelected = view as android.widget.Button
        var cellID = 0
        when (btnSelected.id) {
            R.id.bu1 -> cellID = 1
            R.id.bu2 -> cellID = 2
            R.id.bu3 -> cellID = 3
            R.id.bu4 -> cellID = 4
            R.id.bu5 -> cellID = 5
            R.id.bu6 -> cellID = 6
            R.id.bu7 -> cellID = 7
            R.id.bu8 -> cellID = 8
            R.id.bu9 -> cellID = 9
        }

        myRef.child("PlayerOnline").child(sessionID!!).child(cellID.toString()).setValue(myEmail)
    }

    fun playGame(cellID:Int,buSelected:Button){

        if(activePlayer==1){
            buSelected.text="X"
            buSelected.setBackgroundResource(R.color.colorAccent)
            player1.add(cellID)
            activePlayer=2

        }else{
            buSelected.text="O"
            buSelected.setBackgroundResource(R.color.colorPrimary)
            player2.add(cellID)
            activePlayer=1
        }

        buSelected.isEnabled=false
        checkWiner()
    }

    fun  checkWiner(){
        var winer=-1

        // row 1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3)){
            winer=1
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)){
            winer=2
        }


        // row 2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)){
            winer=1
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)){
            winer=2
        }




        // row 3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)){
            winer=1
        }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)){
            winer=2
        }



        // col 1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7)){
            winer=1
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7)){
            winer=2
        }



        // col 2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)){
            winer=1
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)){
            winer=2
        }


        // col 3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)){
            winer=1
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)){
            winer=2
        }


        if( winer != -1){

            if (winer==1){
                android.widget.Toast.makeText(this," Player 1  win the game", android.widget.Toast.LENGTH_LONG).show()
            }else{
                android.widget.Toast.makeText(this," Player 2  win the game", android.widget.Toast.LENGTH_LONG).show()

            }

        }

    }

    fun autoPlay(cellID:Int) {

        val buSelect:Button? = when(cellID){
            1 -> bu1
            2 -> bu2
            3 -> bu3
            4 -> bu4
            5 -> bu5
            6 -> bu6
            7 -> bu7
            8 -> bu8
            9 -> bu9
            else -> {
                bu1
            }
        }
        playGame(cellID, buSelect!!)
    }

}
