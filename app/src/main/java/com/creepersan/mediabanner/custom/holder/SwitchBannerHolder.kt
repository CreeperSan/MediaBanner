package com.creepersan.mediabanner.custom.holder

import android.view.View
import android.widget.Switch
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.view.holder.BaseBannerHolder

/**
 * Author      : gemvary
 * Time        : 2018-05-23 17:22
 * Description :
 */
class SwitchBannerHolder(itemView:View) : BaseBannerHolder(itemView) {
    var switch = itemView.findViewById<Switch>(R.id.bannerSwitchSwitch)

}