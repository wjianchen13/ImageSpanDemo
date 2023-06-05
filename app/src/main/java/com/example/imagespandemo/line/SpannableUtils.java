package com.example.imagespandemo.line;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.line.span.CusImageSpan;
import com.example.imagespandemo.line.span.CustomImageSpan;
import com.example.imagespandemo.line.span.ImageSpanConstants;
import com.example.imagespandemo.line.span.LinearGradientSpan;
import com.example.imagespandemo.line.span.UnderLineClickSpan;

/**
 * Span操作类
 */
public class SpannableUtils {

    /**
     * 徽章的高度，宽度按照高度等比缩放
     */
    public static final int BADGE_NORMAL_HEIGHT = 30;

    /**
     * 返回Vip徽章
     *
     * @param nobleId Vip等级
     */
    public static SpannableString getUserNameSpan(Context context, int nobleId, String name) {
        return new SpannableString(name);
    }

    /**
     * 获取指定颜色内容
     *
     * @param context
     * @param txt
     * @param colorId
     * @return
     */
    public static SpannableString getColorSpanText(Context context, String txt, @ColorInt int colorId) {
        SpannableString spanStr = new SpannableString(txt);
        if (context != null) {
            spanStr.setSpan(new ForegroundColorSpan(colorId), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanStr;
    }

    /**
     * 设置Drawable的大小
     *
     * @param height
     * @param d
     * @return
     */
    public static void setDrawableBound(int height, @NonNull Drawable d) {
        if (d != null) {
            int intrinsicWidth = d.getIntrinsicWidth();
            int intrinsicHeight = d.getIntrinsicHeight();
            float factor = (float) height / intrinsicHeight;
            d.setBounds(0, 0, (int) (intrinsicWidth * factor), height);
        }
    }

    /**
     * 获取指定颜色和字体大小的span
     *
     * @param s       字体
     * @param sizeId  尺寸的id
     * @param colorId 颜色id
     */
    public static SpannableString getColorAndSizeTestSpan(Context context, String s, int sizeId, int colorId) {
        if (TextUtils.isEmpty(s)) {
            return new SpannableString("");
        }
        SpannableString spanString = new SpannableString(s);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanString.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获取指定颜色加粗内容
     *
     * @param txt
     * @param colorId
     * @return
     */
    public static SpannableString getColorBoldSpanText(String txt, @ColorInt int colorId) {
        return getColorAndStyleSpanText(txt, colorId, Typeface.BOLD);
    }

    /**
     * 获取指定颜色，不同式样的内容
     *
     * @param txt
     * @param colorId
     * @param style   式样
     * @return
     */
    public static SpannableString getColorAndStyleSpanText(String txt, @ColorInt int colorId, int style) {
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new ForegroundColorSpan(colorId), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new StyleSpan(style), 0, spanStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanStr;
    }

    /**
     * 设置指定内容的颜色，并且添加点击
     *
     * @param txt              显示内容
     * @param color            颜色值
     * @param mOnClickListener 点击回调
     * @param clickTag         携带内容
     * @return
     */
    public static SpannableString getColorAndClickSpanText(String txt, @ColorInt int color, final View.OnClickListener mOnClickListener, final Object clickTag, boolean isShowUnderLine) {
        String clickText = txt + "";
        SpannableString spanStr = new SpannableString(clickText);
        UnderLineClickSpan underLineSpan = new UnderLineClickSpan(color, mOnClickListener) {

            @Override
            public void onClick(View widget) {
                widget.setTag(clickTag);
                super.onClick(widget);
            }
        };
        underLineSpan.setShowUnderLine(isShowUnderLine);
        spanStr.setSpan(underLineSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 设置指定内容的颜色，并且添加点击
     *
     * @param context          context
     * @param txt              显示内容
     * @param colorId          颜色引用
     * @param mOnClickListener 点击回调
     * @param clickTag         携带内容
     * @return
     */
    public static SpannableString getColorAndClickSpanText(Context context, String txt, @ColorRes int colorId,
                                                           final View.OnClickListener mOnClickListener, final Object clickTag, boolean isShowUnderLine) {
        String clickText = txt + "";
        SpannableString spanStr = new SpannableString(clickText);
        if (context != null) {
            UnderLineClickSpan underLineSpan = new UnderLineClickSpan(context, colorId, mOnClickListener) {

                @Override
                public void onClick(View widget) {
                    widget.setTag(clickTag);
                    super.onClick(widget);
                }
            };
            underLineSpan.setShowUnderLine(isShowUnderLine);
            spanStr.setSpan(underLineSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanStr;
    }

    /**
     * 指定字体从上到下渐变颜色，点击的字符串
     *
     * @param txt        显示内容
     * @param startColor 渐变开始颜色
     * @param endColor   渐变结束颜色
     * @return
     */
    public static SpannableString getGradientSpanText(Context context, String start, String txt, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor, boolean isBold) {
        return getGradientClickSpanText(context, start, txt, startColor, midColor, endColor, isBold, 0, null, null);
    }

    /**
     * 指定字体从上到下渐变颜色，点击的字符串
     * @param start            开始偏移字符串
     * @param txt              显示内容
     * @param startColor       渐变开始颜色
     * @param midColor         渐变中间颜色
     * @param endColor         渐变结束颜色
     * @param isBold           是否加粗
     * @param clickColor       字体颜色
     * @param mOnClickListener 监听
     * @return
     */
    public static SpannableString getGradientClickSpanText(Context context, String start, String txt, @ColorRes int startColor, @ColorRes int midColor, @ColorRes int endColor, boolean isBold, @ColorRes int clickColor,
                                                           final View.OnClickListener mOnClickListener, final Object clickTag) {
        if(TextUtils.isEmpty(txt)) {
            return new SpannableString("");
        }
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new LinearGradientSpan(context, start, txt, startColor, midColor, endColor), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if(isBold) {
            spanStr.setSpan(new StyleSpan(Typeface.BOLD), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        if (mOnClickListener != null) {
            UnderLineClickSpan underLineSpan = new UnderLineClickSpan(context, clickColor, mOnClickListener) {
                @Override
                public void onClick(View widget) {
                    widget.setTag(clickTag);
                    super.onClick(widget);
                }
            };
            underLineSpan.setShowUnderLine(false);
            spanStr.setSpan(underLineSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanStr;
    }

    /**
     * 获取图像Spannable
     *
     * @param id
     * @return
     */
    public static SpannableString getImageSpan(Context context, int id) {
        SpannableString spanStr = null;
        if (id > 0) {
            try {
                spanStr = new SpannableString("img ");
                Drawable img = ContextCompat.getDrawable(context, id);
                img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                ImageSpan imgSpan = createImageSpan(img);
//                ImageSpan imgSpan = new ImageSpan(context, R.drawable.ic_chat_msg);
                spanStr.setSpan(imgSpan, 0, spanStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 创建一个居中显示的ImageSpan
     *
     * @param drawable
     * @return
     */
    public static ImageSpan createImageSpan(Drawable drawable) {
        return createImageSpan(drawable, ImageSpanConstants.IMAGE_SPAN_ALIGN_CENTER);
    }

    /**
     * @param drawable
     * @param v
     * @return
     */
    public static ImageSpan createImageSpan(Drawable drawable, int v) {
        return new CusImageSpan(drawable, v);
    }


}


























