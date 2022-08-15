package com.threedust.app.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.threedust.app.R
import com.threedust.app.listener.OneArgListener
import com.threedust.app.utils.SysUtils
import com.threedust.app.utils.UIUtils

class ScrollPickerView @JvmOverloads constructor(
    @NonNull context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    RecyclerView(context, attrs, defStyle) {
    private var mSmoothScrollTask: Runnable? = null
    private var mItemHeight = 0
    private var mItemWidth = 0
    private var mInitialY = 0
    private var mFirstLineY = 0
    private var mSecondLineY = 0
    private var mFirstAmend = false

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        if (!mFirstAmend) {
            mFirstAmend = true
            (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                itemSelectedOffset, 0
            )
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        freshItemView()
    }

    private val scrollYDistance: Int
        private get() {
            val layoutManager: LinearLayoutManager = this.getLayoutManager() as LinearLayoutManager?
                ?: return 0
            val position: Int = layoutManager.findFirstVisibleItemPosition()
            val firstVisibleChildView: View = layoutManager.findViewByPosition(position)
                ?: return 0
            val itemHeight = firstVisibleChildView.height
            return position * itemHeight - firstVisibleChildView.top
        }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_UP) {
            processItemOffset()
        }
        return super.onTouchEvent(e)
    }

    protected override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        var widthSpec = widthSpec
        var heightSpec = heightSpec
        widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        heightSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, heightSpec)
        measureSize()
        setMeasuredDimension(mItemWidth, mItemHeight * visibleItemNumber)
    }

    private fun measureSize() {
        if (getChildCount() > 0) {
            if (mItemHeight == 0) {
                mItemHeight = getChildAt(0).getMeasuredHeight()
            }
            if (mItemWidth == 0) {
                mItemWidth = getChildAt(0).getMeasuredWidth()
            }
            if (mFirstLineY == 0 || mSecondLineY == 0) {
                mFirstLineY = mItemHeight * itemSelectedOffset
                mSecondLineY = mItemHeight * (itemSelectedOffset + 1)
            }
        }
    }

    private fun processItemOffset() {
        mInitialY = scrollYDistance
        postDelayed(mSmoothScrollTask, 30)
    }

    private fun initTask() {
        mSmoothScrollTask = Runnable {
            val newY = scrollYDistance
            if (mInitialY != newY) {
                mInitialY = scrollYDistance
                postDelayed(mSmoothScrollTask, 30)
            } else if (mItemHeight > 0) {
                val offset = mInitialY % mItemHeight //离选中区域中心的偏移量
                if (offset == 0) {
                    return@Runnable
                }
                if (offset >= mItemHeight / 2) { //滚动区域超过了item高度的1/2，调整position的值
                    smoothScrollBy(0, mItemHeight - offset)
                } else if (offset < mItemHeight / 2) {
                    smoothScrollBy(0, -offset)
                }
            }
        }
    }

    private val visibleItemNumber: Int
        private get() {
            return 3
        }
    private val itemSelectedOffset: Int
        private get() {
            return 1
        }

    private fun updateView(itemView: View, isSelected: Boolean) {
        (adapter as ScrollPickerAdapter<*>).updateView(itemView, isSelected)
    }

    private fun freshItemView() {
        for (i in 0 until childCount) {
            val itemViewY: Float = getChildAt(i).getTop() + mItemHeight / 2f
            updateView(getChildAt(i), mFirstLineY < itemViewY && itemViewY < mSecondLineY)
        }
    }

    init {
        initTask()
    }

    /**
     * provider class
     */
    class ItemViewProvider {
        fun resLayout(): Int = R.layout.wg_picker_item_view
        fun onBindView(view: View, text: String?) {
            val tv = view.findViewById<TextView>(R.id.tv_content)
            tv.text = text
            view.tag = text
            tv.textSize = 40f
        }
        fun updateView(itemView: View, isSelected: Boolean) {
            val tv = itemView.findViewById<TextView>(R.id.tv_content)
            tv.textSize = if (isSelected) 40f else 32f
            val color = if (isSelected) "#303033" else "#d6d6d6"
            tv.setTextColor(Color.parseColor(color))
        }
    }

    /**
     *  holder class
     */
    class ScrollPickerAdapterHolder constructor(@NonNull view: View) : ViewHolder(view) {
    }

    /**
     *  adapter class
     */
    class ScrollPickerAdapter<T>(context: Context) :
        RecyclerView.Adapter<ScrollPickerAdapterHolder>() {

        var mDataList: ArrayList<T?> = ArrayList()
        val mContext: Context = context
        var mOnItemClickListener: OneArgListener? = null
        var mOnScrollListener: OneArgListener? = null
        var mSelectedItemOffset = 0
        var mVisibleItemNum = 3
        lateinit var mViewProvider: ItemViewProvider
        var maxItemH = 0
        var maxItemW = 0

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ScrollPickerAdapterHolder {
            mViewProvider = ItemViewProvider()
            return ScrollPickerAdapterHolder(LayoutInflater.from(mContext).inflate(mViewProvider.resLayout(), parent, false))
        }

        override fun onBindViewHolder(holder: ScrollPickerAdapterHolder, position: Int) {
            mViewProvider.onBindView(holder.itemView, mDataList[position] as? String)
        }

        override fun getItemCount(): Int {
            return mDataList.size
        }

        fun updateView(itemView: View, isSelected: Boolean) {
            mViewProvider.updateView(itemView, isSelected)
            adaptiveItemViewSize(itemView)
            if (isSelected) {
                mOnScrollListener?.trigger(itemView)
            }
            itemView.setOnClickListener { v ->
                mOnItemClickListener?.trigger(v)
            }
        }

        private fun adaptiveItemViewSize(itemView: View) {
            if (itemView.height > maxItemH) maxItemH = itemView.height
            if (itemView.width > maxItemW) maxItemW = itemView.width
            itemView.minimumHeight = maxItemH
            itemView.minimumWidth = maxItemW
        }
    }

    /**
     * adapter builder
     */
    class ScrollPickerAdapterBuilder<T>(context: Context) {
        var mAdapter : ScrollPickerAdapter<T>

        init {
            mAdapter = ScrollPickerAdapter(context)
        }

        fun selectedItemOffset(offset: Int) : ScrollPickerAdapterBuilder<T> {
            mAdapter.mSelectedItemOffset = offset
            return this
        }

        fun setDataList(list: List<T>) : ScrollPickerAdapterBuilder<T> {
            mAdapter.mDataList.clear()
            mAdapter.mDataList.addAll(list)
            return this
        }

        fun setOnClickListener(listener: OneArgListener) : ScrollPickerAdapterBuilder<T> {
            mAdapter.mOnItemClickListener = listener
            return this
        }

        fun setOnScrolledListener(listener: OneArgListener) : ScrollPickerAdapterBuilder<T> {
            mAdapter.mOnScrollListener = listener
            return this
        }

        private fun adaptiveData(list: ArrayList<T?>) {
            val visibleItemNum = mAdapter.mVisibleItemNum
            val selectedItemOffset = mAdapter.mSelectedItemOffset
            for (i in 0 until mAdapter.mSelectedItemOffset) {
                list.add(0, null)
            }
            for (i in 0 until visibleItemNum - selectedItemOffset - 1) {
                list.add(null)
            }
        }

        fun build() : ScrollPickerAdapter<T> {
            adaptiveData(mAdapter.mDataList)
            mAdapter.notifyDataSetChanged()
            return mAdapter
        }
    }
}
