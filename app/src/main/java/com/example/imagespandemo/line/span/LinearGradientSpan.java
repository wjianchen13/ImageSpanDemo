package com.example.imagespandemo.line.span;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class LinearGradientSpan extends CharacterStyle
        implements UpdateAppearance {

    private String mStart;
    private String mText;

    private int startColor;
    private int midColor;
    private int endColor;

    public LinearGradientSpan(Context context, String start, String text, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor) {
        this.mStart = start;
        this.mText = text;
        this.startColor = ContextCompat.getColor(context, startColor);
        this.midColor = ContextCompat.getColor(context, midColor);
        this.endColor = ContextCompat.getColor(context, endColor);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        if(tp != null) {
            float start = !TextUtils.isEmpty(mStart) ? tp.measureText(mStart) : 0;
            float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) : 0;
            LinearGradient lg = new LinearGradient(0 + start, 0, width + start, 0,
                    new int[]{startColor, midColor, endColor},
                    null, Shader.TileMode.REPEAT);
            tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }

    }
}

