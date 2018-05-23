package com.creepersan.mediabanner.view.bean

import com.creepersan.mediabanner.view.config.VideoConfig

/**
 * Author      : gemvary
 * Time        : 2018-05-22 18:02
 * Description :
 */
class VideoBannerItem(var video:String): BaseBannerItem(){

    var config : VideoConfig? = null
    override var isUseDefaultAutoPlay: Boolean = false

}