package com.creepersan.mediabanner.view.item

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.view.holder.ImageBannerHolder

/**
 * Author      : gemvary
 * Time        : 2018-05-24 10:41
 * Description :
 */
class ImageBannerItem : BaseBannerItem<ImageBannerHolder> {
    var imageRes = 0
    var imagePath = ""
    var scaleType = ImageView.ScaleType.CENTER_CROP

    constructor(imgRes : Int){
        imageRes = imgRes
        imagePath = ""
    }
    constructor(imgPath : String){
        imageRes = 0
        imagePath = imgPath
    }

    override fun getHolder(context: Context): ImageBannerHolder {
        return ImageBannerHolder(ImageView(context))
    }

    override fun bindHolder(context: Context, holder: ImageBannerHolder) {
        holder.imageView.scaleType = scaleType
        if (imageRes == 0){
            Glide.with(holder.imageView).load(imagePath).into(holder.imageView)
        }else{
            Glide.with(holder.imageView).load(imageRes).into(holder.imageView)
        }
    }


}