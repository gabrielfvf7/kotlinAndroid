package com.example.firebasedemoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var myAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        myAuth = FirebaseAuth.getInstance()
    }

    fun btnLoginEvent(view: View) {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()
        loginToFirebase(email, password)
    }

    private fun loginToFirebase(email: String, password: String) {
        myAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Login realizado", Toast.LENGTH_SHORT).show()
                        val user = myAuth!!.currentUser
                        if (user != null) {
                            myRef.child("Users").child(splitString(user.email.toString())).child("Request").setValue(user.uid)
                            loadMain(user)
                        }
                    } else {
                        Toast.makeText(applicationContext, "Erro ao logar", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = myAuth!!.currentUser
        if (currentUser != null) {
            loadMain(currentUser)
        }
    }

    private fun loadMain(currentUser: FirebaseUser) {
        val intent = Intent(this, Control::class.java)
        intent.putExtra("email", currentUser.email)
        intent.putExtra("uid", currentUser.uid)
        startActivity(intent)
    }

    fun splitString(str: String): String {
        val split = str.split("@")
        return split[0]
    }

}
