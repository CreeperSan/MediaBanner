package com.creepersan.mediabanner.custom.holder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.custom.item.PhotoBannerItem
import com.creepersan.mediabanner.view.holder.CustomBannerHolder

/**
 * Author      : gemvary
 * Time        : 2018-05-23 14:20
 * Description :
 */
class PhotoBannerHolder(itemView:View) : CustomBannerHolder(itemView){
    var backgroundImg = itemView.findViewById<ImageView>(R.id.bannerPhotoImage)
    var titleText = itemView.findViewById<TextView>(R.id.bannerPhotoTitle)
    var iconImg = itemView.findViewById<ImageView>(R.id.bannerPhotoIcon)
    var descriptionText = itemView.findViewById<TextView>(R.id.bannerPhotoDescription)
}