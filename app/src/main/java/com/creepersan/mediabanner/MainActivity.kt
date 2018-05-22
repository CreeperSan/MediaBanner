package com.creepersan.mediabanner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.creepersan.mediabanner.view.MediaBanner
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.ImageBannerItem
import com.creepersan.mediabanner.view.bean.TextBannerItem
import com.creepersan.mediabanner.view.bean.VideoBannerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainMediaBanner.addItems(arrayOf(
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170710_150012.mp4"),
                ImageBannerItem(R.drawable.ic_launcher_background),
                ImageBannerItem("/storage/emulated/0/DCIM/Camera/IMG_20170223_172040.jpg").apply { setOnStateChangeListener(
                        object : BaseBannerItem.OnStateChangeListener{
                            override fun onShow() {
                                Log.i("MediaBanner", "正在显示！")
                            }

                            override fun onCreate() {
                                Log.i("MediaBanner", "创建了")
                            }

                            override fun onDestroy() {
                                Log.i("MediaBanner", "销毁了")
                            }

                        }
                ) },
                ImageBannerItem("/storage/emulated/0/DCIM/Camera/IMG_20170708_100653.jpg"),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170709_142306.mp4"),
                ImageBannerItem(R.drawable.ic_audiotrack_black_24dp),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170223_104913.mp4"),
                ImageBannerItem(R.drawable.ic_brightness_2_black_24dp),
                ImageBannerItem("/storage/emulated/0/DCIM/Camera/IMG_20170710_172242.jpg"),
                TextBannerItem("Fire in the hole"),
                ImageBannerItem(R.mipmap.ic_launcher),
                ImageBannerItem("/storage/emulated/0/DCIM/Camera/IMG_20170711_121252.jpg"),
                TextBannerItem("纯文本"),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170709_142306.mp4"),
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170710_150012.mp4")
        ))

        mainSeekBar.max = mainMediaBanner.getSize()-1
        mainSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mainMediaBanner.scrollTo(progress)
                }
            }
        })

        mainMediaBanner.setMediaBannerScrollListener(object : MediaBanner.OnMediaBannerScrollListener{
            override fun onPageChange(position: Int) {
                mainSeekBar.progress = position
            }
        })

        mainFirst.setOnClickListener { mainMediaBanner.scrollFirst() }
        mainPrev.setOnClickListener { mainMediaBanner.scrollPrevious() }
        mainNext.setOnClickListener { mainMediaBanner.scrollNext() }
        mainLast.setOnClickListener { mainMediaBanner.scrollLast() }

    }
}
