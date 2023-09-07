package com.example.imagespandemo.gradient;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.imagespandemo.R;

public class GradientTextView extends AppCompatTextView {
    private String mGradientText;
    private int mStartColor;
    private int mEndColor;
    private boolean mBold;

    public GradientTextView(@NonNull Context context) {
        this(context, null);
    }

    public GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        if (typedArray == null) {
            return;
        }
        mGradientText =
                typedArray.getString(R.styleable.GradientTextView_gradient_text);
        mBold = typedArray.getBoolean(R.styleable.GradientTextView_gradient_bold,
                false);
        mStartColor =
                typedArray.getColor(R.styleable.GradientTextView_gradient_start_color,
                        getResources().getColor(R.color.colorPrimary));
        mEndColor =
                typedArray.getColor(R.styleable.GradientTextView_gradient_end_color,
                        getResources().getColor(R.color.colorAccent));
        typedArray.recycle();
        setGradientText(mGradientText);
        if (mBold) {
            getPaint().setFakeBoldText(true);
        }
    }

    public void setGradientText(String text) {
        if (text == null || text.length() == 0) {
            return;
        }
        SpannableString spannableString = new SpannableString(text);
        GradientFontSpan gradientFontSpan = new GradientFontSpan(mStartColor, mEndColor);
        spannableString.setSpan(gradientFontSpan, 0, text.length(),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(spannableString);
        invalidate();
    }

    public static class GradientFontSpan extends ReplacementSpan {
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
}
