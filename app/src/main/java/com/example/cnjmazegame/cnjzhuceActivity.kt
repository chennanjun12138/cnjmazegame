package com.example.cnjmazegame

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class cnjzhuceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cnjzhuce)
        val name:EditText=findViewById(R.id.input_user_text2)
        val pass:EditText=findViewById(R.id.inmima2)
        val b1:Button=findViewById(R.id.zhucebtn)
        val dbHelper = cnjMyDatabaseHelper(this, "Usersum.db", 3)
        b1.setOnClickListener {
            val userAccount1 = name.text.toString()
            val userPassword1 = pass.text.toString()
            val db = dbHelper.writableDatabase
            var flag=0;
            val cursor = db.query("User", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    if(name==userAccount1)
                    {
                        flag=1
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
            if(TextUtils.isEmpty(userAccount1) || TextUtils.isEmpty(userPassword1))
            {
                Toast.makeText(applicationContext, "用户名或密码不能为空", Toast.LENGTH_LONG).show()
            }
            else if(flag!=1)
            {  val values1 = ContentValues().apply {
                put("name", userAccount1)
                put("pass", userPassword1)
                put("grade", 0)
                }
                db.insert("User", null, values1) // 插入第一条数据
                Toast.makeText(applicationContext, "注册成功", Toast.LENGTH_LONG).show()
                val intent2 = Intent(this, cnjMainActivity::class.java)
                startActivity(intent2)
            }
            else
            {
                Toast.makeText(applicationContext, "该用户名已注册", Toast.LENGTH_LONG).show()
            }

        }

    }
}