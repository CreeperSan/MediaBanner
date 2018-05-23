package com.creepersan.mediabanner.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.creepersan.mediabanner.R
import com.creepersan.mediabanner.custom.item.PhotoBannerItem
import com.creepersan.mediabanner.custom.item.SwitchBannerItem
import com.creepersan.mediabanner.view.MediaBanner
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.ImageBannerItem
import com.creepersan.mediabanner.view.bean.TextBannerItem
import com.creepersan.mediabanner.view.bean.VideoBannerItem
import com.creepersan.mediabanner.view.config.TextConfig
import com.creepersan.mediabanner.view.util.Console
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainMediaBanner.addItems(arrayOf(
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170710_150012.mp4"),
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
                VideoBannerItem("/storage/emulated/0/DCIM/Camera/VID_20170710_150012.mp4"),
                PhotoBannerItem("优胜美地", R.drawable.img1,"优胜美地国家公园是美国加州中东部横跨图奥勒米县、马里波萨县和马德拉县东部部分地区的国家公园。"),
                PhotoBannerItem("优胜美地", R.drawable.img2,"该公园占地747,956英亩并延伸到了横跨内华达山脉西坡。"),
                PhotoBannerItem("优胜美地", R.drawable.img3,"优胜美地国家公园每年大约有三百八十多万游客：大部分人在七平方英里的约塞米蒂山谷逗留。"),
                SwitchBannerItem(),
                SwitchBannerItem(),
                SwitchBannerItem(),
                SwitchBannerItem()
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

        mainMediaBanner.setOnBannerScrollListener(object : MediaBanner.OnBannerScrollListener{
            override fun onPageChange(position: Int) {
                mainSeekBar.progress = position
            }
        })

        mainFirst.setOnClickListener { mainMediaBanner.scrollFirst() }
        mainPrev.setOnClickListener { mainMediaBanner.scrollPrevious() }
        mainNext.setOnClickListener { mainMediaBanner.scrollNext() }
        mainLast.setOnClickListener { mainMediaBanner.scrollLast() }

        mainLoopLoop.setOnClickListener { mainMediaBanner.setLoopMode(MediaBanner.LOOP_LOOP) }
        mainLoopNo.setOnClickListener { mainMediaBanner.setLoopMode(MediaBanner.LOOP_NO) }
        mainLoopRandom.setOnClickListener { mainMediaBanner.setLoopMode(MediaBanner.LOOP_RANDOM) }

        mainAutoPlayNo.setOnClickListener { mainMediaBanner.setAutoPlay(MediaBanner.AUTO_PLAY_OFF) }
        mainAutoPlayPositive.setOnClickListener { mainMediaBanner.setAutoPlay(MediaBanner.AUTO_PLAY_POSITIVE) }
        mainAutoPlayNegative.setOnClickListener { mainMediaBanner.setAutoPlay(MediaBanner.AUTO_PLAY_NEGATIVE) }

        mainMediaBanner.setGlobalTextConfig(TextConfig(resources).apply { textColor = Color.RED })

        mainAutoPlayDelaySeekBar.progress = mainMediaBanner.getAutoPlayDelay().toInt()
        mainAutoPlayDelaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress<100){
                    seekBar.progress = 100
                }
                mainAutoPlayDelayText.text = "自动播放延时 : $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mainMediaBanner.setAutoPlayDelay(seekBar.progress)
            }
        })

        mainMediaBanner.setOnBannerClickListener(object : MediaBanner.OnBannerClickListener{
            override fun onClick(position: Int, item: BaseBannerItem) {
                Console.log("Click #$position  Item => ${item.javaClass.simpleName}  Time => ${System.currentTimeMillis()}")
            }
        })
    }
}
