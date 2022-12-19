package com.example.cnjmazegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.example.cnjmazegame.cnjgezi
import java.util.*

class cnjviewhard(context: Context?) : View(context) {
    var flag = 0
    var padding = 10
    var gamewideh = 1080
    var NUM = 20
    var width:Int?= (gamewideh - 2 * padding) / NUM
    var maze: Array<Array<cnjgezi?>>?=null
    var ballX = 0
    var ballY = 0
    var drawPath = false
    var paint: Paint? = null
    fun init() {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = -0x1000000
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = 2f
        maze = Array(NUM) { arrayOfNulls(NUM) }
        for (i in 0..NUM - 1) {
            for (j in 0..NUM - 1) {
                maze!![i][j] = cnjgezi(i, j)
            }
        }
        for (i in 0..NUM - 1) {
            for (j in 0..NUM - 1) {
                maze!![i][j]!!.father = null
                maze!![i][j]!!.flag = cnjgezi.NOTINTREE
            }
        }
        ballX = 0
        ballY = 0
        drawPath = false
        createMaze()
    }

    fun createMaze() {
        val random = Random()
        val rx = Math.abs(random.nextInt()) % NUM
        val ry = Math.abs(random.nextInt()) % NUM
        var net: Array<cnjgezi?>? = null //网格
        val s = Stack<cnjgezi?>() //此处使用了BFS思想,此时使用的Stack
        var p = maze!![rx][ry] //先随机确定一个点，然后利用广度优先搜索的思想对整个图进行遍历，转换成树
        s.push(p)
        while (!s.isEmpty()) {
            p = s.pop()
            p!!.flag = cnjgezi.INTREE
            net = getnet(p)
            var ans = Math.abs(random.nextInt()) % 4
            for (a in 0..3) {
                ans++
                ans %= 4
                if (net!![ans] == null || net[ans]!!.flag == cnjgezi.INTREE) //对周边的结点遍历，存在且未访问过
                    continue
                s.push(net[ans])
                net[ans]!!.father = p //记录周围的子节点
            }
        }
    }

    fun getnet(p: cnjgezi?): Array<cnjgezi?>? {
        val adds = intArrayOf(-1, 0, 1, 0, -1)
        if (isOutOfBorder(p)) {
            return null
        }
        val res = arrayOfNulls<cnjgezi>(4)
        for (i in 0..3) {
            val xt = p!!.x + adds[i]
            val yt = p.y + adds[i + 1]
            if (isOutOfBorder(xt, yt)) continue
            res[i] = maze!![xt][yt]
        }
        return res
    }

    fun move(c: Int) {
        var tx = ballX
        var ty = ballY
        when (c) {
            1 -> ty--
            2 -> ty++
            3 -> tx--
            4 -> tx++
            5 -> drawPath = if (drawPath == true) {
                false
            } else {
                true
            }
            else -> {}
        }
        //检查是否联通且未越界
        if (!isOutOfBorder(
                tx,
                ty
            ) && (maze!![tx][ty]!!.father == maze!![ballX][ballY] || maze!![ballX][ballY]!!.father == maze!![tx][ty])
        ) {
            ballX = tx
            ballY = ty
        }
    }

    fun isOutOfBorder(p: cnjgezi?): Boolean {
        return isOutOfBorder(p!!.x, p.y)
    }

    fun isOutOfBorder(x: Int, y: Int): Boolean {
        return if (x > NUM - 1 || y > NUM - 1 || x < 0 || y < 0) true else false
    }

    fun getCenterX(x: Int): Int {
        return padding + x * width!! + width!! / 2
    }

    fun getCenterY(y: Int): Int {
        return padding + y * width!! + width!! / 2
    }

    fun judgewin() {
        if (ballX == NUM - 1 && ballY == NUM - 1) {
            flag = 1
            init()
        }
    }

    var pos = 0
    var map: Array<IntArray>?=null
    fun helpway() {
        pos = 1
        map = Array(NUM) { IntArray(NUM) }
        for (i in 0..NUM - 1) {
            for (j in 0..NUM - 1) {
                map!![i][j] = 0
            }
        }
    }

    fun recver() {
        pos = 0
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(-0x1)
        //从上到下画线
        for (i in 0..NUM) {
            canvas.drawLine(
                (padding + i * width!!).toFloat(),
                padding.toFloat(),
                (padding + i * width!!).toFloat(),
                (
                        padding + NUM * width!!).toFloat(),
                paint!!
            )
        }
        //从左到右画线
        for (j in 0..NUM) {
            canvas.drawLine(
                padding.toFloat(),
                (padding + j * width!!).toFloat(),
                (padding + NUM * width!!).toFloat(),
                (
                        padding + j * width!!).toFloat(),
                paint!!
            )
        }
        paint!!.color = -0x1
        for (i in NUM - 1 downTo 0) {
            for (j in NUM - 1 downTo 0) {
                val f = maze!![i][j]!!.father
                if (f != null) {
                    val fx = f.x
                    val fy = f.y //获取父节点的坐标
                    clearFence(i, j, fx, fy, canvas)
                }
            }
        }
        if (pos == 1) {
            map!![ballX][ballY] = 100
            for (i in NUM - 1 downTo 0) {
                for (j in NUM - 1 downTo 0) {
                    if (map!![i][j] == 100) {
                        findway(i, j, canvas)
                    }
                }
            }
        }
        //入口
        canvas.drawLine(
            padding.toFloat(),
            (padding + 1).toFloat(),
            padding.toFloat(),
            (padding + width!! - 1).toFloat(),
            paint!!
        )
        val last = padding + NUM * width!!
        //出口
        canvas.drawLine(
            last.toFloat(),
            (last - 1).toFloat(),
            last.toFloat(),
            (last - width!! + 1).toFloat(),
            paint!!
        )
        //画玩家小球
        paint!!.color = -0xfc253b
        paint!!.style = Paint.Style.FILL
        val cx = (getCenterX(ballY) - width!! / 3 + padding).toFloat()
        val cy = (getCenterY(ballX) - width!! / 3 + padding).toFloat()
        val ra = (width!! / 3).toFloat()
        canvas.drawCircle(cx, cy, ra, paint!!)
    }

    fun clearFence(i: Int, j: Int, fx: Int, fy: Int, canvas: Canvas) {
        var sx = padding + (if (j > fy) j else fy) * width!!
        //取出两个点中相对于右下角的点的坐标
        var sy = padding + (if (i > fx) i else fx) * width!!
        var dx = if (i == fx) sx else sx + width!!
        //如果和父结点在同一列,取右下角的x;不在，右下角x + "1"加上一个单位长度
        var dy =
            if (i == fx) sy + width!! else sy //...                        y + "1"           y   (右移还是下移)
        if (sx != dx) {
            sx++
            dx--
        } else {
            sy++
            dy--
        }
        canvas.drawLine(sx.toFloat(), sy.toFloat(), dx.toFloat(), dy.toFloat(), paint!!)
    }

    fun findway(fy: Int, fx: Int, canvas: Canvas) {
        paint!!.color = -0x10000
        paint!!.style = Paint.Style.FILL
        val cx = (getCenterX(fx) - width !!/ 3 + padding).toFloat()
        val cy = (getCenterY(fy) - width!! / 3 + padding).toFloat()
        val ra = (width!! / 4).toFloat()
        canvas.drawCircle(cx, cy, ra, paint!!)
    }

    init {
        init()
    }
}