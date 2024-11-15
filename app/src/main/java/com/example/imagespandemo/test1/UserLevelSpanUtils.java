package com.example.imagespandemo.test1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.imagespan.PkLevelImageSpan;
import com.example.imagespandemo.utils.ScreenUtils;

public class UserLevelSpanUtils {

    /**
     *
     */
    public static SpannableString getUserLevelImageSpan(Context context, int level) {

        int size = ScreenUtils.dip2px(context, 16);
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = createDrawable(context, getWealthLevelResId(level));
            int iHeight = img.getIntrinsicHeight();
            float factor = (float)size / iHeight;
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new UserLevelImageSpan(img, context, level);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 生成新的drawable
     */
    private static Drawable createDrawable(Context mContext, int id) {
        int size = ScreenUtils.dip2px(mContext, 16);
        Drawable img = ContextCompat.getDrawable(mContext, id);
        int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
        int iWidth = img.getIntrinsicWidth();
        float factor;

        Bitmap target = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas temp_canvas = new Canvas(target);
        if(iHeight > iWidth) {
            factor = (float)size / iHeight;
            int left = (size - (int) (iWidth * factor)) / 2;
            img.setBounds(left, 0, (int) (iWidth * factor) + left, size);
        } else {
            factor = (float)size / iWidth;
            int top = (size - (int) (iHeight * factor)) / 2;
            img.setBounds(0, top, size, (int) (iHeight * factor) + top);
        }
        img.draw(temp_canvas);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), target);
        return drawable;
    }

    /**
     * 段位背景
     */
    private static int getPkBackground(int level) {
        int res = R.drawable.bg_rank_pk_bronze;
        if(level <= 0)
            res = R.drawable.bg_rank_pk_bronze;
        else if(level == 1)
            res = R.drawable.bg_rank_pk_silver;
        else if(level == 2)
            res = R.drawable.bg_rank_pk_gold;
        else if(level == 3)
            res = R.drawable.bg_rank_pk_platinum;
        else if(level == 4)
            res = R.drawable.bg_rank_pk_diamonds;
        else if(level == 5)
            res = R.drawable.bg_rank_pk_star;
        else if(level == 6)
            res = R.drawable.bg_rank_pk_king;
        else if(level > 6)
            res = R.drawable.bg_rank_pk_king;
        return res;
    }

    public static int getWealthLevelResId(int level) {
        if (level <= 0) {
            return R.drawable.ic_user_wealth_0;
        } else if (level >= 1 && level <= 9) {
            return R.drawable.ic_user_wealth_1_9;
        } else if (level >= 10 && level <= 19) {
            return R.drawable.ic_user_wealth_10_19;
        } else if (level >= 20 && level <= 29) {
            return R.drawable.ic_user_wealth_20_29;
        } else if (level >= 30 && level <= 39) {
            return R.drawable.ic_user_wealth_30_39;
        } else if (level >= 40 && level <= 49) {
            return R.drawable.ic_user_wealth_40_49;
        } else if (level >= 50 && level <= 59) {
            return R.drawable.ic_user_wealth_50_59;
        } else if (level >= 60 && level <= 65) {
            return R.drawable.ic_user_wealth_60_64;
        } else if (level >= 65 && level <= 69) {
            return R.drawable.ic_user_wealth_65_69;
        } else if (level >= 70 && level <= 75) {
            return R.drawable.ic_user_wealth_70_74;
        } else if (level >= 75 && level <= 79) {
            return R.drawable.ic_user_wealth_75_79;
        } else if (level >= 80) {
            return R.drawable.ic_user_wealth_80;
        }
        return R.drawable.ic_user_wealth_0;
    }

    /**
     *
     */
    public static SpannableString getUserLevelImageSpan1(Context context, int level) {

        int size = ScreenUtils.dip2px(context, 16);
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, getWealthLevelResId(level));
            int iHeight = img.getIntrinsicHeight();
            float factor = (float)size / iHeight;
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new UserLevelImageSpan(img, context, level, getGradientColor(context, level), ContextCompat.getColor(context, R.color.common_white));
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    private static int[] getGradientColor(Context context, int level) {
        if(level == 1) {
            return new int[] {ContextCompat.getColor(context, R.color.c_ll_ff00bfcb), ContextCompat.getColor(context, R.color.c_ll_ffffc735)};
        }
        return new int[] {ContextCompat.getColor(context, R.color.c_ll_ff00bfcb), ContextCompat.getColor(context, R.color.c_ll_ffffc735)};
    }


}
