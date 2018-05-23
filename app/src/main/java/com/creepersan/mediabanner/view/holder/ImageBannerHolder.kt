package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.ImageBannerItem
import com.creepersan.mediabanner.view.config.ImageConfig
import java.io.File

/**
 * Author      : gemvary
 * Time        : 2018-05-22 12:15
 * Description :
 */
class ImageBannerHolder(context: Context) : BaseBannerHolder(context){

    private val imageView = ImageView(context).apply { setImageResource(R.drawable.ic_person_black_24dp) }

    fun setImageRes(imgID:Int, config: ImageConfig){
        initImageView(config)
        Glide.with(context).load(imgID).into(imageView)
    }

    fun setImage(path:String, config: ImageConfig){
        initImageView(config)
        Glide.with(context).load(File(path)).into(imageView)
    }

    private fun initImageView(config:ImageConfig){
        imageView.scaleType = config.scaleType
    }

    override fun getView(): View {
        return imageView
    }

}