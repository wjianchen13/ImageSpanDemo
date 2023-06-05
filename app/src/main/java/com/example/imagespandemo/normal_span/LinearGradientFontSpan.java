package com.example.imagespandemo.normal_span;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class LinearGradientFontSpan extends CharacterStyle
        implements UpdateAppearance {

    private String mText;
    private int startColor;
    private int midColor;
    private int endColor;

    public LinearGradientFontSpan(String txt, int startColor, int midColor, int endColor) {
        this.mText = txt;
        this.startColor = startColor;
        this.midColor = midColor;
        this.endColor = endColor;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        float width = tp.measureText(mText);
        System.out.println("==========================? width: " + width);
        LinearGradient lg = new LinearGradient(0, 0, width, 0,
                new int[] {startColor, midColor, endColor},
                null, Shader.TileMode.REPEAT);
        tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响


    }
}

