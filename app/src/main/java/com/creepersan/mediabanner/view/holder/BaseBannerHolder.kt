package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.view.View

/**
 * Author      : gemvary
 * Time        : 2018-05-23 14:16
 * Description :
 */
abstract class BaseBannerHolder(var itemView: View) {
    private var isIdle = true
    val context = itemView.context

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

    fun getView(): View = itemView

}