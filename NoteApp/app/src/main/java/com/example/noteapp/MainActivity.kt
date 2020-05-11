package com.example.noteapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuery("%")
    }

    override fun onResume() {
        loadQuery("%")
        super.onResume()
    }

    fun loadQuery(title: String) {
        val dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.query(projections, "Title like ?", selectionArgs, "Title")
        listOfNotes.clear()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                listOfNotes.add(Note(id, title, description))
            } while (cursor.moveToNext())
        }

        val myNotesAdapter = MyNotesAdapter(this, listOfNotes)
        listViewNotes.adapter = myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val searchMenu = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchMenu.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(applicationContext, p0, Toast.LENGTH_SHORT).show()
                loadQuery("%$p0%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNote -> {
                val intent = Intent(this, AddNotes::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter(private val context: Context, private var listOfNotes: ArrayList<Note>) : BaseAdapter() {

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.ticket, null)
            val myNote = listOfNotes[p0]
            myView.txtTitle.text = myNote.noteName
            myView.txtContent.text = myNote.noteDes
            val dbManager = DbManager(context)
            val selectionArgs = arrayOf(myNote.noteId.toString())

            myView.btnDelete.setOnClickListener {
                dbManager.delete("ID = ?", selectionArgs)
                loadQuery("%")
            }

            myView.btnEdit.setOnClickListener {
                goToUpdate(myNote)
            }

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

    fun goToUpdate(note: Note) {
        val intent = Intent(this, AddNotes::class.java)
        intent.putExtra("ID", note.noteId)
        intent.putExtra("name", note.noteName)
        intent.putExtra("des", note.noteDes)
        startActivity(intent)
    }
}
