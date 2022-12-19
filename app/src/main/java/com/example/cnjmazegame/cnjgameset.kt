package com.example.cnjmazegame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class cnjgameset : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cnjgameset)
        val b1: Button =findViewById(R.id.play)
        val b2: Button =findViewById(R.id.back)
        val b3: Button =findViewById(R.id.paiming)
        val seek1: SeekBar =findViewById(R.id.seek_bar)
        val t1: TextView =findViewById(R.id.youxinandu)
        val t2:TextView=findViewById(R.id.yonghu)
        val t3:TextView=findViewById(R.id.jifen)
        val ig1:ImageView=findViewById(R.id.tubiao)
        val name = intent.getStringExtra("data")
        val dbHelper = cnjMyDatabaseHelper(this, "Usersum.db", 3)

        val db = dbHelper.writableDatabase
        val cursor = db.query("User", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name1 = cursor.getString(cursor.getColumnIndexOrThrow("name"))

                val grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade"))
                if(name1.equals(name))
                {
                    if(grade<10)
                    {   t2.setText("青铜"+name+"的积分：")
                        ig1.setImageResource(R.mipmap.qingtong)

                    }
                    else if(grade<30&&grade>=10)
                    { t2.setText("白银"+name+"的积分：")
                        ig1.setImageResource(R.mipmap.bangyin2)


                    }
                    else if(grade<50&&grade>=30)
                    {
                        ig1.setImageResource(R.mipmap.hangjing)
                        t2.setText("黄金"+name+"的积分：")
                    }
                    else if(grade<70&&grade>=50)
                    {
                        ig1.setImageResource(R.mipmap.bojing)
                        t2.setText("铂金"+name+"的积分：")

                    }
                    else if(grade<90&&grade>=70)
                    {
                        ig1.setImageResource(R.mipmap.zuanshi)
                        t2.setText("钻石"+name+"的积分：")

                    }
                    else if(grade>=90)
                    {
                        ig1.setImageResource(R.mipmap.zuiqiang)
                        t2.setText("王者"+name+"的积分：")
                    }
                    t3.setText(grade.toString())
                      break
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        initMediaPlayer()
        mediaPlayer.start()
        b1.setOnClickListener {
            mediaPlayer.stop()
            if (t1.getText().toString()=="简单")
            {
                val intent = Intent(this, cnjmazegame::class.java)
                intent.putExtra("name",name)
                intent.putExtra("jifen",t3.text.toString())
                startActivity(intent)
            }
            else if(t1.getText().toString()=="困难")
            {
                val intent = Intent(this, cnjmazehard::class.java)
                intent.putExtra("name",name)
                intent.putExtra("jifen",t3.text.toString())
                startActivity(intent)
            }
            else if(t1.getText().toString()=="噩梦")
            {
                val intent = Intent(this, cnjmazeemeng::class.java)
                intent.putExtra("name",name)
                intent.putExtra("jifen",t3.text.toString())
                startActivity(intent)
            }
        }
        b2.setOnClickListener {
            mediaPlayer.stop()
            val intent = Intent(this, cnjMainActivity::class.java)
            startActivity(intent)
        }
        b3.setOnClickListener {
            mediaPlayer.stop()
            val intent = Intent(this, cnjlist::class.java)
            startActivity(intent)
        }
        seek1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //设置图片透明度
                if (p1 <= 40) {
                    t1.text = "简单"
                } else if (p1 <= 80 && p1 > 40) {
                    t1.text = "困难"
                } else {
                    t1.text = "噩梦"
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
    private fun initMediaPlayer() {
        val assetManager = assets
        val fd = assetManager.openFd("first.wav")
        //background_music.mp3
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}