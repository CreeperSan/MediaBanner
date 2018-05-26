
# MediaBanner

> A easy use banner view on android.

### How to add to project?
1.Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency
```
dependencies {
	        implementation 'com.github.CreeperSan:MediaBanner:a1.0.0'
	}
```

3. Done!   :)

### How to use?

1. Use in you layout.xml
```
    <com.creepersan.mediabannerview.MediaBannerView
        android:id="@+id/mainBannerView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
will add more attributes later >w< !

2. Set a image load adapter to load image(optional,recommand if you need to use default image banner)
```
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
```

3. Add items 
```
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
                VideoBannerItem("/storage/emulated/0/video/1.mp4"),
                VideoBannerItem("/storage/emulated/0/video/2.mp4"),
                VideoBannerItem("/storage/emulated/0/video/3.mp4")
        ))
```

4. Now you can run on your devices and see what's going on.  :)

### Default Banner
##### TextBannerItem
##### ImageBannerItem
##### VideoBannerItem
How to use? Just put your data in constructure, and also provide some setup methods in it, you will kown how to use when you see the method name :P
### Other usage
##### Setup loop mode
  `setLoopMode()`
    + `LOOP_NO` : will not loop play, stop at the last position
    + `LOOP_LOOP` : will loop, come back to fist position
    + `LOOP_RANDOM` : will loop but randomly
##### Setup auto play mode
  `setAutoPlay()`
   + 'AUTO_PLAY_NEGATIVE' : will auto play but also will bock by touch event and `isUseDefaultAutoPlay` in banner item
   + 'AUTO_PLAY_POSITIVE' : will auto play in times
   + 'AUTO_PLAY_OFF' : will not auto play banner
##### Set global click listener
  'setOnBannerClickListener()' : Do things as the neme said
##### Set auto play time
  `setAutoPlayDelay` : setup auto play times,in ms
##### Banner operation
  + `scrollTo`
  + `scrollNext`
  + `scrollPrevious`
  + `scrollLast`
  + `scrollFirst`
##### Custom Banner
  1. Create a class extend `BaseBanneHolder` and provice some UI control methods in it
  2. Create a class extend `BaseBannerItem<T : BaseBannerHolder>` and implemente method there
  3. Write your logic code in step2's class
    here are some description:
    `gravity : Int` : subscribe the gravity of content
    `isUseDefaultAutoPlay` : whether use default auto play rules(play in customied times)(only work in `AUTO_PLAY_NEGATIVE`)
    `isUseDefaultClickEvent` : whether use the default click event
    `bindHolder()` : run method when the banner holder get a new item
    `getHolder()` : return a new banner holder object 
    `onCreate()` : when banner item attch to a banner holder
    `onDestroy()` : when banner item unattach to a banner
    `onPrepare()` : when banner about to show / banner come to prepare from showing state
    `onShow()` : when banner come to show itself
    `setGravity()` : set the content's gravity
  4. Use it like other banner item
  
  
  ### A detailed readme will upload later, now it's time for launch   XD
 
