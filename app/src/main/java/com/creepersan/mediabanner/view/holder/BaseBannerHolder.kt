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
    internal fun isBusy():Boolean{
        return !isIdle
    }
    internal fun isIdle():Boolean{
        return isIdle
    }
    internal fun setBusy(){
        isIdle = false
    }
    internal fun setIdle(){
        isIdle = true
    }


    /**
     *  抽象方法
     */
    abstract fun getView(): View


}