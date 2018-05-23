package com.creepersan.mediabanner.view.holder

import android.view.View
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Author      : gemvary
 * Time        : 2018-05-24 10:46
 * Description :
 */
class VideoBannerHolder(itemView: View) : BaseBannerHolder(itemView){

    val playerView by lazy { itemView as PlayerView }
    val player by lazy { playerView.player as ExoPlayer }

    fun play(){
        player.playWhenReady = true
        player.playbackState
    }

    fun pause(){
        player.playWhenReady = false
        player.playbackState
    }

}