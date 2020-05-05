package com.hussein.startup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import  kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_ticket.view.*
import android.app.Activity
import android.content.Intent
import android.widget.AdapterView.OnItemClickListener



class MainActivity : AppCompatActivity() {

    var listOfAnimals = ArrayList<Animal>()
    var adapter: AnimalsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfAnimals.add(Animal("Baboon", "macaco louco", R.drawable.baboon, false))
        listOfAnimals.add(Animal("Bulldog", "cachorro", R.drawable.bulldog, false))
        listOfAnimals.add(Animal("Panda", "incel", R.drawable.panda, false))
        listOfAnimals.add(Animal("Swallow Bird", "passarin", R.drawable.swallow_bird, false))
        listOfAnimals.add(Animal("Zebra", "é preta ou branca", R.drawable.zebra, false))
        listOfAnimals.add(Animal("White Tiger", "branco", R.drawable.white_tiger, true))

        adapter = AnimalsAdapter(this, listOfAnimals)
        tvListView.adapter = adapter
    }

    fun delete (index: Int) {
        listOfAnimals.removeAt(index)
        adapter!!.notifyDataSetChanged()
    }

    fun add (index: Int) {
        listOfAnimals.add(index, listOfAnimals[index])
        adapter!!.notifyDataSetChanged()
    }

    inner class AnimalsAdapter: BaseAdapter {

        var listOfAnimals = ArrayList<Animal>()
        var context: Context? = null

        constructor(context: Context, listOfAnimals: ArrayList<Animal>): super() {
            this.listOfAnimals = listOfAnimals
            this.context = context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val animal = listOfAnimals[p0]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE)  as LayoutInflater
            var myView = if (animal.isKiller) {
                inflator.inflate(R.layout.animal_killer_ticket, null)
            } else {
                inflator.inflate(R.layout.animal_ticket, null)
            }
            myView.imgViewAnimal.setOnClickListener {
//                add(p0)
                val intent = Intent(context, AnimalInfo::class.java)
                intent.putExtra("name", animal.name)
                intent.putExtra("des", animal.des)
                intent.putExtra("img", animal.img)
                context!!.startActivity(intent)
            }
            myView.txtViewName.text = animal.name
            myView.txtViewDescr.text = animal.des
            myView.imgViewAnimal.setImageResource(animal.img!!)
            return myView
        }

        override fun getItem(p0: Int): Any {
            return listOfAnimals[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listOfAnimals.size
        }

    }
}
