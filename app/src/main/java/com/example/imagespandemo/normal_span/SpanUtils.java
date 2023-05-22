package com.example.imagespandemo.normal_span;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.MaskFilterSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

public class SpanUtils {

    public static SpannableString getColorSpanText(String txt, int colorId) {
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new ForegroundColorSpan(colorId), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 获取指定颜色，指定点击的字符串
     * @param
     * @return
     */
    public static SpannableString getColorClickSpanText(String txt, int colorId, int colorStart, int colorEnd, int clickColorId,
                                                        int clickStart, int clickEnd, final View.OnClickListener mOnClickListener, final Object clickTag) {
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new ForegroundColorSpan(colorId), colorStart, colorEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        NoLineClickSpan mNoLineClickSpan = new NoLineClickSpan(clickColorId, mOnClickListener) {
            @Override
            public void onClick(View widget) {
                widget.setTag(clickTag);
                super.onClick(widget);
            }
        };
        mNoLineClickSpan.setShowUnderLine(true);
        spanStr.setSpan(mNoLineClickSpan, clickStart, clickEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 给指定的内容设置颜色并可点击
     *
     * @param context
     * @param txt
     * @param colorId
     * @param mOnClickListener
     * @param clickTag
     * @return
     */
    public static SpannableString getClickSpanText(Context context, String txt, @ColorRes int colorId, final View.OnClickListener mOnClickListener, final Object clickTag, boolean isShowUnderLine) {
        String clickText = txt + "";
        SpannableString spanStr = new SpannableString(clickText);
        if (context != null) {
            NoLineClickSpan mNoLineClickSpan = new NoLineClickSpan(context, colorId, mOnClickListener) {

                @Override
                public void onClick(View widget) {
                    widget.setTag(clickTag);
                    super.onClick(widget);
                }
            };
            mNoLineClickSpan.setShowUnderLine(isShowUnderLine);
            spanStr.setSpan(mNoLineClickSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanStr;
    }

    /**
     * 给指定的内容设置渐变
     * @param txt
     * @param startColor
     * @param endColor
     * @return
     */
    public static SpannableString getLinearGradientSpanText(String txt, @ColorInt int startColor, @ColorInt int endColor) {
        String clickText = txt + "";
        SpannableString spanStr = new SpannableString(clickText);
        LinearGradientFontSpan2 span = new LinearGradientFontSpan2(startColor, endColor);
        spanStr.setSpan(span, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 获取指定颜色，指定点击的字符串
     * @param clickColor 有渐变这个参数不起作用
     * @return
     */
    public static SpannableString getGradientClickSpanText(String txt, @ColorInt int startColor, @ColorInt int endColor, int clickColor,
                                                        final View.OnClickListener mOnClickListener, final Object clickTag) {
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new LinearGradientFontSpan2(startColor, endColor), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER));
//        spanStr.setSpan(new MaskFilterSpan(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID)), 0, spanStr.length(), Spannable.
//                SPAN_INCLUSIVE_EXCLUSIVE);

        NoLineClickSpan mNoLineClickSpan = new NoLineClickSpan(clickColor, mOnClickListener) {
            @Override
            public void onClick(View widget) {
                widget.setTag(clickTag);
                super.onClick(widget);
            }
        };
        mNoLineClickSpan.setShowUnderLine(false);
        spanStr.setSpan(mNoLineClickSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }
}
