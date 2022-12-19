package com.example.cnjmazegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import java.util.*
import kotlin.collections.ArrayList

class cnjlist : AppCompatActivity() {
    private val userList = ArrayList<cnjuser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cnjlist)
        val dbHelper = cnjMyDatabaseHelper(this, "Usersum.db", 3)
        val db=dbHelper.writableDatabase
        val cursor = db.query("User", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade"))
                val dengji =grade

                userList.add(cnjuser(dengji,name,grade))
            } while (cursor.moveToNext())
        }
        cursor.close()
        Collections.sort(
            userList,
            Comparator<cnjuser> { t1, t2 -> // TODO Auto-generated method stub
                if (t1.grade < t2.grade ) {
                    return@Comparator 1
                }
                if (t1.grade > t2.grade) {
                    -1
                } else 0
            })
        val adapter=cnjuserAdapter(this,R.layout.item,userList)
        val liview = findViewById<View>(R.id.listview) as ListView
        liview.adapter = adapter
    }
}