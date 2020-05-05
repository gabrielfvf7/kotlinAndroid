package com.example.foodapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.food_ticket.view.*

class MainActivity : AppCompatActivity() {

    var adapter: FoodAdapter? = null
    var listOfFoods = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfFoods.add(Food("Espresso", "cafe gourmet", R.drawable.espresso))
        listOfFoods.add(Food("Coffe","odeio", R.drawable.coffee_pot))
        listOfFoods.add(Food("French Fries", "nao eh da frança", R.drawable.french_fries))
        listOfFoods.add(Food("Honey", "hmm melzin", R.drawable.honey))
        listOfFoods.add((Food("Sugar Cubes", "isso n é comida", R.drawable.sugar_cubes)))
        listOfFoods.add(Food("Strawberry Ice Cream", "queria :'(", R.drawable.strawberry_ice_cream))

        adapter = FoodAdapter(listOfFoods, this)

        gvListFood.adapter = adapter
    }

    class FoodAdapter(var listOfFood: ArrayList<Food>, context: Context) : BaseAdapter() {

        var context: Context? = context

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var food = listOfFood[p0]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var foodView = inflator.inflate(R.layout.food_ticket, null)
            foodView.imgFood.setImageResource(food.img!!)
            foodView.imgFood.setOnClickListener {
                var intent = Intent(context, FoodDetails::class.java)
                intent.putExtra("name", food.name!!)
                intent.putExtra("des", food.des!!)
                intent.putExtra("img", food.img!!)
                context!!.startActivity(intent)
            }
            foodView.txtFoodName.text = food.name
            return foodView
        }

        override fun getItem(p0: Int): Any {
            return listOfFood[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listOfFood.size
        }

    }
}
