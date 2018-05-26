package com.creepersan.mediabannerview.holder

import android.graphics.Bitmap
import android.widget.ImageView
import com.creepersan.mediabannerview.MediaBannerView
import java.io.File

class ImageBannerHolder(imageView:ImageView) : BaseBannerHolder(imageView) {
    val imageView by lazy { itemView as ImageView }

    fun setImage(resID:Int){
        MediaBannerView.imageLoader?.loadImage(context, resID, imageView)
    }

    fun setImage(filePath:String){
        MediaBannerView.imageLoader?.loadImage(context, File(filePath), imageView)
    }

    fun setImage(file: File){
        MediaBannerView.imageLoader?.loadImage(context, file, imageView)
    }

    fun setImage(bitmap:Bitmap){
        MediaBannerView.imageLoader?.loadImage(context, bitmap, imageView)
    }

    fun setImageScaleType(scaleType:ImageView.ScaleType){
        imageView.scaleType = scaleType
    }

}