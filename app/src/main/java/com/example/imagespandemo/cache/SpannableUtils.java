package com.example.imagespandemo.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import androidx.annotation.ColorRes;

import com.example.imagespandemo.BaseApp;
import com.example.imagespandemo.R;

public class SpannableUtils {

    /**
     * 徽章的高度，宽度按照高度等比缩放
     */
    public static final int BADGE_NORMAL_HEIGHT = (int) BaseApp.getInstance().getResources().getDimension(R.dimen.badge_normal_height);

    /**
     * 显示vip名字，渐变，需要点击
     * @param maxWidth 限制最大长度，用于单行超出范围显示省略号的情况
     * @return
     */
    public static SpannableString getVipNameClick(String start, String name, int maxWidth) {
        return SpannableUtils.getGradientClickSpanText(start,
                name,
                R.color.c2176f5,
                R.color.c87bcff,
                R.color.c2176f5,
                maxWidth);
    }

    /**
     * 指定字体从上到下渐变颜色，点击的字符串
     * @param start            开始偏移字符串
     * @param txt              显示内容
     * @param startColor       渐变开始颜色
     * @param midColor         渐变中间颜色
     * @param endColor         渐变结束颜色
     * @param maxWidth         最大宽度
     * @return
     */
    public static SpannableString getGradientClickSpanText(String start, String txt, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor, int maxWidth) {
        if(TextUtils.isEmpty(txt)) {
            return new SpannableString("");
        }
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new LinearGradientSpan(start, txt, startColor, midColor, endColor, maxWidth), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static SpannableStringBuilder getUserBadge(Context context, Bitmap bp) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(getNameImageSpan(context, bp));
        return builder;
    }

    /**
     * 获取名称span
     *
     */
    public static SpannableString getNameImageSpan(Context context, Bitmap bp) {
        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
        SpannableString spanStr = null;
        if (context != null && bp != null) {
            Drawable img = new BitmapDrawable(context.getResources(), bp);
            if (img != null) {
                spanStr = new SpannableString("img ");
                int iHeight = img.getIntrinsicHeight();
                float factor = (float) size / iHeight;
                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
                ImageSpan fansImageSpan = new CustomImageSpan(img, context, "");
                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

}
