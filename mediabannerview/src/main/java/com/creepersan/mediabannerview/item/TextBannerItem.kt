package com.creepersan.mediabannerview.item

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.widget.TextView
import com.creepersan.mediabannerview.holder.TextBannerHolder

/**
 * Author      : CreeperSan
 * Time        : 2018-05-25 08:36
 * Description :
 */
class TextBannerItem() : BaseBannerItem<TextBannerHolder>() {
    @ColorInt var color:Int = Color.parseColor("#333333")
    var textSize = 12f
    var textStr = ""
    var textRes = 0

    constructor(text:String):this(){
        this.textStr = text
    }
    constructor(@StringRes textRes:Int):this(){
        this.textRes = textRes
    }

    override fun getHolder(context: Context): TextBannerHolder {
        return TextBannerHolder(TextView(context))
    }

    override fun bindHolder(context: Context, holder: TextBannerHolder) {
        holder.setGravity(gravity)
        holder.setTextSize(textSize)
        holder.setTextColor(color)
        if (textRes == 0){
            holder.setText(textStr)
        }else{
            holder.setText(textRes)
        }
    }
}