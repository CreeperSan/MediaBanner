package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creepersan.mediabanner.view.bean.CustomBannerItem

/**
 * Author      : gemvary
 * Time        : 2018-05-23 14:16
 * Description :
 */
abstract class CustomBannerHolder(view: View) : BaseBannerHolder(view.context) {

    val itemView = view

    final override fun getView(): View = itemView

}