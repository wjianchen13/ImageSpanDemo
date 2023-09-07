package com.example.imagespandemo.cache;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.imagespandemo.R;

/**
 * 自动滚动TextView
 * 需要封装一层
 */
public class MarqueeTextView extends FrameLayout {

    private Context mContext;
    private TextView tv;

    /**
     * 字体大小
     */
    private int textColor;

    /**
     * id字体颜色
     */
    private int textSize;

    public MarqueeTextView(@NonNull Context context) {
        super(context);
        init();
        this.mContext = context;
    }

    public MarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
        this.mContext = context;
    }

    public MarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
        this.mContext = context;
    }

    /**
     * init attr
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.marquee_view);
        textColor = a.getColor(R.styleable.marquee_view_marquee_text_color, 0);
        textSize = a.getDimensionPixelOffset(R.styleable.marquee_view_marquee_text_size, 0);
        a.recycle();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_marquee_textview, this, true);
        tv = findViewById(R.id.tv);
        if (textColor != 0)
            tv.setTextColor(textColor);
        if (textSize != 0)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setContent(String str) {
        if (tv != null) {
            tv.setText(str);
            tv.setSelected(true);
        }
    }

    public void setContent(CharSequence text) {
        if (tv != null) {
            tv.setText(text);
            tv.setSelected(true);
        }
    }

    public void setTypeface(Typeface tf){
        if (tv != null) {
            tv.setTypeface(tf);
        }
    }

}
