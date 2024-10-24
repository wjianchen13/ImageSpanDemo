package com.example.imagespandemo.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;

public class ShadowSpan extends ReplacementSpan {

    // 文字宽度
    private int mSize;

    private int drawColor;
    private int shadowColor;

    private int startColor = Color.GRAY;
    // 渐变结束颜色
    private int endColor = Color.RED;
    private float radius;
    private float dx;
    private float dy;

    public ShadowSpan(int startColor, int endColor, int shadowColor, float radius, float dx, float dy) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.shadowColor = shadowColor;
        drawColor = Color.RED;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end));
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        if (fm != null) {
            fm.top = metrics.top;
            fm.ascent = metrics.ascent;
            fm.descent = metrics.descent;
            fm.bottom = metrics.bottom;
        }
        return mSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        LinearGradient lg = new LinearGradient(mSize, 0, 0, 0,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);


        Paint shadowPaint = new Paint();
        shadowPaint.setTextSize(100);
        shadowPaint.setColor(drawColor);
//        shadowPaint.setAlpha(50); // 设置阴影的透明度
        shadowPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(radius, 20, 20, Color.GREEN);

//        shadowPaint.setShader(lg);


        canvas.drawText(text, start, end, x, y, shadowPaint);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        // 如果需要，可以在这里设置文本的属性
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        // 如果需要，可以在这里设置测量文本的属性
    }
}
