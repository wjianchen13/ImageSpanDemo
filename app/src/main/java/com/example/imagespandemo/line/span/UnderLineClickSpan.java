package com.example.imagespandemo.line.span;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

/**
 * 可点击span
 */
public class UnderLineClickSpan extends ClickableSpan {

    private int colorId;
    private int color;
    private Context mContext;
    private boolean isShowUnderLine = false;
    private WeakReference<View.OnClickListener> wrOnClickListener;

    public UnderLineClickSpan(Context context, int colorId, View.OnClickListener listener) {
        super();
        wrOnClickListener = new WeakReference<>(listener);
        mContext = context.getApplicationContext();
        this.colorId = colorId;
    }

    public UnderLineClickSpan(@ColorRes int color, View.OnClickListener listener) {
        super();
        wrOnClickListener = new WeakReference<>(listener);
        this.color = color;
    }

    @Override
    public void onClick(View widget) {
        if (wrOnClickListener != null) {
            View.OnClickListener listener = wrOnClickListener.get();
            if (listener != null)
                listener.onClick(widget);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (mContext == null)
            ds.setColor(color);
        else {
            try {
                ds.setColor(ContextCompat.getColor(mContext, colorId));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        ds.setUnderlineText(isShowUnderLine);

    }

    public boolean isShowUnderLine() {
        return isShowUnderLine;
    }

    public void setShowUnderLine(boolean isShowUnderLine) {
        this.isShowUnderLine = isShowUnderLine;
    }

}
