package com.example.imagespandemo.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.imagespandemo.R;

public class GradientFontSpan extends ReplacementSpan {

    private int mSize;
    private int mStartColor;
    private int mEndColor;

    public GradientFontSpan(int startColor, int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
    }

    public GradientFontSpan(Context context) {
        mStartColor = context.getResources().getColor(R.color.colorPrimary);
        mEndColor = context.getResources().getColor(R.color.colorAccent);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       @Nullable Paint.FontMetricsInt fm) {
        mSize = Math.round(paint.measureText(text, start, end));
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
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        LinearGradient gradient = new LinearGradient(0, 0, mSize, 0, mEndColor, mStartColor,
                Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawText(text, start, end, x, y, paint);
    }

}
