package com.bytedance.camp.chapter4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClockView extends View {

    private static final int FULL_CIRCLE_DEGREE = 360;
    private static final int UNIT_DEGREE = 6;

    private static final float UNIT_LINE_WIDTH = 8; // 刻度线的宽度
    private static final int HIGHLIGHT_UNIT_ALPHA = 0xFF;
    private static final int NORMAL_UNIT_ALPHA = 0x80;

    private static final float HOUR_NEEDLE_LENGTH_RATIO = 0.4f; // 时针长度相对表盘半径的比例
    private static final float MINUTE_NEEDLE_LENGTH_RATIO = 0.6f; // 分针长度相对表盘半径的比例
    private static final float SECOND_NEEDLE_LENGTH_RATIO = 0.8f; // 秒针长度相对表盘半径的比例
    private static final float HOUR_NEEDLE_WIDTH = 12; // 时针的宽度
    private static final float MINUTE_NEEDLE_WIDTH = 8; // 分针的宽度
    private static final float SECOND_NEEDLE_WIDTH = 4; // 秒针的宽度

    private Calendar calendar = Calendar.getInstance();
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    private float radius = 0; // 表盘半径
    private float centerX = 0; // 表盘圆心X坐标
    private float centerY = 0; // 表盘圆心Y坐标

    private List<RectF> unitLinePositions = new ArrayList<>();
    private Paint unitPaint = new Paint();
    private Paint needlePaint = new Paint();
    private Paint numberPaint = new Paint();
    private Runnable clockRunnable;

    private Handler handler = new Handler();

    private boolean isRunning = false;


    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        unitPaint.setAntiAlias(true);
        unitPaint.setColor(Color.WHITE);
        unitPaint.setStrokeWidth(UNIT_LINE_WIDTH);
        unitPaint.setStrokeCap(Paint.Cap.ROUND);
        unitPaint.setStyle(Paint.Style.STROKE);

        // TODO 设置绘制时、分、秒针的画笔: needlePaint

        needlePaint.setAntiAlias(true);
        needlePaint.setColor(Color.WHITE);
        needlePaint.setStrokeWidth(UNIT_LINE_WIDTH);
        needlePaint.setStrokeCap(Paint.Cap.ROUND);
        needlePaint.setStyle(Paint.Style.STROKE);


        // TODO 设置绘制时间数字的画笔: numberPaint

        numberPaint.setAntiAlias(true);
        numberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        numberPaint.setTextSize(50);
        numberPaint.setColor(Color.WHITE);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        clockRunnable = new Runnable() {//里面做的事情就是每隔一秒，刷新一次界面
            @Override
            public void run() {
                //线程中刷新界面
                invalidate();
                //postInvalidate(); 也可以，因为都在主线程
                handler.postDelayed(this, 1000);
            }
        };

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        configWhenLayoutChanged();
    }

    private void configWhenLayoutChanged() {
        float newRadius = Math.min(getWidth(), getHeight()) / 2f;
        if (newRadius == radius) {
            return;
        }
        radius = newRadius;
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;

        // 当视图的宽高确定后就可以提前计算表盘的刻度线的起止坐标了
        for (int degree = 0; degree < FULL_CIRCLE_DEGREE; degree += UNIT_DEGREE) {
            double radians = Math.toRadians(degree);
            float startX = (float) (centerX + (radius * (1 - 0.05f)) * Math.cos(radians));
            float startY = (float) (centerX + (radius * (1 - 0.05f)) * Math.sin(radians));
            float stopX = (float) (centerX + radius * Math.cos(radians));
            float stopY = (float) (centerY + radius * Math.sin(radians));
            unitLinePositions.add(new RectF(startX, startY, stopX, stopY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawUnit(canvas);
        //drawTimeNeedles(canvas);
        drawTimeNumbers(canvas);
        // TODO 实现时间的转动，每一秒刷新一次
        // ↓ add ?
        //handler.sendEmptyMessage(0);
        if(!isRunning){
            runClock();
        }else{
            drawTimeNeedles(canvas);
        }
    }

    // 绘制表盘上的刻度
    private void drawUnit(Canvas canvas) {
        for (int i = 0; i < unitLinePositions.size(); i++) {
            if (i % 5 == 0) {
                unitPaint.setAlpha(HIGHLIGHT_UNIT_ALPHA);
            } else {
                unitPaint.setAlpha(NORMAL_UNIT_ALPHA);
            }
            RectF linePosition = unitLinePositions.get(i);
            canvas.drawLine(linePosition.left, linePosition.top, linePosition.right, linePosition.bottom, unitPaint);
        }
    }

    private void drawTimeNeedles(Canvas canvas) {
        Time time = getCurrentTime();
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();

        Log.d("timekk","------" + hour + ":" + minute + ":" + second + "------");
        // TODO 根据当前时间，绘制时针、分针、秒针
        /**
         * 思路：
         * 1、以时针为例，计算从0点（12点）到当前时间，时针需要转动的角度
         * 2、根据转动角度、时针长度和圆心坐标计算出时针终点坐标（起始点为圆心）
         * 3、从圆心到终点画一条线，此为时针
         * 注1：计算时针转动角度时要把时和分都得考虑进去
         * 注2：计算坐标时需要用到正余弦计算，请用Math.sin()和Math.cos()方法
         * 注3：Math.sin()和Math.cos()方法计算时使用不是角度而是弧度，所以需要先把角度转换成弧度，
         *     可以使用Math.toRadians()方法转换，例如Math.toRadians(180) = 3.1415926...(PI)
         * 注4：Android视图坐标系的0度方向是从圆心指向表盘3点方向，指向表盘的0点时是-90度或270度方向，要注意角度的转换
         */
        // int hourDegree = 180;
        // float endX = (float) (centerX + radius * HOUR_NEEDLE_LENGTH_RATIO * Math.cos(Math.toRadians(hourDegree)))


        float XforHour = (float)Math.cos(Math.toRadians((hour+minute/60.0+second/3600.0)*30+270))*radius*HOUR_NEEDLE_LENGTH_RATIO + centerX;
        float YforHour = (float)Math.sin(Math.toRadians((hour+minute/60.0+second/3600.0)*30+270))*radius*HOUR_NEEDLE_LENGTH_RATIO + centerY;


        float XforMinute = (float)Math.cos(Math.toRadians((minute+second/60.0)*6+270))*radius*MINUTE_NEEDLE_LENGTH_RATIO + centerX;
        float YforMinute = (float)Math.sin(Math.toRadians((minute+second/60.0)*6+270))*radius*MINUTE_NEEDLE_LENGTH_RATIO + centerY;


        float XforSecond = (float)Math.cos(Math.toRadians(second*6+270))*radius*SECOND_NEEDLE_LENGTH_RATIO + centerX;
        float YforSecond = (float)Math.sin(Math.toRadians(second*6+270))*radius*SECOND_NEEDLE_LENGTH_RATIO + centerY;

        needlePaint.setStrokeWidth(HOUR_NEEDLE_WIDTH);
        canvas.drawLine(centerX,centerY,XforHour,YforHour,needlePaint);

        needlePaint.setStrokeWidth(MINUTE_NEEDLE_WIDTH);
        canvas.drawLine(centerX,centerY,XforMinute,YforMinute,needlePaint);

        needlePaint.setStrokeWidth(SECOND_NEEDLE_WIDTH);
        canvas.drawLine(centerX,centerY,XforSecond,YforSecond,needlePaint);

    }

    private void drawTimeNumbers(Canvas canvas) {
        // TODO 绘制表盘时间数字（可选）
        numberPaint.getFontMetrics(fontMetrics);
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        // 粗糙绘制
        for (int i = 0; i < 12; i++) {
            float hourDegree = i * 30 - 60;
            String number = String.valueOf(i + 1);
            float textWidth = numberPaint.measureText(number);
            canvas.drawText(
                    number,
                    (float) (centerX + (radius * 0.8f + textWidth / 2f) * Math.cos(Math.toRadians(hourDegree))),
                    (float) (centerY + (radius * 0.8f + textHeight / 2f) * Math.sin(Math.toRadians(hourDegree)) + 20),
                    numberPaint
            );
        }
    }

    // 获取当前的时间：时、分、秒
    private Time getCurrentTime() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        return new Time(
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    private void runClock() {
        isRunning = true;
        handler.postDelayed(clockRunnable, 1000);
    }

}
