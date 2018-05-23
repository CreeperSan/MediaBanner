package com.creepersan.mediabanner.view.bean

import com.creepersan.mediabanner.view.config.ImageConfig

/**
 * Author      : gemvary
 * Time        : 2018-05-22 17:32
 * Description :
 */
class ImageBannerItem: BaseBannerItem{

    var imageID : Int = EMPTY_IMAGE_ID
    var imagePath : String = EMPTY_IMAGE_PATH
    var config : ImageConfig? = null

    companion object {
        const val EMPTY_IMAGE_ID = 0
        const val EMPTY_IMAGE_PATH = ""
    }

    constructor(imageID : Int){
        this.imageID = imageID
        imagePath = EMPTY_IMAGE_PATH
    }

    constructor(imagePath : String){
        this.imagePath = imagePath
        imageID = EMPTY_IMAGE_ID
    }





}