package com.example.imagespandemo.gradient;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class ShadowSpan extends CharacterStyle implements UpdateAppearance {

    private float radius;
    private float dx;
    private float dy;
    private int shadowColor;

    public ShadowSpan(float radius, float dx, float dy, int shadowColor) {
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.shadowColor = shadowColor;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setShadowLayer(radius, dx, dy, shadowColor);
    }
}