package com.example.cnjmazegame

import com.example.cnjmazegame.cnjgezi

class cnjgezi(x1: Int, y1: Int) {
    var x = -1
    var y = -1
    var flag = NOTINTREE
    var father: cnjgezi? = null

    companion object {
        @JvmField
        var INTREE = 1
        @JvmField
        var NOTINTREE = 0
    }

    init {
        x = x1
        y = y1
    }
}
