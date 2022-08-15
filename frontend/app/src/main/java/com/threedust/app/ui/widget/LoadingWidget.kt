package com.threedust.app.ui.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.threedust.app.R
import java.util.*

class LoadingWidget constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mFlashView: View? = null
    private var mLoad1: ImageView? = null
    private var mLoad2: ImageView? = null
    private var mLoad3: ImageView? = null
    private var mLoad4: ImageView? = null
    private var mLoad5: ImageView? = null
    private var mAnimatorSet: AnimatorSet? = null

    constructor(ctx: Context) : this(ctx, null)
    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    private fun initView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        mFlashView = LayoutInflater.from(context).inflate(R.layout.wg_loading, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mLoad1 = mFlashView!!.findViewById<View>(R.id.load1) as ImageView
        mLoad2 = mFlashView!!.findViewById<View>(R.id.load2) as ImageView
        mLoad3 = mFlashView!!.findViewById<View>(R.id.load3) as ImageView
        mLoad4 = mFlashView!!.findViewById<View>(R.id.load4) as ImageView
        mLoad5 = mFlashView!!.findViewById<View>(R.id.load5) as ImageView
        showLoading()
    }

    fun showLoading() {
        if (visibility != View.VISIBLE) return
        if (mAnimatorSet == null) initAnimation()
        if (mAnimatorSet!!.isRunning || mAnimatorSet!!.isStarted) return
        mAnimatorSet!!.start()
    }

    fun hideLoading() {
        if (mAnimatorSet == null) return
        if (!mAnimatorSet!!.isRunning && !mAnimatorSet!!.isStarted) return
        mAnimatorSet!!.removeAllListeners()
        mAnimatorSet!!.cancel()
        mAnimatorSet!!.end()
    }

    private fun initAnimation() {
        mAnimatorSet = AnimatorSet()
        val imageViewList: List<ImageView> =
            Arrays.asList(mLoad1!!, mLoad2!!, mLoad3!!, mLoad4!!, mLoad5!!)
        val animatorList: MutableList<Animator> = ArrayList()
        val duration = 100L
        imageViewList.forEachIndexed { index, imageView ->
            val loadAnimator =
                ObjectAnimator.ofFloat(imageView, "alpha", *floatArrayOf(1.0f, 0.2f))
                    .setDuration(duration * imageViewList.size)
            loadAnimator.startDelay = duration * index.toLong()
            loadAnimator.repeatMode = ObjectAnimator.REVERSE
            loadAnimator.repeatCount = -1
            animatorList.add(loadAnimator)
        }
        mAnimatorSet!!.playTogether(animatorList)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility != View.VISIBLE) hideLoading()
    }

    init {
        initView(context, attrs, defStyleAttr)
    }
}
