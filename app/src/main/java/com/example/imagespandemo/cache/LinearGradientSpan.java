package com.example.imagespandemo.cache;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.BaseApp;

public class LinearGradientSpan extends CharacterStyle
        implements UpdateAppearance {

    private String mStart;
    private String mText;

    private int startColor;
    private int midColor;
    private int endColor;
    private int mMaxWidth;


    public LinearGradientSpan(String start, String text, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor) {
        this(start, text, startColor, midColor, endColor, 0);
    }

    public LinearGradientSpan(String start, String text, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor, int maxWidth) {
        this.mStart = start;
        this.mText = text;
        this.startColor = ContextCompat.getColor(BaseApp.getInstance(), startColor);
        this.midColor = ContextCompat.getColor(BaseApp.getInstance(), midColor);
        this.endColor = ContextCompat.getColor(BaseApp.getInstance(), endColor);
        this.mMaxWidth = maxWidth;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        if(tp != null) {
            float start = !TextUtils.isEmpty(mStart) ? tp.measureText(mStart) : 0;
            float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) : 0;
            if(mMaxWidth > 0 && width > mMaxWidth)
                width = mMaxWidth;
            LinearGradient lg = new LinearGradient(0 + start, 0, width + start, 0,
                    new int[]{startColor, midColor, endColor},
                    null, Shader.TileMode.REPEAT);
            tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }

    }
}

