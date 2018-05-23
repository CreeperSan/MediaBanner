package com.creepersan.mediabanner.view.holder

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import com.creepersan.mediabanner.view.util.Console
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.File
import kotlin.math.log

/**
 * Author      : gemvary
 * Time        : 2018-05-22 16:31
 * Description :
 */
class VideoBannerHolder(context: Context) : BaseBannerHolder(context) {

    private val exoPlayer by lazy { ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector(AdaptiveTrackSelection.Factory(DefaultBandwidthMeter()))) }
    private val playerView by lazy { PlayerView(context).apply { useController = false } }

    init {
        playerView.player = exoPlayer
        exoPlayer.playWhenReady = true
    }

    override fun getView(): View {
        return playerView
    }

    fun play(filePath:String){
        val bandwidthMeter = DefaultBandwidthMeter()
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
        val aaa = ExtractorMediaSource.Factory(dataSourceFactory)
        log("Play!!!!!!!!!!!!!!")
        exoPlayer.prepare(aaa.createMediaSource(Uri.fromFile(File(filePath))));
    }

    private fun log(content:String){
        Log.i("MediaBanner",content)
    }

}