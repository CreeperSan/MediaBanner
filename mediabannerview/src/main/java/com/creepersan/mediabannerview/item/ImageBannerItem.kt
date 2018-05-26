package com.creepersan.mediabannerview.item

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.creepersan.mediabannerview.MediaBannerView
import com.creepersan.mediabannerview.holder.ImageBannerHolder
import java.io.File

class ImageBannerItem : BaseBannerItem<ImageBannerHolder> {

    companion object {
        private const val TYPE_NO = -1
        private const val TYPE_RESID = 0
        private const val TYPE_FILE = 1
        private const val TYPE_BITMAP = 2
        private const val TYPE_STRING = 3
    }

    private var mDataSource = TYPE_NO
    private var mDataResID = 0
    private var mDataFile : File? = null
    private var mDataBitmap :Bitmap? = null
    private var mDataString : String = ""
    private var scaleType : ImageView.ScaleType = ImageView.ScaleType.CENTER

    constructor(imgID:Int){
        mDataSource = TYPE_RESID
        mDataResID = imgID
        mDataFile = null
        mDataBitmap = null
        mDataString = ""
    }
    constructor(file:String){
        mDataSource = TYPE_STRING
        mDataResID = 0
        mDataBitmap = null
        mDataString = file
    }
    constructor(file:File){
        mDataSource = TYPE_FILE
        mDataResID = 0
        mDataFile = file
        mDataBitmap = null
        mDataString = ""
    }
    constructor(bitmap:Bitmap){
        mDataSource = TYPE_BITMAP
        mDataResID = 0
        mDataFile = null
        mDataBitmap = bitmap
        mDataString = ""
    }


    fun setScaleType(scaleType: ImageView.ScaleType):ImageBannerItem{
        this.scaleType = scaleType
        return this
    }

    override fun getHolder(context: Context): ImageBannerHolder {
        return ImageBannerHolder(ImageView(context))
    }

    override fun bindHolder(context: Context, holder: ImageBannerHolder) {
        // 加载资源
        when(mDataSource){
            TYPE_BITMAP -> {
                MediaBannerView.imageLoader?.loadImage(context, mDataBitmap ?: return, holder.imageView)
            }
            TYPE_FILE -> {
                MediaBannerView.imageLoader?.loadImage(context, mDataFile ?: return, holder.imageView)
            }
            TYPE_RESID -> {
                MediaBannerView.imageLoader?.loadImage(context, mDataResID, holder.imageView)
            }
            TYPE_STRING -> {
                MediaBannerView.imageLoader?.loadImage(context, mDataString, holder.imageView)
            }
        }
        // 加载其他配置
        holder.imageView.scaleType = scaleType
    }

}