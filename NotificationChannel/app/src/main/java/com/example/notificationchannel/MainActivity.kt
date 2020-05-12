package com.example.notificationchannel

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    var notificationHelper: NotificationHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationHelper = NotificationHelper(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickCh1(view: View) {
        notificationHelper!!.notify(1, notificationHelper!!.getNotify1("Teste", "oioioioi"))
    }

    fun onClickCh2(view: View) {}
    fun onClickCh3(view: View) {}
}
