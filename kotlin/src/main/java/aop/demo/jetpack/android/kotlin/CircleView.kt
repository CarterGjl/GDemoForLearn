package aop.demo.jetpack.android.kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class CircleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var mRadius: Float = 0.toFloat()
    private var mOuterCircleColor: Int = 0
    private var mMiddleCircleColor: Int = 0
    private var mInnerCircleColor: Int = 0
    private var mPaint: Paint

    init {
        //1. 自定义属性的声明与获取
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        mRadius = typedArray.getDimension(R.styleable.CircleView_circle_radius, resources.getDimension(R.dimen.avatar_size))
        mOuterCircleColor = typedArray.getColor(R.styleable.CircleView_outer_circle_color,
                ContextCompat.getColor(context, R.color.purple_500)
        )

        mMiddleCircleColor = typedArray.getColor(R.styleable.CircleView_middle_circle_color,
                ContextCompat.getColor(context, R.color.purple_500)
        )
        mInnerCircleColor = typedArray.getColor(R.styleable.CircleView_inner_circle_color,
                ContextCompat.getColor(context, R.color.purple_500))
        typedArray.recycle()

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL
        mPaint.color = mOuterCircleColor

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint.color = mOuterCircleColor
        canvas?.drawCircle(mRadius, mRadius, mRadius, mPaint)
        mPaint.color = mMiddleCircleColor
        canvas?.drawCircle(mRadius, mRadius, mRadius * 2 / 3, mPaint)
        mPaint.color = mInnerCircleColor
        canvas?.drawCircle(mRadius, mRadius, mRadius / 3, mPaint)
        mPaint.textSize = 50F
        mPaint.color = ContextCompat.getColor(context,R.color.colorLine)

        canvas?.drawText("Carter love", mRadius,mRadius,mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //2.1 根据 View 特点或业务需求计算出 View 的尺寸
        mWidth = (mRadius * 2).toInt()
        mHeight = (mRadius * 2).toInt()

        //2.2 通过 resolveSize() 方法修正结果
        mWidth = resolveSize(mWidth, widthMeasureSpec)
        mHeight = resolveSize(mHeight, heightMeasureSpec)

        //2.3 通过 setMeasuredDimension() 保存 View 的期望尺寸（通过 setMeasuredDimension() 告知父 View 的期望尺寸）
        setMeasuredDimension(mWidth, mHeight)

    }
}