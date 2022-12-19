package com.example.cnjmazegame

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
class cnjMyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version){
    private val createStudent = "create table User (" +
            "name text," +
            "pass text," +
            "grade text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createStudent)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists Student")
        onCreate(db)
    }
}
