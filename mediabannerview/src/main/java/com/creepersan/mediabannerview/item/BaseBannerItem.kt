package com.creepersan.mediabannerview.item

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creepersan.mediabannerview.MediaBannerView
import com.creepersan.mediabannerview.holder.BaseBannerHolder

/**
 * Author      : CreeperSan
 * Time        : 2018-05-23 14:18
 * Description :
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseBannerItem<T : BaseBannerHolder>() {

    open var isUseDefaultClickEvent = true
    open var isUseDefaultAutoPlay = true
    internal var mHolder : T? = null
    internal var gravity = Gravity.CENTER

    companion object {
        internal const val ON_CREATE = 0
        internal const val ON_DESTROY = 1
        internal const val ON_SHOW = 2
        internal const val ON_PREPARE = 3
    }

    abstract fun getHolder(context:Context):T
    internal fun rawBindHolder(context: Context, holder : BaseBannerHolder){
        bindHolder(context ,holder as T)
    }
    abstract fun bindHolder(context: Context,holder: T)

    internal fun parseHolder(type:Int, mediaBanner: MediaBannerView, position:Int, controller: MediaBannerView.MediaBannerController, holder : BaseBannerHolder? = null){
        when(type){
            ON_CREATE -> {
                onCreate(mediaBanner, position, controller, holder as T)
                mHolder = holder
            }
            ON_DESTROY -> {
                onDestroy(mediaBanner, position, controller, holder as T)
                mHolder = null
            }
            ON_SHOW -> {
                onShow(mediaBanner, position, controller, holder as T? ?: mHolder)
            }
            ON_PREPARE -> {
                onPrepare(mediaBanner, position, controller, holder as T? ?: mHolder)
            }
        }
    }

    open fun onCreate(mediaBanner: MediaBannerView, position: Int, controller: MediaBannerView.MediaBannerController, holder: T){}
    open fun onDestroy(mediaBanner: MediaBannerView ,position: Int, controller: MediaBannerView.MediaBannerController, holder: T){}
    open fun onShow(mediaBanner: MediaBannerView ,position: Int, controller: MediaBannerView.MediaBannerController, holder: T?){}
    open fun onPrepare(mediaBanner: MediaBannerView ,position: Int, controller: MediaBannerView.MediaBannerController, holder: T?){}

    /**
     *  设置方法
     */
    fun setGravity(gravity: Int){
        this.gravity = gravity
    }

    /**
     *  拓展方法
     */

    protected fun Context.inflateLayout(@LayoutRes layoutID:Int, viewGroup: ViewGroup? = null, attachToRoot : Boolean = false): View {
        return LayoutInflater.from(this).inflate(layoutID, viewGroup, attachToRoot)
    }

}