package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.TextBannerItem

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

    fun setText(content:String){
        textView.text = content
    }

}