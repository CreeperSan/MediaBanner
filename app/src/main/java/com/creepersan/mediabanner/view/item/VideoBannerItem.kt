package com.creepersan.mediabanner.view.item

import android.content.Context
import android.net.Uri
import com.creepersan.mediabanner.view.MediaBanner
import com.creepersan.mediabanner.view.holder.VideoBannerHolder
import com.creepersan.mediabanner.view.util.Console
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.File
import android.content.ContentResolver
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource


/**
 * Author      : gemvary
 * Time        : 2018-05-24 10:47
 * Description :
 */
class VideoBannerItem(var mediaBanner: MediaBanner) : BaseBannerItem<VideoBannerHolder>(), Player.EventListener {
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        Console.log("onPlaybackParametersChanged(playbackParameters)")
    }

    override fun onSeekProcessed() {
        Console.log("onSeekProcessed()")
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        Console.log("onTracksChanged(trackGroups, trackSelections)")
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Console.log("onPlayerError()")
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Console.log("onLoadingChanged($isLoading)")
    }

    override fun onPositionDiscontinuity(reason: Int) {
        Console.log("onPositionDiscontinuity($reason)")
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        Console.log("onRepeatModeChanged($repeatMode)")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        Console.log("onShuffleModeEnabledChanged($shuffleModeEnabled)")
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        Console.log("onTimelineChanged(timeline, manifest, $reason)")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Console.log("onPlayerStateChanged($playWhenReady, $playbackState)")
        if (playWhenReady && playbackState == Player.STATE_ENDED){
            if (mediaBanner.isScrolling()){
                mediaBanner.setCanNextFlag(true)
            }else{
                mediaBanner.scrollNext()
            }
        }
    }

    private var videoPath : String = ""
    private var videoID : Int = 0

    constructor(mediaBanner: MediaBanner,path:String):this(mediaBanner){
        videoPath = path
        videoID = 0
    }
    constructor(mediaBanner: MediaBanner,resID:Int):this(mediaBanner){
        videoID = resID
        videoPath = ""
    }

    override var isUseDefaultAutoPlay: Boolean = false

    override fun getHolder(context: Context): VideoBannerHolder {
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector(AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())))
        exoPlayer.playWhenReady = false
        val playerView = PlayerView(context).apply { useController = false; }
        playerView.player = exoPlayer
        return VideoBannerHolder(playerView)
    }

    override fun bindHolder(context: Context, holder: VideoBannerHolder) {
        val bandwidthMeter = DefaultBandwidthMeter()
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
        val aaa = ExtractorMediaSource.Factory(dataSourceFactory)
        if (videoID == 0){
            holder.player.prepare(aaa.createMediaSource(Uri.fromFile(File(videoPath))))
        }else{
            val dataSpec = DataSpec(RawResourceDataSource.buildRawResourceUri(videoID))
            val rawResourceDataSource = RawResourceDataSource(context)
            rawResourceDataSource.open(dataSpec)
            holder.player.prepare(aaa.createMediaSource(rawResourceDataSource.uri))
        }
    }

    override fun onPrepare(mediaBanner: MediaBanner, position: Int, controller: MediaBanner.MediaBannerController, holder: VideoBannerHolder?) {
        super.onPrepare(mediaBanner, position, controller, holder)
        if (holder == null){ return }
        holder.pause()
        holder.player.removeListener(this)
    }

    override fun onShow(mediaBanner: MediaBanner, position: Int, controller: MediaBanner.MediaBannerController, holder: VideoBannerHolder?) {
        super.onShow(mediaBanner, position, controller, holder)
        if (holder == null) return
        if (holder.player.playbackState == Player.STATE_ENDED){
            holder.player.seekTo(0)
        }
        holder.play()
        holder.player.removeListener(this)
        if (mediaBanner.getAutoPlayMode() == MediaBanner.AUTO_PLAY_NEGATIVE){
            holder.player.addListener(this)
        }
    }


    inner class Tmp(var holder:VideoBannerHolder, var controller: MediaBanner.MediaBannerController,var position:Int) : Player.EventListener{

        fun setData(holder:VideoBannerHolder, controller: MediaBanner.MediaBannerController, position:Int){
            this.holder = holder
            this.controller = controller
            this.position = position
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
        override fun onSeekProcessed() {}
        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}
        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}
        override fun onPlayerError(error: ExoPlaybackException?) {
            holder.player.removeListener(this)
            holder.player.seekTo(0)
            holder.player.stop(false)
            controller?.requireNext(position)
            Console.log("播放错误的下一个")
        }
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_ENDED){
                holder.player.removeListener(this)
                holder.player.seekTo(0)
                holder.player.stop(false)
                controller?.requireNext(position)
                Console.log("播放完成的下一个($playWhenReady, $playbackState)")
            }
        }
    }

    override fun onCreate(mediaBanner: MediaBanner, position: Int, controller: MediaBanner.MediaBannerController, holder: VideoBannerHolder) {
        super.onCreate(mediaBanner, position, controller, holder)
        // 第一个页面加载的时候也需要调用一遍onShow，要不然是视频的话就不会播放了
        if (position == mediaBanner.getPosition()){
            onShow(mediaBanner, position, controller, holder)
        }
    }

    override fun onDestroy(mediaBanner: MediaBanner, position: Int, controller: MediaBanner.MediaBannerController, holder: VideoBannerHolder) {
        super.onDestroy(mediaBanner, position, controller, holder)
    }

}