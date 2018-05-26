package com.creepersan.mediabannerview.item

import android.content.Context
import android.media.MediaPlayer
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.VideoView
import com.creepersan.mediabannerview.MediaBannerView
import com.creepersan.mediabannerview.holder.VideoBannerHolder
import com.creepersan.mediabannerview.utils.Console
import java.io.File

class VideoBannerItem : BaseBannerItem<VideoBannerHolder>, MediaPlayer.OnCompletionListener {

    override var isUseDefaultAutoPlay: Boolean = false
    private var scaleType = SCALE_TYPE_FIT

    companion object {
        private const val TYPE_NO = -1
        private const val TYPE_RESID = 0
        private const val TYPE_FILE = 1

        const val SCALE_TYPE_FIT = 0
        const val SCALE_TYPE_ORIGINAL = 1
    }

    private var mType = TYPE_NO
    private var mVideoPath : String = ""
    private var mVideoResID : Int = 0

    constructor(videoPath:String){
        mType = TYPE_FILE
        this.mVideoPath = videoPath
        this.mVideoResID = 0
    }
    constructor(videoFile : File){
        mType = TYPE_FILE
        this.mVideoPath = videoFile.absolutePath;
        this.mVideoResID = 0
    }
    constructor(videoResID:Int){
        mType = TYPE_RESID
        this.mVideoPath = ""
        this.mVideoResID = videoResID
    }


    override fun getHolder(context: Context): VideoBannerHolder {
        val videoView = VideoView(context)
        videoView.tag = VideoBannerHolder.TAG_VIDEO_VIEW
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        frameLayout.addView(videoView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        (videoView.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
        return VideoBannerHolder(frameLayout)
    }

    private val wrapParams by lazy { ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) }
    private val matchParams by lazy { ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) }
    override fun bindHolder(context: Context, holder: VideoBannerHolder) {
        // 大小缩放
        val layoutParamsSideSize = when(scaleType){
            SCALE_TYPE_FIT -> {
                ViewGroup.LayoutParams.MATCH_PARENT
            }
            SCALE_TYPE_ORIGINAL -> {
                ViewGroup.LayoutParams.WRAP_CONTENT
            }
            else -> {
                ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        holder.videoView.layoutParams.apply {
            width = layoutParamsSideSize
            height = layoutParamsSideSize
        }
        // 位置
        (holder.videoView.layoutParams as FrameLayout.LayoutParams).gravity = gravity
    }

    private fun setScaleType(scaleType:Int){
        this.scaleType = scaleType
    }

    override fun onCreate(mediaBanner: MediaBannerView, position: Int, controller: MediaBannerView.MediaBannerController, holder: VideoBannerHolder) {
        Console.log("onCreate($position)")
        // 加载资源 & 设置缩放
        when(mType){
            TYPE_FILE -> {
                holder.prepareVideo(mVideoPath)
            }
            TYPE_RESID -> {
                holder.prepareVideo(mVideoResID)
            }
        }
    }

    override fun onDestroy(mediaBanner: MediaBannerView, position: Int, controller: MediaBannerView.MediaBannerController, holder: VideoBannerHolder) {
        Console.log("onDestroy($position)")
    }

    override fun onPrepare(mediaBanner: MediaBannerView, position: Int, controller: MediaBannerView.MediaBannerController, holder: VideoBannerHolder?) {
        Console.log("onPrepare($position)")
        holder?.pause()
        // 监听
        holder?.videoView?.setOnCompletionListener(null)
        tmpController = null
        tmpPosition = null
    }

    override fun onShow(mediaBanner: MediaBannerView, position: Int, controller: MediaBannerView.MediaBannerController, holder: VideoBannerHolder?) {
        Console.log("onShow($position)")
        holder?.play()
        // 监听
        holder?.videoView?.setOnCompletionListener(null)
        if (mediaBanner.getAutoPlayMode() == MediaBannerView.AUTO_PLAY_NEGATIVE){
            tmpController = controller
            tmpPosition = position
            holder?.videoView?.setOnCompletionListener(this)
        }
    }

    var tmpController : MediaBannerView.MediaBannerController? = null
    var tmpPosition : Int? = null
    // 播放完成的回调
    override fun onCompletion(mp: MediaPlayer?) {
        tmpController?.requireNext(tmpPosition ?: return)
    }
}