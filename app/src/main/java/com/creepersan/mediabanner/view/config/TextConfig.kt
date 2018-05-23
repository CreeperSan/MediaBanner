package com.creepersan.mediabanner.view.config

import android.content.res.Resources
import android.graphics.Color
import android.support.annotation.ColorInt
import android.util.TypedValue

/**
 * Author      : gemvary
 * Time        : 2018-05-23 10:29
 * Description :
 */
class TextConfig(resources : Resources) : BaseConfig(){

    @ColorInt var textColor : Int = Color.BLACK
    var textSize : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics)

}