package com.creepersan.mediabanner.view.holder

import android.view.View
import android.widget.ImageView

/**
 * Author      : gemvary
 * Time        : 2018-05-24 10:38
 * Description :
 */
class ImageBannerHolder(itemView:View) : BaseBannerHolder(itemView) {
    val imageView by lazy { itemView as ImageView }
}