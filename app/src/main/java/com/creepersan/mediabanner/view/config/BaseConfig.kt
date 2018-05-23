package com.creepersan.mediabanner.view.config

import android.view.Gravity

/**
 * Author      : gemvary
 * Time        : 2018-05-23 09:50
 * Description :
 */
open class BaseConfig {

    open var isBlockAutoPlay = false    // 这个标识为决定了在消极播放的模式下，是否会打断替换默认时间的自动播放，如果设置为True，则需要手动执行scrollNext()，否则就是默认的自动播放
    var gravity : Int = Gravity.CENTER

}