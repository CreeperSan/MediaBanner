package com.creepersan.mediabanner.view.bean

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creepersan.mediabanner.view.exception.HolderTypeNotMatchException
import com.creepersan.mediabanner.view.holder.CustomBannerHolder

/**
 * Author      : gemvary
 * Time        : 2018-05-23 14:18
 * Description :
 */
@Suppress("UNCHECKED_CAST")
abstract class CustomBannerItem<T : CustomBannerHolder> : BaseBannerItem() {


    abstract fun getHolder(context:Context):T
    internal fun rawBindHolder(context: Context, holder : CustomBannerHolder){
        bindHolder(context ,holder as T)
    }
    abstract fun bindHolder(context: Context,holder: T)

    /**
     *  拓展方法
     */

    protected fun Context.inflateLayout(@LayoutRes layoutID:Int, viewGroup: ViewGroup? = null, attachToRoot : Boolean = false): View {
        return LayoutInflater.from(this).inflate(layoutID, viewGroup, attachToRoot)
    }

}