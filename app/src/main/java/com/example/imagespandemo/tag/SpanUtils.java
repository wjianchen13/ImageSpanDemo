package com.example.imagespandemo.tag;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

public class SpanUtils {

    public static SpannableString getTagSpan(Context context, String txt, boolean isBold, @ColorRes int clickColor, final View.OnClickListener mOnClickListener, final Object clickTag) {
        if (TextUtils.isEmpty(txt))
            return new SpannableString("");
        SpannableString spanStr = new SpannableString(txt);
        spanStr.setSpan(new IconTextSpan(context, R.color.colorPrimary, txt), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (isBold)
            spanStr.setSpan(new StyleSpan(Typeface.BOLD), 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (mOnClickListener != null) {
            BaseUnderLineClickSpan underLineSpan = new BaseUnderLineClickSpan(context, clickColor, mOnClickListener) {
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
     * 福袋加入按钮
     */
    public static SpannableString getJoinImageSpan(Context context, String content, final View.OnClickListener mOnClickListener, final Object clickTag) {
        return getTagImageSpan(context, R.drawable.bg_test_round, content, ScreenUtils.dip2px(context, 13),
                ScreenUtils.dip2px(context, 6), ScreenUtils.dip2px(context, 28), 0, mOnClickListener, clickTag);
    }

    /**
     * 标签ImageSpan
     * 背景自适应，中间文字，支持自定义高度
     */
    public static SpannableString getTagImageSpan(Context context, @DrawableRes int resId, String content,
                                                  int textSize, int paddingLR, int spanHeight, @ColorRes int clickColor, final View.OnClickListener mOnClickListener, final Object clickTag) {
        if(resId == 0 || TextUtils.isEmpty(content))
            return new SpannableString("");

        int size = ScreenUtils.dip2px(context, 16);
        SpannableString spanStr = new SpannableString(content);
        if (context != null) {
            Drawable img = ContextCompat.getDrawable(context, resId);
            if (img != null) {

                int iHeight = img.getIntrinsicHeight();
                float factor = (float) size / iHeight;
                Rect textRect = new Rect();
                Paint paint = new Paint();
                paint.setTextSize(textSize);
                paint.getTextBounds(content, 0, content.length(), textRect);

                img.setBounds(0, 0, textRect.width() + (paddingLR * 2), spanHeight);
                ImageSpan fansImageSpan = new CustomTagSpan(img, context, content);
                spanStr.setSpan(fansImageSpan, 0, spanStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (mOnClickListener != null) {
                BaseUnderLineClickSpan underLineSpan = new BaseUnderLineClickSpan(context, clickColor, mOnClickListener) {
                    @Override
                    public void onClick(View widget) {
                        widget.setTag(clickTag);
                        super.onClick(widget);
                    }
                };
                underLineSpan.setShowUnderLine(false);
                spanStr.setSpan(underLineSpan, 0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getSizeColorClick_Txt(Context context, String txt, int sizeId, int colorId, boolean isBold, final View.OnClickListener mOnClickListener, final Object clickTag) {
        if (TextUtils.isEmpty(txt)) return new SpannableString("");
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); /*            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); */
            if (isBold)
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (mOnClickListener != null) {
                BaseUnderLineClickSpan underLineSpan = new BaseUnderLineClickSpan(context, colorId, mOnClickListener) {
                    @Override
                    public void onClick(View widget) {
                        widget.setTag(clickTag);
                        super.onClick(widget);
                    }
                };
                underLineSpan.setShowUnderLine(false);
                spanString.setSpan(underLineSpan, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spanString;
    }

}
