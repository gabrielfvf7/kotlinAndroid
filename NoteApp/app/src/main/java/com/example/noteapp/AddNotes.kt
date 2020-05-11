package com.example.noteapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class AddNotes : AppCompatActivity() {

    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt("ID", 0)
        if (id != 0) {
            edtTxtTitle.setText(bundle.getString("name"))
            edtTxtDes.setText(bundle.getString("des"))
        }
    }

    fun btnFinish(view: View) {
        val dbManager = DbManager(this)
        val values = ContentValues()
        values.put("Title", edtTxtTitle.text.toString())
        values.put("Description", edtTxtDes.text.toString())

        if (id == 0) {
            val iD = dbManager.insert(values)
            if (iD > 0) {
                Toast.makeText(this, "Nota foi adicionada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao adicionar", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            val selectionArgs = arrayOf(id.toString())
            val iD = dbManager.update(values, "ID = ?", selectionArgs)
            if (iD > 0) {
                Toast.makeText(this, "Nota atualizada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
