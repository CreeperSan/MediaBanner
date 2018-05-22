package com.creepersan.mediabanner.view

import android.content.Context
import android.nfc.FormatException
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.creepersan.mediabanner.view.bean.BaseBannerItem
import com.creepersan.mediabanner.view.bean.ImageBannerItem
import com.creepersan.mediabanner.view.bean.TextBannerItem
import com.creepersan.mediabanner.view.bean.VideoBannerItem
import com.creepersan.mediabanner.view.holder.BaseBannerHolder
import com.creepersan.mediabanner.view.holder.ImageBannerHolder
import com.creepersan.mediabanner.view.holder.TextBannerHolder
import com.creepersan.mediabanner.view.holder.VideoBannerHolder
import com.creepersan.mediabanner.view.util.Console
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Author      : gemvary
 * Time        : 2018-05-22 09:05
 * Description : 自定义控件
 */
class MediaBanner : FrameLayout, ViewPager.OnPageChangeListener{

    companion object {
        const val LOOP_NO = 0               // 到了尽头就不回来
        const val LOOP_LOOP = 1             // 到了尽头往上翻到第一张
        const val LOOP_LOOP_INFINITE = 2   // 到了尽头往下翻又是第一张
        const val LOOP_RANDOM = 3           // 随便跳

        const val AUTO_PLAY_NEGATIVE = 0    // 消极自动播放
        const val AUTO_PLAY_POSITIVE = 1    // 积极自动播放
        const val AUTO_PLAY_OFF = -1         // 不自动播放

        const val DEFAULT_AUTO_PLAY_DELAY = 3000L
    }

    private val mViewPager by lazy { ViewPager(context) }
    private val mViewPagerAdapter by lazy { MediaBannerPagerAdapter() }
    private val mBannerItemList by lazy { ArrayList<BaseBannerItem>() }
    private val mHolderPool by lazy { HashMap<String, LinkedList<BaseBannerHolder>>() }
    private var mBannerScrollListener : OnMediaBannerScrollListener? = null
    private var autoPlayTimer = Timer()
    private var autoPlayTimerTask = AutoPlayTimerTask()
    private var mLoopMode = LOOP_LOOP
    private var mAutoPlayMode = AUTO_PLAY_NEGATIVE
    private var mAutoPlayDelay = DEFAULT_AUTO_PLAY_DELAY
    private var isScrolling = false

    constructor(context: Context):super(context)
    constructor(context: Context, attributeSet: AttributeSet?):super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context,attributeSet,defStyleAttr)

    init {
        mViewPagerAdapter.notifyDataSetChanged()
        mViewPager.adapter = mViewPagerAdapter
        addView(mViewPager)
        mViewPager.addOnPageChangeListener(this)
        // 自动播放
        when(mAutoPlayMode){
            AUTO_PLAY_POSITIVE,
            AUTO_PLAY_NEGATIVE -> {
                autoPlayTimer.schedule(autoPlayTimerTask, mAutoPlayDelay, mAutoPlayDelay)
            }
            AUTO_PLAY_OFF -> {
                autoPlayTimerTask.cancel()
            }
        }
        setLoopMode(mAutoPlayMode)
    }

    /**
     *  对外暴露的设置方法
     */
    fun addItem(bannerItem: BaseBannerItem){
        mBannerItemList.add(bannerItem)
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun <E : BaseBannerItem>addItems(bannerItem: Array<E>){
        mBannerItemList.addAll(bannerItem.asList())
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun <E : BaseBannerItem>addItems(bannerItem: Collection<E>){
        mBannerItemList.addAll(bannerItem)
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun clearItems(){
        mViewPagerAdapter.notifyDataSetChanged()
    }

    fun setMediaBannerScrollListener(listener:OnMediaBannerScrollListener){
        mBannerScrollListener = listener
    }
    fun getMediaBannerScrollListener():OnMediaBannerScrollListener?{
        return mBannerScrollListener
    }

    fun setLoopMode(loopMode:Int){
        if (loopMode == mLoopMode) return
        when(loopMode){
            LOOP_NO -> {
                autoPlayTimerTask.cancel()
            }
            LOOP_LOOP -> {
                autoPlayTimerTask.cancel()
                autoPlayTimer.schedule(autoPlayTimerTask, mAutoPlayDelay, mAutoPlayDelay)
            }
            LOOP_RANDOM -> {
                autoPlayTimerTask.cancel()
                autoPlayTimer.schedule(autoPlayTimerTask, mAutoPlayDelay, mAutoPlayDelay)
            }
            else -> {
                Console.warming("不支持的循环模式 $loopMode")
            }
        }
        mLoopMode = loopMode
    }

    /**
     *  对外暴露的获取信息方法
     */
    fun getSize():Int{
        return mBannerItemList.size
    }
    fun getPosition():Int{
        return mViewPager.currentItem
    }

    /**
     *  对外暴露的操作方法
     */
    fun scrollTo(position: Int, animated:Boolean = true){
        mViewPager.setCurrentItem(position, animated)
    }
    fun scrollNext(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        mViewPager.setCurrentItem(( mViewPager.currentItem + 1 ) % mBannerItemList.size)
    }
    fun scrollPrevious(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        val currentPos = mViewPager.currentItem
        if (currentPos <= 0){
            mViewPager.setCurrentItem(mBannerItemList.size - 1)
        }else{
            mViewPager.setCurrentItem(currentPos - 1)
        }
    }
    fun scrollLast(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        mViewPager.setCurrentItem(mBannerItemList.size - 1, animated)
    }
    fun scrollFirst(animated:Boolean = true){
        mViewPager.setCurrentItem(0, animated)
    }

    /**
     *  内部设备刷新选项
     */
    private fun refreshAutoPlay(){

    }
    private fun refreshLoopMode(){

    }

    /**
     *  Holder的操作
     */
    private fun getHolderList(item:BaseBannerItem):LinkedList<BaseBannerHolder>{
        val key = item.javaClass.name
        return if (mHolderPool.containsKey(key)){
            mHolderPool[key] ?: LinkedList<BaseBannerHolder>().apply { mHolderPool.put(key, this) }
        }else{
            LinkedList<BaseBannerHolder>().apply { mHolderPool.put(key, this) }
        }
    }
    private inline fun <reified T : BaseBannerHolder> getHolder(holderList : LinkedList<BaseBannerHolder>, default:() -> T):T{
        holderList.forEach {
            if (it.isIdle() && it is T){
                return it
            }
        }
        return default().apply { holderList.add(this) }
    }
    fun getHolder(item:BaseBannerItem):BaseBannerHolder{
        val holderList = getHolderList(item)
        var holder : BaseBannerHolder? = null
        when(item){
            is ImageBannerItem  -> {
                holder = getHolder(holderList,{ ImageBannerHolder(context) })
                renderImage(item, holder)
            }
            is TextBannerItem -> {
                holder = getHolder(holderList,{ TextBannerHolder(context) })
                renderText(item, holder)
            }
            is VideoBannerItem -> {
                holder = getHolder(holderList,{ VideoBannerHolder(context) })
                renderVideo(item, holder)
            }
            else -> {
                throw FormatException("所需要展示的不是合法的Banner对象 ${item.javaClass.simpleName}")
            }
        }
        return holder
    }

    private fun renderImage(item:ImageBannerItem, holder:ImageBannerHolder){
        if (item.imageID == ImageBannerItem.EMPTY_IMAGE_ID){    // 如果没有ID，就读取文件
            holder.setImage(item.imagePath)
        }else{
            holder.setImageRes(item.imageID)
        }
    }
    private fun renderText(item:TextBannerItem, holder:TextBannerHolder){
        holder.setText(item.text)
    }
    private fun renderVideo(item:VideoBannerItem, holder:VideoBannerHolder){
        holder.play(item.video)
    }

    /**
     *  一些事件的回调
     */
    override fun onPageScrollStateChanged(state: Int) {
        when(state){
            ViewPager.SCROLL_STATE_IDLE -> { isScrolling = false }
            ViewPager.SCROLL_STATE_DRAGGING -> { isScrolling = true }
            ViewPager.SCROLL_STATE_SETTLING -> { isScrolling = true }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mBannerItemList[position].getOnStateChangeListener()?.onShow()  // 正在播放的
        mBannerScrollListener?.onPageChange(position)
        Console.log("onPageSelected($position)")
    }

    /**
     *  获取数据的一些方法
     */

    /**
     *  内部类
     */
    private inner class MediaBannerPagerAdapter : PagerAdapter(){
        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == (obj as BaseBannerHolder).getView()
        }

        override fun getCount(): Int {
            return mBannerItemList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val holder = getHolder(mBannerItemList[position])
            holder.setBusy()
            container.addView(holder.getView())
            mBannerItemList[position].getOnStateChangeListener()?.onCreate()   // 生命周期创建
            return holder
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            val holder = obj as BaseBannerHolder
            container.removeView(holder.getView())
            mBannerItemList[position].getOnStateChangeListener()?.onDestroy()   // 生命周期销毁
            holder.setIdle()
        }
    }
    private inner class AutoPlayTimerTask : TimerTask(){
        override fun run() {
            when(mAutoPlayMode){
                AUTO_PLAY_POSITIVE -> {
                    post { scrollNext() }
                }
                AUTO_PLAY_NEGATIVE -> {
                    if (!isScrolling){ post{ scrollNext() } }
                }
            }
        }
    }
    /**
     *  接口
     */

    interface OnMediaBannerScrollListener{
        fun onPageChange(position:Int)
    }

}