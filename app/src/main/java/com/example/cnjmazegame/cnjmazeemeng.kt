package com.example.cnjmazegame

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class cnjmazeemeng : AppCompatActivity(){

    public var widths = 0
    public var gamewideh = 0

    private val mediaPlayer2 = MediaPlayer()
    private val mediaPlayer3 = MediaPlayer()
    private val mediaPlayer4 = MediaPlayer()
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cnjmazegame)
        val metric = DisplayMetrics() //获取屏幕信息
        windowManager.defaultDisplay.getMetrics(metric) //获取屏幕信息
        gamewideh = metric.widthPixels
        val dbHelper = cnjMyDatabaseHelper(this, "Usersum.db", 3)
        val name = intent.getStringExtra("name")
        val jifeng = intent.getStringExtra("jifen")
        val frameLayout = findViewById<View>(R.id.frameLayout) as FrameLayout
        var myView = cnjemengview(this)
        myView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        frameLayout.addView(myView)
        val b1: Button =findViewById(R.id.top)
        val b2: Button =findViewById(R.id.down)
        val b3: Button =findViewById(R.id.left)
        val b4: Button =findViewById(R.id.right)
        val b5: Button =findViewById(R.id.btn_pause)
        val b6: Button =findViewById(R.id.btn_play)
        val b7:Button=findViewById(R.id.help)
        var f7=0;
        b7.setOnClickListener {
            if(f7==0)
            {
                myView.helpway()
                f7=1;
            }
            else
            {
                f7=0;
                myView.recver()
            }
        }
        val view_timer: Chronometer =findViewById(R.id.view_timer)

        view_timer.base = SystemClock.elapsedRealtime() +600000
        view_timer.start()
        view_timer.setOnChronometerTickListener(Chronometer.OnChronometerTickListener {
            view_timer.setText(view_timer.getText().toString().substring(1))
            if (SystemClock.elapsedRealtime() - view_timer.getBase() >= 0) view_timer.stop()
        })

        Timer().schedule(object : TimerTask() {
            override fun run() {
                //if (SystemClock.elapsedRealtime()-view_timer.getBase()>=0)view_timer.stop();
                Looper.prepare()
                val builder = AlertDialog.Builder(this@cnjmazeemeng)
                builder.setTitle("游戏结束")
                builder.setMessage("遗憾，闯关失败!")
                builder.setPositiveButton("确定"){
                        DialogInterface,which->
                    val intent2 = Intent(this@cnjmazeemeng, cnjgameset::class.java)
                    startActivity(intent2)
                }
                builder.show()
                Looper.loop();//增加部分
            }
        }, 600000)
        b1.setOnClickListener {
            myView.move(3)
            myView.postInvalidate()
            myView.judgewin()
            if(myView.flag==1)
            {   mediaPlayer.stop()
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                val ans=jifeng.toString().toInt()+40
                values.put("grade", ans)
                db.update("User", values, "name = ?", arrayOf(name))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("游戏结束")
                builder.setMessage("恭喜，通过此迷宫!")
                builder.setPositiveButton("确定"){
                        DialogInterface,which->
                    val intent2 = Intent(this, cnjgameset::class.java)
                    intent2.putExtra("data",name)
                    startActivity(intent2)
                }
                builder.show()
                myView.flag=0;
            }
        }
        b2.setOnClickListener {
            myView.move(4)
            myView.postInvalidate()
            myView.judgewin()
            if(myView.flag==1)
            {  mediaPlayer.stop()
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                val ans=jifeng.toString().toInt()+40
                values.put("grade", ans)
                db.update("User", values, "name = ?", arrayOf(name))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("游戏结束")
                builder.setMessage("恭喜，通过此迷宫!")
                builder.setPositiveButton("确定"){
                        DialogInterface,which->
                    val intent2 = Intent(this, cnjgameset::class.java)
                    intent2.putExtra("data",name)
                    startActivity(intent2)
                }
                builder.show()
                myView.flag=0;
            }
        }
        b3.setOnClickListener {
            myView.move(1)
            myView.postInvalidate()
            myView.judgewin()
            if(myView.flag==1)
            {  mediaPlayer.stop()
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                val ans=jifeng.toString().toInt()+40
                values.put("grade", ans)
                db.update("User", values, "name = ?", arrayOf(name))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("游戏结束")
                builder.setMessage("恭喜，通过此迷宫!")
                builder.setPositiveButton("确定"){
                        DialogInterface,which->
                    val intent2 = Intent(this, cnjgameset::class.java)
                    intent2.putExtra("data",name)
                    startActivity(intent2)
                }
                builder.show()

                myView.flag=0;
            }
        }
        b4.setOnClickListener {
            myView.move(2)
            myView.postInvalidate()
            myView.judgewin()
            if(myView.flag==1)
            {  mediaPlayer.stop()
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                val ans=jifeng.toString().toInt()+40
                values.put("grade", ans)
                db.update("User", values, "name = ?", arrayOf(name))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("游戏结束")
                builder.setMessage("恭喜，通过此迷宫!")
                builder.setPositiveButton("确定"){
                        DialogInterface,which->
                    val intent2 = Intent(this, cnjgameset::class.java)
                    intent2.putExtra("data",name)
                    startActivity(intent2)
                }
                builder.show()
                myView.flag=0;
            }
        }
        b5.setOnClickListener {
            if (mediaPlayer.isPlaying ) {
                mediaPlayer.pause()
            }
            else if(mediaPlayer2.isPlaying)
            {
                mediaPlayer2.pause()
            }
            else if(mediaPlayer3.isPlaying)
            {
                mediaPlayer3.pause()
            }
            else if(mediaPlayer4.isPlaying)
            {
                mediaPlayer4.pause()
            }
            else
            {
                val items = arrayOf("孤勇者", "逆战", "海月", "英雄")
                val dialog: AlertDialog = AlertDialog.Builder(this)
                    .setIcon(R.mipmap.keji) //设置标题的图片
                    .setTitle("列表对话框") //设置对话框的标题
                    .setItems(items,
                        DialogInterface.OnClickListener { dialog, which ->
                            initMediaPlayer(items[which])
                            if(items[which]=="孤勇者")
                            {
                                mediaPlayer.start()
                            }
                            if(items[which]=="逆战")
                            {
                                mediaPlayer2.start()
                            }
                            if(items[which]=="海月")
                            {
                                mediaPlayer3.start()
                            }
                            if(items[which]=="英雄")
                            {
                                mediaPlayer4.start()
                            }
                        })
                    .setPositiveButton("取消",
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).create()
                dialog.show()
            }
        }
        b6.setOnClickListener {
            mediaPlayer.stop()
            val intent2 = Intent(this, cnjgameset::class.java)
            intent2.putExtra("data",name)
            startActivity(intent2)
        }

    }
    private fun initMediaPlayer(str: String) {
        val assetManager = assets
        if(str=="孤勇者")
        {
            val fd = assetManager.openFd("music0.mp3")
            mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mediaPlayer.prepare()
        }
        if(str=="逆战")
        {
            val f2= assetManager.openFd("music1.mp3")
            mediaPlayer2.setDataSource(f2.fileDescriptor, f2.startOffset, f2.length)
            mediaPlayer2.prepare()
        }
        if(str=="海月")
        {
            val f3 = assetManager.openFd("music2.mp3")
            mediaPlayer3.setDataSource(f3.fileDescriptor, f3.startOffset, f3.length)
            mediaPlayer3.prepare()
        }
        if(str=="英雄")
        {
            val f4= assetManager.openFd("music4.mp3")
            mediaPlayer4.setDataSource(f4.fileDescriptor, f4.startOffset, f4.length)
            mediaPlayer4.prepare()
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}