package com.creepersan.mediabannerview.utils

import android.util.Log

/**
 * Author      : CreeperSan
 * Time        : 2018-05-22 19:24
 * Description :
 */
object Console {
    val isDebug = false
    val TAG = "MediaBanner"

    fun error(content: String, tag: String = TAG){
        if (isDebug)
            Log.e(tag, content)
    }

    fun warming(content:String, tag: String = TAG){
        if (isDebug)
            Log.w(tag, content)
    }

    fun log(content:String, tag: String = TAG){
        if (isDebug)
            Log.i(tag, content)
    }

}