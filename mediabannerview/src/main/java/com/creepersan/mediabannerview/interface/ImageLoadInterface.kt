package com.creepersan.mediabannerview.`interface`

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import java.io.File

interface ImageLoadInterface {

    fun loadImage(context:Context, img:Int, imageView:ImageView)
    fun loadImage(context:Context, img:String, imageView: ImageView)
    fun loadImage(context:Context, img:File, imageView: ImageView)
    fun loadImage(context:Context, img:Bitmap, imageView: ImageView)

}