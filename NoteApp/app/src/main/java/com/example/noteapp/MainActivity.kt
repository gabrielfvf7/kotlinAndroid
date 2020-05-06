package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfNotes.add(Note(1, "Fazer exercicio", "todo dia às 18h por 1h"))
        listOfNotes.add(Note(2, "Tomar remedio", "assim que acordar"))
        listOfNotes.add(Note(3, "Lavar banheiro", "direito"))

        val myNotesAdapter = MyNotesAdapter(listOfNotes)
        listViewNotes.adapter = myNotesAdapter
    }

    inner class MyNotesAdapter(var listOfNotes: ArrayList<Note>) : BaseAdapter() {

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.ticket, null)
            val myNote = listOfNotes[p0]
            myView.txtTitle.text = myNote.noteName
            myView.txtContent.text = myNote.noteDes

            return myView
        }

        override fun getItem(p0: Int): Any {
            return listOfNotes[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listOfNotes.size
        }

    }
}
