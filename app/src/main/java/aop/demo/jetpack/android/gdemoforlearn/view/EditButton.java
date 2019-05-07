package aop.demo.jetpack.android.gdemoforlearn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import aop.demo.jetpack.android.gdemoforlearn.R;

/**
 * =======================================
 * 描    述：拍照或录制完成后弹出的确认和返回按钮
 * =======================================
 */
public class EditButton extends View {
    public static final int TYPE_CANCEL = 0x001;
    public static final int TYPE_CONFIRM = 0x002;
    private int button_type;
    private int button_size = 100;

    private float center_X;
    private float center_Y;
    private float button_radius;

    private Paint mPaint;
    private Path path;
    private float strokeWidth;

    private float index;
    private RectF rectF;
    private Bitmap mBitmap;

    public EditButton(Context context) {
        super(context);
    }

    public EditButton(Context context, int size) {
        super(context);
        button_size = size;
        button_radius = size / 2.0f;
        center_X = size / 2.0f;
        center_Y = size / 2.0f;

        mPaint = new Paint();
        path = new Path();
        strokeWidth = size / 50f;
        index = button_size / 12f;
        rectF = new RectF(center_X, center_Y - index + 7, center_X + index * 2, center_Y + index + 7);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiaozheng);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(button_size, button_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xEEDCDCDC);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(center_X, center_Y, button_radius, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
//
//            path.moveTo(center_X - index / 7, center_Y + index+7);
//            path.lineTo(center_X + index, center_Y + index+7);
//
//            path.arcTo(rectF, 90, -180);
//            path.lineTo(center_X - index, center_Y - index+7);
//            canvas.drawPath(path, mPaint);
//            mPaint.setStyle(Paint.Style.FILL);
//            path.reset();
//            path.moveTo(center_X - index, (float) (center_Y - index * 1.5+7));
//            path.lineTo(center_X - index, (float) (center_Y - index / 2.3+7));
//            path.lineTo((float) (center_X - index * 1.6), center_Y - index+7);
//            path.close();
//            canvas.drawPath(path, mPaint);

        canvas.drawBitmap(mBitmap, center_X - (mBitmap.getWidth() >> 1),
                center_Y - (mBitmap.getHeight() >> 1), null);

    }
}
