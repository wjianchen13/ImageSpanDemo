package com.example.imagespandemo.tag;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

/**
 * 可点击span
 */
public class BaseUnderLineClickSpan extends ClickableSpan {

    private Context mContext;
    private int color;
    private int colorId;
    private WeakReference<View.OnClickListener> wrOnClickListener;
    private boolean isShowUnderLine = false;

    public BaseUnderLineClickSpan(@ColorInt int color, View.OnClickListener listener) {
        super();
        wrOnClickListener = new WeakReference<>(listener);
        this.color = color;
    }

    public BaseUnderLineClickSpan(Context context, int colorId, View.OnClickListener listener) {
        super();
        wrOnClickListener = new WeakReference<>(listener);
        mContext = context.getApplicationContext();
        this.colorId = colorId;
    }

    public void setShowUnderLine(boolean isShowUnderLine) {
        this.isShowUnderLine = isShowUnderLine;
    }

    @Override
    public void onClick(View widget) {
        if (wrOnClickListener != null) {
            View.OnClickListener listener = wrOnClickListener.get();
            if (listener != null)
                listener.onClick(widget);
        }
    }

    public boolean isShowUnderLine() {
        return isShowUnderLine;
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

}
