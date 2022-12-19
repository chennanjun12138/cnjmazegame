package com.example.cnjmazegame

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.image

class cnjuserAdapter (activity: Activity, val resourceId: Int, data: List<cnjuser>) :
    ArrayAdapter<cnjuser>(activity, resourceId, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup):
            View {
        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)
        val username: TextView = view.findViewById(R.id.uername)
        val uergrade: TextView = view.findViewById(R.id.fenshu)
        val pai: TextView = view.findViewById(R.id.level)
        val uesr = getItem(position)
        var cnt=position+1
        if (uesr != null) {
            username.text=uesr.name
            uergrade.text =uesr.grade.toString()
            pai.text=cnt.toString()

        }
        return view
    }
}