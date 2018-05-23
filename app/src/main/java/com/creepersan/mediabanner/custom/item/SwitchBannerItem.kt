package com.creepersan.mediabanner.custom.item

import android.content.Context
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.custom.holder.SwitchBannerHolder
import com.creepersan.mediabanner.view.bean.CustomBannerItem

/**
 * Author      : gemvary
 * Time        : 2018-05-23 17:21
 * Description :
 */
class SwitchBannerItem : CustomBannerItem<SwitchBannerHolder>() {
    var isOn = false

    override fun getHolder(context: Context): SwitchBannerHolder {
        return SwitchBannerHolder(context.inflateLayout(R.layout.banner_switch))
    }

    override fun bindHolder(context: Context, holder: SwitchBannerHolder) {
        holder.switch.isChecked = isOn
        holder.switch.setOnCheckedChangeListener { buttonView, isChecked ->
            isOn = !isOn
            holder.switch.isChecked = isOn
        }
    }
}