package com.threedust.app.ui.widget

import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.threedust.app.R
import com.threedust.app.utils.Logger
import com.threedust.app.utils.UIUtils

class CircularTimerView : View {
    private var mContext: Context
    private lateinit var mPaint: Paint
    private var mProgress = 0

    /**
     * angle
     */
    private var mAngle = 360f

    private var mScale = 100

    /**
     * text in middle part
     */
    private lateinit var mText: String

    /**
     * outside circle
     */
    private var outRoundColor = 0

    /**
     * inter circle
     */
    private var inRoundColor = 0

    /**
     * width of round outter / inner
     */
    private var roundWidth = 0
    private var outterCircleWidth = 0

    /**
     *  color of text
     */
    private var textColor = 0

    /**
     *
     */
    private var textSize = 0f

    /**
     * bold
     */
    private var isBold = false

    /**
     * color of progress
     */
    private var progressBarColor = 0

    // time
    private var secondCount = 0

    private var valueAnimator : ValueAnimator? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        init(attrs)
    }

    @TargetApi(21)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        mContext = context
        init(attrs)
    }

    /**
     * 解析自定义属性
     *
     * @param attrs
     */
    fun init(attrs: AttributeSet?) {
        mPaint = Paint()
        outRoundColor = Color.WHITE
        inRoundColor = resources.getColor(R.color.bg_light_gray)
        progressBarColor = resources.getColor(R.color.pri_green)
        isBold = true
        textColor = resources.getColor(R.color.text_dark)
        outterCircleWidth = 15
        roundWidth = 40
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val center = width / 2
        var radius = center
        mPaint.color = outRoundColor
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.isAntiAlias = true
        canvas.drawCircle(center.toFloat(), center.toFloat(), radius.toFloat(), mPaint)

        mPaint.style = Paint.Style.STROKE
        mPaint.color = inRoundColor
        radius = radius - outterCircleWidth - roundWidth / 2
        mPaint.strokeWidth = roundWidth.toFloat()
        canvas.drawCircle(center.toFloat(), center.toFloat(), radius.toFloat(), mPaint)

        mPaint.color = progressBarColor
        val rectF = RectF(
            (center - radius).toFloat(),
            (center - radius).toFloat(),
            (center + radius).toFloat(),
            (center + radius).toFloat()
        )
        // draw angle
        val startAngle = 90f
        mPaint.strokeCap = Paint.Cap.ROUND
        val oldShader = mPaint.shader
        val shader = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            resources.getColor(R.color.light_green), resources.getColor(R.color.dark_green),
            Shader.TileMode.MIRROR
        )
        mPaint.shader = shader
        canvas.drawArc(rectF, -startAngle, mAngle, false, mPaint)
        mPaint.shader = oldShader
        mPaint.style = Paint.Style.FILL

        // end angle dot
        if (mAngle != 0f && mAngle != 360f) {
            canvas.save()
            canvas.translate(center.toFloat(), center.toFloat())
            canvas.rotate(mAngle - startAngle)
            canvas.translate(radius.toFloat(), 0f)
            mPaint.color = Color.WHITE
            canvas.drawCircle(0f, 0f, roundWidth.toFloat() / 3.5f, mPaint)
            canvas.restore()
        }

        // draw text
        val minute = secondCount / 60
        val second = secondCount % 60
        var minuteText = minute.toString()
        var secondText = second.toString()
        if (minute < 10) minuteText = "0$minuteText"
        if (second < 10) secondText = "0$secondText"

        canvas.translate(center.toFloat(), center.toFloat())
        mPaint.strokeWidth = 0f
        mPaint.color = textColor

        mPaint.setTypeface(Typeface.DEFAULT_BOLD)
        var bigTextSize = UIUtils.sp2px(40).toFloat()
        mPaint.textSize = bigTextSize
        var bigTextWidth = mPaint.measureText(minuteText)
        var smallTextSize = UIUtils.sp2px(18).toFloat()
        mPaint.textSize = smallTextSize
        var smallTextWidth = mPaint.measureText("m")

        val baseLine = bigTextSize / 3f
        mPaint.textSize = bigTextSize
        canvas.drawText(minuteText, -bigTextWidth - smallTextWidth * 1.2f,  baseLine, mPaint)

        mPaint.textSize = smallTextSize
        canvas.drawText("m", -smallTextWidth * 0.9f,  baseLine, mPaint)

        mPaint.textSize = bigTextSize
        canvas.drawText(secondText, smallTextWidth * 0.8f,  baseLine, mPaint)

        mPaint.textSize = smallTextSize
        canvas.drawText("s", bigTextWidth + smallTextWidth * 1.2f,  baseLine, mPaint)
    }

    /**
     * @param p
     */
    fun setSecondCountDown(startF: ()->Unit,  endF: ()->Unit) {
        if (mProgress < 1) return
        //设置属性动画
        valueAnimator = ValueAnimator.ofInt(0, mProgress)
        //动画从快到慢
        //valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator!!.interpolator = LinearInterpolator()
        valueAnimator!!.duration = secondCount.toLong() * 1000
        //监听值的变化
        startF()
        valueAnimator!!.addUpdateListener { animation ->
            val currentV = animation.animatedValue as Int
            mAngle = 360f * currentV / mProgress
            //if (mAngle < 1) mAngle = 1f
            secondCount = (mProgress - currentV + mScale - 1) / mScale
//            Logger.d(mAngle)
            invalidate()
            if (currentV == mProgress) {
                endF()
            }
        }
        valueAnimator!!.start()
    }

    fun setMinute(minute: Int) {
        valueAnimator?.removeAllUpdateListeners()
        valueAnimator?.cancel()
        mProgress = minute * 60 * mScale
        if (minute < 1) return
        secondCount = minute * 60
        invalidate()
    }

    fun pause() {
        valueAnimator?.pause()
    }

    fun resume() {
        valueAnimator?.resume()
    }

    fun stop() {
        mProgress = 0
        secondCount = 0
        mAngle = 360f
        valueAnimator?.removeAllUpdateListeners()
        valueAnimator?.cancel()
        invalidate()
    }

    companion object {
        private const val MAX_PROGRESS = 100
    }
}