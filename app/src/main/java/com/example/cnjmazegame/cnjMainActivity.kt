package com.example.cnjmazegame

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog

class cnjMainActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()
    private  var i1: ImageView? = null
    private var passward: EditText? = null
    private var isShow = true
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cnjmain)
        val edname:EditText=findViewById(R.id.input_user_text)
        passward=findViewById(R.id.input_key_text)
        val textView:TextView=findViewById(R.id.zhuce)
        val b1: Button =findViewById(R.id.btn1)
        val b2: Button =findViewById(R.id.btn2)
        i1 = findViewById<ImageView>(R.id.iv_eye1)
        i1?.setOnClickListener {
            if (isShow) {
                isShow = false
                //显示
                passward?.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                isShow = true
                //隐藏
                passward?.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }
        val dbHelper = cnjMyDatabaseHelper(this, "Usersum.db", 3)
        dbHelper.writableDatabase
        initMediaPlayer()
        mediaPlayer.start()
        b1.setOnClickListener {
            var flag=0;
            val db = dbHelper.writableDatabase
            val cursor = db.query("User", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"))
                    val grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade"))
                    if(name.equals(edname.text.toString()) && pass.equals(passward?.text.toString()))
                    {
                          flag=1
                    }
                 } while (cursor.moveToNext())
            }
            cursor.close()
            if(flag==1)
            {
                mediaPlayer.stop()
                val intent2 = Intent(this, cnjgameset::class.java)
                intent2.putExtra("data",edname.text.toString())
                startActivity(intent2)
            }
            else
            {
                if(TextUtils.isEmpty(edname.text.toString()) || TextUtils.isEmpty(passward?.text.toString()))
                {
                    Toast.makeText(applicationContext, "用户名或密码不能为空", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(applicationContext, "用户名或密码错误", Toast.LENGTH_LONG).show()

                }
            }


        }
        b2.setOnClickListener {
           // val builder = AlertDialog.Builder(this)
           //  builder.setTitle("游戏说明")
           // builder.setMessage("游戏简介:走迷宫是一款益智类经典小游戏\n操作说明:通过屏幕下方4个方向键控制行走路线,只要将人物从左上角移动到右下角就视为成功闯关。闯关同时还提供了循环播放背景音乐，通过右下角音乐控制按钮可以实现对背景音乐的控制，点击左下角的返回设置按钮，可以返回游戏设置界面重新选择难度和迷宫,点击help按钮，将会显示你接下来走过的路径，方便通关。")
           // builder.setPositiveButton("确定"){DialogInterface,which-> }
           // builder.show()
            showDialog()
        }
        val ss = SpannableString("立刻注册")
        ss.setSpan(object : ClickableSpan() {
            override fun updateDrawState(t: TextPaint) {
                super.updateDrawState(t)
                t.isUnderlineText = false //设置去掉下划线
                t.color = Color.BLACK //设置字体颜色为黑
            }

            override fun onClick(widget: View) {
                val intent = Intent(this@cnjMainActivity, cnjzhuceActivity::class.java) //当点击详情页时触发事件函数完成页面跳转
                startActivity(intent)
            }
        }, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        textView.setText(ss)
        textView.setMovementMethod(LinkMovementMethod.getInstance())

    }
    private fun initMediaPlayer()
    {
        val assetManager = assets
        val fd = assetManager.openFd("first.wav")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.activity_cnj_custom_dialog)
        val title = dialog .findViewById(R.id.youxititle) as TextView
        title.text = "游戏介绍"
        val body = dialog .findViewById(R.id.youximessage) as TextView
        val noBtn = dialog .findViewById(R.id.btn_cancel) as Button
        body.text="游戏简介:走迷宫是一款益智类经典小游戏\n操作说明:通过屏幕下方4个方向键控制行走路线,只要将人物从左上角移动到右下角就视为成功闯关。闯关同时还提供了循环播放背景音乐，通过右下角按钮可以实现对背景音乐的控制，可以选择歌曲，点击左下角的返回设置按钮，可以返回游戏设置界面重新选择难度和迷宫,点击help按钮，将会显示你接下来走过的路径，方便通关。"
        noBtn.setOnClickListener { dialog .dismiss() }
        dialog .show()

    }
}