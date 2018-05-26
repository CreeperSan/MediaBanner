package com.creepersan.mediabannerview

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.creepersan.mediabannerview.`interface`.ImageLoadInterface
import com.creepersan.mediabannerview.holder.BaseBannerHolder
import com.creepersan.mediabannerview.item.BaseBannerItem
import com.creepersan.mediabannerview.utils.Console
import java.lang.IllegalStateException
import java.util.*

/**
 * Author      : CreeperSan
 * Time        : 2018-05-22 09:05
 * Description : 自定义控件
 */
class MediaBannerView : FrameLayout, ViewPager.OnPageChangeListener, View.OnClickListener{

    companion object {
        const val LOOP_NO = 0               // 到了尽头就不回来
        const val LOOP_LOOP = 1             // 到了尽头往上翻到第一张
        const val LOOP_RANDOM = 3           // 随便跳

        const val AUTO_PLAY_NEGATIVE = 0    // 消极自动播放
        const val AUTO_PLAY_POSITIVE = 1    // 积极自动播放
        const val AUTO_PLAY_OFF = -1         // 不自动播放

        const val DEFAULT_AUTO_PLAY_DELAY = 3000L

        var imageLoader : ImageLoadInterface? = null    // 全剧图片加载器

    }

    private val mViewPager by lazy { ViewPager(context) }
    private val mViewPagerAdapter by lazy { MediaBannerPagerAdapter() }
    private val mBannerItemList by lazy { ArrayList<BaseBannerItem<*>>() }
    private val mHolderPool by lazy { HashMap<String, LinkedList<BaseBannerHolder>>() }
    private var mBannerScrollListener : OnBannerScrollListener? = null
    private var mBannerClickListener : OnBannerClickListener? = null
    private var autoPlayTimer = Timer()
    private var autoPlayTimerTask = AutoPlayTimerTask()
    private val mBannerController by lazy { MediaBannerController() }
    private val mRandom by lazy { Random() }

    private var mLoopMode = LOOP_LOOP
    private var mAutoPlayMode = AUTO_PLAY_NEGATIVE
    private var mAutoPlayDelay = DEFAULT_AUTO_PLAY_DELAY
    private var isScrolling = false
    private var isCanNextFlag = false

    constructor(context: Context):super(context)
    constructor(context: Context, attributeSet: AttributeSet?):super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context,attributeSet,defStyleAttr)

    init {
        mViewPagerAdapter.notifyDataSetChanged()
        mViewPager.adapter = mViewPagerAdapter
        addView(mViewPager)
        mViewPager.addOnPageChangeListener(this)
        // 自动播放
        refreshAutoPlay()
    }

    /**
     *  对外暴露的设置方法
     */
    fun addItem(bannerItem: BaseBannerItem<*>){
        mBannerItemList.add(bannerItem)
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun <E : BaseBannerItem<*>>addItems(bannerItem: Array<E>){
        mBannerItemList.addAll(bannerItem.asList())
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun <E : BaseBannerItem<*>>addItems(bannerItem: Collection<E>){
        mBannerItemList.addAll(bannerItem)
        mViewPagerAdapter.notifyDataSetChanged()
    }
    fun clearItems(){
        mViewPagerAdapter.notifyDataSetChanged()
    }

    fun setOnBannerScrollListener(listener: OnBannerScrollListener){
        mBannerScrollListener = listener
    }
    fun getOnBannerScrollListener(): OnBannerScrollListener?{
        return mBannerScrollListener
    }
    fun setOnBannerClickListener(listener: OnBannerClickListener){
        mBannerClickListener = listener
    }
    fun getOnBannerClickListener(): OnBannerClickListener?{
        return mBannerClickListener
    }

    fun setCanNextFlag(flag:Boolean){
        isCanNextFlag = flag
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
    fun getAutoPlayDelay():Long{
        return mAutoPlayDelay
    }
    fun getAutoPlayMode():Int {
        return mAutoPlayMode
    }
    fun isScrolling():Boolean{
        return isScrolling
    }
    /**
     *  对外暴露的操作方法
     */
    fun scrollTo(position: Int, animated:Boolean = true){
        mViewPager.setCurrentItem(position, animated)
    }
    fun scrollNext(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        when(mLoopMode){
            LOOP_NO -> { if (mViewPager.currentItem < mBannerItemList.size-1)mViewPager.setCurrentItem(( mViewPager.currentItem + 1 ) % mBannerItemList.size, animated) }
            LOOP_LOOP -> { mViewPager.setCurrentItem(( mViewPager.currentItem + 1 ) % mBannerItemList.size, animated) }
            LOOP_RANDOM -> { mViewPager.setCurrentItem( mRandom.nextInt(mBannerItemList.size), animated ) }
        }
        Console.log("ScrollNext() ${getPosition()}      ${System.currentTimeMillis()}")
    }
    fun scrollPrevious(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        val currentPos = mViewPager.currentItem
        when(mLoopMode){
            LOOP_NO -> {
                if (currentPos > 0) {
                    mViewPager.setCurrentItem(currentPos - 1, animated)
                }
            }
            LOOP_LOOP -> {
                if (currentPos <= 0) {
                    mViewPager.setCurrentItem(mBannerItemList.size - 1, animated)
                } else {
                    mViewPager.setCurrentItem(currentPos - 1, animated)
                }
            }
            LOOP_RANDOM -> {
                scrollNext(animated)
            }
        }

    }
    fun scrollLast(animated:Boolean = true){
        if (mBannerItemList.size <= 0) return
        mViewPager.setCurrentItem(mBannerItemList.size - 1, animated)
    }
    fun scrollFirst(animated:Boolean = true){
        mViewPager.setCurrentItem(0, animated)
    }

    fun setLoopMode(loopMode : Int){
        this.mLoopMode = loopMode
    }
    fun setAutoPlay(autoPlayMode : Int){
        this.mAutoPlayMode = autoPlayMode
        refreshAutoPlay()
    }
    fun setAutoPlayDelay(delayTime : Long){
        mAutoPlayDelay = delayTime
        refreshAutoPlay()
    }
    fun setAutoPlayDelay(delayTime : Int){
        setAutoPlayDelay(delayTime.toLong())
    }

    /**
     *  内部设备刷新选项
     */
    private fun refreshAutoPlay(){
        when(mAutoPlayMode){
            AUTO_PLAY_OFF -> {
                autoPlayTimerTask.cancelTask()
            }
            AUTO_PLAY_NEGATIVE,
            AUTO_PLAY_POSITIVE -> {
                autoPlayTimerTask.cancelTask()
                autoPlayTimer.schedule(autoPlayTimerTask, mAutoPlayDelay, mAutoPlayDelay)
            }
        }
    }

    /**
     *  Holder的操作
     */
    private fun getHolderList(item:BaseBannerItem<*>): LinkedList<BaseBannerHolder> {
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
    fun getHolder(item:BaseBannerItem<*>):BaseBannerHolder{
        val holderList = getHolderList(item)
        val holder = getHolder<BaseBannerHolder>(holderList, { item.getHolder(context) })
        renderBaseClick(item, holder)
        item.rawBindHolder(context, holder)
        return holder
    }

    private fun renderBaseClick(item:BaseBannerItem<*>, holder:BaseBannerHolder){
        // 能够响应点击事件的View
        val view = holder.getView()
        // 点击触发的 Listener
        val listener = if (item.isUseDefaultClickEvent) {
            this
        } else {
            null
        }

        holder.getGlobalClickableView().setOnClickListener( listener )
    }


    /**
     *  一些事件的回调
     */
    override fun onPageScrollStateChanged(state: Int) {
        when(state){
            ViewPager.SCROLL_STATE_IDLE -> { isScrolling = false;if (isCanNextFlag){
                isCanNextFlag = false;
                scrollNext()
            } }
            ViewPager.SCROLL_STATE_DRAGGING -> { isScrolling = true }
            ViewPager.SCROLL_STATE_SETTLING -> { isScrolling = true }
        }
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }
    override fun onPageSelected(position: Int) {
        val item = mBannerItemList[position]
        val posPrev = position - 1
        val posNext = position + 1
        actionWhenPositionValid(posPrev, -1, mBannerItemList.size, { mBannerItemList[posPrev].parseHolder(BaseBannerItem.ON_PREPARE, this, posPrev, mBannerController) })
        actionWhenPositionValid(posNext, -1, mBannerItemList.size, { mBannerItemList[posNext].parseHolder(BaseBannerItem.ON_PREPARE, this, posNext, mBannerController) })
        item.parseHolder(BaseBannerItem.ON_SHOW, this, position, mBannerController)
        mBannerScrollListener?.onPageChange(position)
        isCanNextFlag = false
    }

    override fun onClick(v: View?) {
        if (mBannerClickListener == null) return
        val position = getPosition()
        mBannerClickListener?.onClick(position, mBannerItemList[position])
    }


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
            val item = mBannerItemList[position]
            val holder = getHolder(item)
            holder.setBusy()
            container.addView(holder.getView())
            item.parseHolder(BaseBannerItem.ON_CREATE, this@MediaBannerView, position, mBannerController, holder)
            return holder
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            val item = mBannerItemList[position]
            val holder = obj as BaseBannerHolder
            item.parseHolder(BaseBannerItem.ON_DESTROY, this@MediaBannerView, position, mBannerController, holder)
            container.removeView(holder.getView())
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
                    if (!isScrolling && mBannerItemList[getPosition()].isUseDefaultAutoPlay){ post{ scrollNext() } }
                }
            }
        }
    }
    inner class MediaBannerController{

        fun requireNext(position:Int){
            Console.log("请求下一次 ${System.currentTimeMillis()}")
            val item = mBannerItemList[position]
            if (mAutoPlayMode == AUTO_PLAY_NEGATIVE && !item.isUseDefaultAutoPlay){
                Console.log("准许下一次 ${System.currentTimeMillis()}")
                if (isScrolling){
                    isCanNextFlag = true
                }else{
                    scrollNext()
                }
            }
        }

    }

    /**
     *  接口
     */

    interface OnBannerScrollListener{
        fun onPageChange(position:Int)
    }
    interface OnBannerClickListener{
        fun onClick(position: Int, item:BaseBannerItem<*>)
    }

    /**
     *  拓展方法
     */
    private fun TimerTask.cancelTask(exceptionHandel:(()->Unit)? = null){
        try {
            autoPlayTimerTask.cancel()
            autoPlayTimerTask = AutoPlayTimerTask()
        }catch (e: IllegalStateException){
            exceptionHandel?.invoke()
        }
    }

    /**
     *  快捷操作
     */
    private inline fun actionWhenPositionValid(position:Int, min:Int, max:Int, action:()->Unit){
        if (position < max && position > min){
            action()
        }
    }
}