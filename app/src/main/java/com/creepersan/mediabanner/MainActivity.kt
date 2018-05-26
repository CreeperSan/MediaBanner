package com.creepersan.mediabanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.creepersan.mediabannerview.MediaBannerView
import com.creepersan.mediabannerview.`interface`.ImageLoadInterface
import com.creepersan.mediabannerview.item.ImageBannerItem
import com.creepersan.mediabannerview.item.TextBannerItem
import com.creepersan.mediabannerview.item.VideoBannerItem
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MediaBannerView.imageLoader = object : ImageLoadInterface{
            override fun loadImage(context: Context, img: String, imageView: ImageView) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(img))
            }

            override fun loadImage(context: Context, img: Int, imageView: ImageView) {
                imageView.setImageResource(img)
            }

            override fun loadImage(context: Context, img: File, imageView: ImageView) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(img.absolutePath))
            }

            override fun loadImage(context: Context, img: Bitmap, imageView: ImageView) {
                imageView.setImageBitmap(img)
            }
        }

        mainBannerView.addItems(arrayOf(
                TextBannerItem(R.string.app_name),
                TextBannerItem("11111111111111"),
                TextBannerItem("222222222222222"),
                TextBannerItem("33333333333333"),
                TextBannerItem("44444444444444444"),
                ImageBannerItem(R.drawable.img1).setScaleType(ImageView.ScaleType.CENTER_CROP),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170710_150012.mp4"),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170709_142306.mp4"),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170223_104913.mp4"),
                VideoBannerItem(R.raw.big_buck_bunny),
                VideoBannerItem("/storage/emulated/0/测试视频/1.mp4"),
                VideoBannerItem("/storage/emulated/0/测试视频/2.mp4"),
                VideoBannerItem("/storage/emulated/0/测试视频/3.mp4")
        ))
    }
}
