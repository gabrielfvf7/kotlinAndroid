package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager(val context: Context)  {
    val dbName = "MyNotes"
    val dbTable = "Notes"
    val colID = "ID"
    val colTitle = "Title"
    val colDes = "Description"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ($colID INTEGER PRIMARY KEY, $colTitle TEXT, $colDes TEXT);"
    var db = DataBaseHelperNotes()
    var sqlDB: SQLiteDatabase = db.writableDatabase

    inner class DataBaseHelperNotes: SQLiteOpenHelper(context, dbName, null, dbVersion ) {
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "Database was createad", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }

    }

    fun insert(values: ContentValues): Long {
        return sqlDB.insert(dbTable, "", values);
    }

    fun query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String): Cursor {
        val query = SQLiteQueryBuilder()
        query.tables = dbTable
        return query.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return sqlDB.delete(dbTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return sqlDB.update(dbTable, values, selection, selectionArgs)
    }

}