package com.creepersan.mediabanner.view.bean

/**
 * Author      : gemvary
 * Time        : 2018-05-22 18:01
 * Description :
 */
open class BaseBannerItem {
    private var listener : OnStateChangeListener? = null
    open var isUseDefaultClickEvent = true
    open var isUseDefaultAutoPlay = true

    /**
     *  属性设置方法
     */


    /**
     *  操作方法
     */
    fun setOnStateChangeListener(listener:OnStateChangeListener){
        this.listener = listener
    }
    fun getOnStateChangeListener():OnStateChangeListener?{
        return listener
    }
    protected fun notifyNext(){

    }
    /**
     *  接口
     */
    interface OnStateChangeListener{
        fun onShow()
        fun onCreate()
        fun onDestroy()
    }
}