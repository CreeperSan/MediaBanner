package com.creepersan.mediabanner.custom.item

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.custom.holder.PhotoBannerHolder
import com.creepersan.mediabanner.view.item.BaseBannerItem

/**
 * Author      : gemvary
 * Time        : 2018-05-23 14:22
 * Description :
 */
class PhotoBannerItem() : BaseBannerItem<PhotoBannerHolder>() {

    override var isUseDefaultClickEvent: Boolean = false

    var title = "优胜美地"
    var imgID = R.drawable.img1
    var isShowDescription = false
    var description = "优胜美地国家公园（Yosemite National Park），位于美国西部加利福尼亚州，是美国国家公园。占地面积约1100平方英里。"


    constructor(title:String, imgID:Int, description:String):this(){
        this.title = title
        this.imgID = imgID
        this.description = description
    }

    override fun getHolder(context: Context): PhotoBannerHolder {
        return PhotoBannerHolder(context.inflateLayout(R.layout.banner_photo))
    }

    override fun bindHolder(context: Context, holder: PhotoBannerHolder) {
        Glide.with(context).load(imgID).into(holder.backgroundImg)
        holder.titleText.text = title
        holder.descriptionText.text = description
        refreshDescriptionState(holder)
        holder.iconImg.setOnClickListener {
            isShowDescription = !isShowDescription
            refreshDescriptionState(holder)
        }
    }
    private fun refreshDescriptionState(holder:PhotoBannerHolder){
        if (isShowDescription){
            holder.descriptionText.visibility = View.VISIBLE
            holder.iconImg.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp)
        }else{
            holder.descriptionText.visibility = View.GONE
            holder.iconImg.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp)
        }
    }
}