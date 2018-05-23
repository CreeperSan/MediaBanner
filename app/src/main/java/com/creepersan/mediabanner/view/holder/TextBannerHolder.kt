package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.TextBannerItem
import com.creepersan.mediabanner.view.config.TextConfig

/**
 * Author      : gemvary
 * Time        : 2018-05-22 12:15
 * Description :
 */
class TextBannerHolder(context: Context) : BaseBannerHolder(context){

    private val textView by lazy { TextView(context) }

    override fun getView(): View {
        return textView
    }

    fun setText(content:String, config: TextConfig){
        initConfig(config)
        textView.text = content
    }

    private fun initConfig(config: TextConfig){
        textView.setTextColor(config.textColor)
        textView.textSize = config.textSize
        textView.gravity = config.gravity
    }

}