package com.creepersan.mediabannerview.holder

import android.support.annotation.ColorInt
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.creepersan.mediabannerview.holder.BaseBannerHolder

/**
 * Author      : CreeperSan
 * Time        : 2018-05-25 08:36
 * Description :
 */
class TextBannerHolder(itemView:View) : BaseBannerHolder(itemView) {
    val textView by lazy { itemView as TextView }

    fun setText(textID:Int){
        textView.setText(textID)
    }
    fun setText(text:String){
        textView.text = text
    }
    fun getText():String{
        return textView.text.toString()
    }

    fun setTextColor(@ColorInt color:Int){
        textView.setTextColor(color)
    }

    fun setTextSize(textSize:Float){
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, context.resources.displayMetrics)
    }

    fun setGravity(gravity:Int){
        textView.gravity = gravity
    }
}