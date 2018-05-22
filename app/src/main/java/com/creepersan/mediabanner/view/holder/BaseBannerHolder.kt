package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.view.View

/**
 * Author      : gemvary
 * Time        : 2018-05-22 12:16
 * Description :
 */
abstract class BaseBannerHolder(var context:Context) {
    private var isIdle = true

    /**
     *  标志位定义
     */
    fun isBusy():Boolean{
        return !isIdle
    }
    fun isIdle():Boolean{
        return isIdle
    }
    fun setBusy(){
        isIdle = false
    }
    fun setIdle(){
        isIdle = true
    }


    /**
     *  抽象方法
     */
    abstract fun getView(): View


}