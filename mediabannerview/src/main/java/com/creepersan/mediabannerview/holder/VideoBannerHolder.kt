package com.creepersan.mediabannerview.holder

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.annotation.RawRes
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import com.creepersan.mediabannerview.item.VideoBannerItem
import java.io.File

class VideoBannerHolder(itemView:View) : BaseBannerHolder(itemView) {

    companion object {
        internal const val TAG_VIDEO_VIEW = "inner-video-view"
    }

    val videoView by lazy { frameLayout.findViewWithTag(TAG_VIDEO_VIEW) as VideoView }
    val frameLayout by lazy { itemView as FrameLayout }

    init {
        videoView.setMediaController(MediaController(context).apply { visibility = View.GONE })
    }

    fun prepareVideo(videoPath:String){
        try {
            videoView.setVideoPath(videoPath)
        }catch (e:Exception){

        }
    }
    fun prepareVideo(video: File){
        videoView.setVideoPath(video.absolutePath)
    }
    fun prepareVideo(@RawRes video:Int){
        videoView.setVideoURI(toUri(video))
    }

    private fun toUri(@RawRes id:Int):Uri{
        return Uri.parse("android.resource://${context.packageName}/$id")
    }

    fun play(){
        videoView.start()
    }
    fun pause(){
//        if (videoView.canPause()){
//            videoView.pause()
//        }
        videoView.pause()
    }
    fun replay(){
        videoView.seekTo(0)
        videoView.start()
    }
    fun seekto(position:Int){
        videoView.seekTo(position)
    }

}