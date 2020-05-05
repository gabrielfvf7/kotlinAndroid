package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_food_details.*
import kotlinx.android.synthetic.main.food_ticket.*

class FoodDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        var bundle = intent.extras
        imgViewFood.setImageResource(bundle!!.getInt("img"))
        txtViewName.text = bundle.getString("name")
        txtViewDetails.text = bundle.getString("des")
    }
}
