package com.creepersan.mediabannerview.holder

import android.view.View

/**
 * Author      : CreeperSan
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
    open fun getGlobalClickableView() : View = itemView

}