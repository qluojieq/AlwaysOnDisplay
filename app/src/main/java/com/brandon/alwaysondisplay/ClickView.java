package com.brandon.alwaysondisplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Brandon on 2021/5/7.
 * https://blog.csdn.net/u010887088/article/details/109640203
 **/
class ClickView extends View {
    private Paint mPaint;
    private int mCenterX;
    private int mCenterY;
    private int mRadius = 500;
    private int mLongLine = 60;
    private Path mPath;
    private Rect mTextBound;
    private int mHour;
    private int mMinute;
    private int mSecond;
    private Calendar mCalendar;
    public ClickView(Context context) {
        super(context);
        init(context);
    }


    public ClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public ClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mCenterX = outMetrics.widthPixels/2;
        mCenterY = outMetrics.widthPixels/2;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2.0f);
        mPaint.setColor(Color.WHITE);

        mTextBound = new Rect();
        mPath = new Path();
        mCalendar = Calendar.getInstance();

        mHour = mCalendar.get(Calendar.HOUR);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mSecond = mCalendar.get(Calendar.SECOND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        //画圆
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        //画刻度
        drawLine(canvas);
        //画数字
        drawNumber(canvas);
        //画圆心
        canvas.drawCircle(mCenterX, mCenterY, 10.0f, mPaint);
        //画Logo（option）
        //画指针
        drawTime(canvas);
        postDelayed(mRunnable, 1000);
    }

    /**
     * 画刻度
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.save();
        int startY = mCenterY - mRadius;
        int endLongY = startY + mLongLine;
        int endShortY = startY + mLongLine / 3;
        for (int i = 1; i <= 60; i++) {
            canvas.rotate(-6.0f, mCenterX, mCenterY);
            if (i % 5 == 0) {
                //长刻度
                canvas.drawLine(mCenterX, startY, mCenterX, endLongY, mPaint);
            } else {
                //短刻度
                canvas.drawLine(mCenterX, startY, mCenterX, endShortY, mPaint);
            }
        }
        canvas.restore();
    }

    /**
     * 画1-12个数字
     * @param canvas
     */
    private void drawNumber(Canvas canvas) {
        canvas.save();
        mPaint.setTextSize(50.0f);
        mPaint.setStyle(Paint.Style.FILL);
        float offsetY = 10.0f;
        for (int j = 1; j <= 12; j++) {
            mPaint.getTextBounds(String.valueOf(j), 0, String.valueOf(j).length(), mTextBound);
            canvas.rotate(30.0f * j);
            float textWidth = mTextBound.width();
            float textHeight = mTextBound.height();
            float translateY = mRadius - mLongLine - offsetY - textHeight/2;
            canvas.translate(0, -translateY);
            canvas.rotate(-30.0f * j);
            canvas.drawText(String.valueOf(j), -textWidth / 2.0f + mCenterX, mCenterY + textHeight/2, mPaint);
            canvas.rotate(30.0f * j);
            canvas.translate(0, translateY);
            canvas.rotate(-30.0f * j);
        }
        canvas.restore();
    }

    /**
     * 画时分秒
     * @param canvas
     */
    private void drawTime(Canvas canvas) {
        //画时针
        canvas.save();
        canvas.rotate(30.0f * mHour + 30.0f/60 * mMinute, mCenterX, mCenterY);
        mPath.reset();
        mPath.moveTo(mCenterX, mCenterY);
        mPath.lineTo(mCenterX+10.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY-250.0f);
        mPath.lineTo(mCenterX-10.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        //画分针
        canvas.save();
        canvas.rotate(6.0f * mMinute + 6.0f/60 *
                mSecond, mCenterX, mCenterY);
        mPath.rewind();
        mPath.moveTo(mCenterX, mCenterY);
        mPath.lineTo(mCenterX+5.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY-300.0f);
        mPath.lineTo(mCenterX-5.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        //画秒针
        canvas.save();
        canvas.rotate(6.0f * mSecond, mCenterX, mCenterY);
        mPath.rewind();
        mPath.moveTo(mCenterX, mCenterY+30.0f);
        mPath.lineTo(mCenterX+3.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY-400.0f);
        mPath.lineTo(mCenterX-3.0f, mCenterY-50.0f);
        mPath.lineTo(mCenterX, mCenterY+50.0f);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    /**
     * 开启表钟
     */
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            mHour = mCalendar.get(Calendar.HOUR);
            mMinute = mCalendar.get(Calendar.MINUTE);
            mSecond = mCalendar.get(Calendar.SECOND);
            postInvalidate();
        }
    };

}
