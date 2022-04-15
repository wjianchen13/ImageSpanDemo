package com.example.imagespandemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.fans.FansBadgeImageSpan;
import com.example.imagespandemo.fans.KiwiiFansBadgeImageSpan;
import com.example.imagespandemo.fans.OldFansBadgeImageSpan;
import com.example.imagespandemo.fans.TestFansBadgeImageSpan;
import com.example.imagespandemo.imagespan.BadgeImageSpan;
import com.example.imagespandemo.imagespan.BadgeImageSpan1;
import com.example.imagespandemo.imagespan.FansImageSpan;
import com.example.imagespandemo.imagespan.GoodNumberImageSpan2;
import com.example.imagespandemo.imagespan.PkLevelImageSpan;
import com.example.imagespandemo.imagespan.PkLevelImageSpan1;

public class SpanUtils {

    /**
     * 返回靓号图片拼接
     *
     * @param context
     * @param sid
     * @return
     */
    public static SpannableString getGoodNumberImageSpan(Context context, String sid, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.live_number_bg_2);
//            GoodNumberSpanUtil2 spanUtil = new GoodNumberSpanUtil2(context, sid);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            float factor = (float)size / iHeight; // 转换因子
//            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(spanUtil.getTotalWidth() * factor), size);
            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan goodNumberImageSpan = new GoodNumberImageSpan2(img, context, sid, verticalAlignment);
            spanStr.setSpan(goodNumberImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 返回粉丝图片拼接
     *
     * @param context
     * @param sid
     * @return
     */
    public static SpannableString getFansImageSpan(Context context, String sid, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_level);
//            GoodNumberSpanUtil2 spanUtil = new GoodNumberSpanUtil2(context, sid);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            int iWidth = img.getIntrinsicWidth();
            float factor = (float)size / iHeight; // 转换因子
//            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(spanUtil.getTotalWidth() * factor), size);
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new FansImageSpan(img, context, sid, verticalAlignment);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 返回粉丝图片拼接
     *
     * @param context
     * @param sid
     * @return
     */
    public static SpannableString getBadgeImageSpan(Context context, String sid, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_badge);
//            GoodNumberSpanUtil2 spanUtil = new GoodNumberSpanUtil2(context, sid);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            int iWidth = img.getIntrinsicWidth();
            float factor = (float)size / iHeight; // 转换因子
//            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(spanUtil.getTotalWidth() * factor), size);
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new BadgeImageSpan(img, context, sid, "你好", verticalAlignment);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 返回粉丝图片拼接
     *
     * @param context
     * @param sid
     * @return
     */
    public static SpannableString getBadgeImageSpan1(Context context, String sid, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_badge);
//            GoodNumberSpanUtil2 spanUtil = new GoodNumberSpanUtil2(context, sid);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            int iWidth = img.getIntrinsicWidth();
            float factor = (float)size / iHeight; // 转换因子
//            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(spanUtil.getTotalWidth() * factor), size);
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new BadgeImageSpan1(img, context, sid, "你好", verticalAlignment);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    public static SpannableString getPkImageSpan(Context context, String name, int level, int grade, int verticalAlignment, int mode) {
        return getPkImageSpan(context, name, level, grade, ScreenUtils.dip2px(context, mode == PkLevelImageSpan.MODE_NORMAL ? 29 : 20) , verticalAlignment, mode);
    }

    /**
     * 返回粉丝图片拼接
     *
     * @param context
     * @param
     * @return
     */
    public static SpannableString getPkImageSpan(Context context, String name, int level, int grade, int size, int verticalAlignment, int mode) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = mode == PkLevelImageSpan.MODE_NORMAL
                    ? ContextCompat.getDrawable(context, getPkBackground(level))
                    : createDrawable(context, getPkIcon(level));
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            int iWidth = img.getIntrinsicWidth();
            float factor = (float)size / iHeight; // 转换因子
//            img.setBounds(0, 0, sid.length() <= 2 ? (int)(img.getIntrinsicWidth() * factor) : (int)(spanUtil.getTotalWidth() * factor), size);
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new PkLevelImageSpan(img, context, name, level, grade, verticalAlignment, mode);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    private static int getPkBackground(int level) {
        int res = 0;
        if(level == 0)
            res = R.drawable.bg_rank_pk_bronze;
        if(level == 1)
            res = R.drawable.bg_rank_pk_silver;
        if(level == 2)
            res = R.drawable.bg_rank_pk_gold;
        if(level == 3)
            res = R.drawable.bg_rank_pk_platinum;
        if(level == 4)
            res = R.drawable.bg_rank_pk_diamonds;
        if(level == 5)
            res = R.drawable.bg_rank_pk_star;
        if(level == 6)
            res = R.drawable.bg_rank_pk_king;
        return res;
    }

    private static int getPkIcon(int level) {
        int res = 0;
        if(level == 0)
            res = R.drawable.ic_rank_pk_bronze;
        if(level == 1)
            res = R.drawable.ic_rank_pk_silver;
        if(level == 2)
            res = R.drawable.ic_rank_pk_gold;
        if(level == 3)
            res = R.drawable.ic_rank_pk_platinum;
        if(level == 4)
            res = R.drawable.ic_rank_pk_diamonds;
        if(level == 5)
            res = R.drawable.ic_rank_pk_star;
        if(level == 6)
            res = R.drawable.ic_rank_pk_king;
        return res;
    }

    /**
     * 返回粉丝图片拼接
     *
     * @param context
     * @param
     * @return
     */
    public static SpannableString getPkImageSpan1(Context context, String name, int segment, int mode) {
        return PkLevelImageSpan1.getPkImageSpan(context, name, segment, mode);
    }

    private static Drawable createDrawable(Context mContext, int id) {
        int size = ScreenUtils.dip2px(mContext, 20);
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
     * 独立国际版 返回粉丝团徽章
     */
    public static SpannableString getInterFansBadgeImageSpan(Context context, String sid, String badge, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null && null != sid && !TextUtils.isEmpty(badge)) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_group_badge);
            ImageSpan fansImageSpan = new KiwiiFansBadgeImageSpan(context, img, sid, badge, verticalAlignment, img.getIntrinsicWidth(), Math.max(size, img.getIntrinsicHeight()));
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 返回粉丝团徽章
     */
    public static SpannableString getTestImageSpan(Context context, String sid, String badge, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null && null != sid && !TextUtils.isEmpty(badge)) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_group_badge);
            ImageSpan fansImageSpan = new TestFansBadgeImageSpan(context, img, sid, badge, verticalAlignment, img.getIntrinsicWidth(), Math.max(size, img.getIntrinsicHeight()));
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 95xiu返回粉丝团徽章
     */
    public static SpannableString getFansBadgeImageSpan(Context context, String sid, String badge, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_group_badge1);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            float factor = (float)size / iHeight; // 转换因子
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new FansBadgeImageSpan(img, context, sid, badge, verticalAlignment);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    /**
     * 95xiu返回粉丝团徽章
     */
    public static SpannableString getOldFansBadgeImageSpan(Context context, String sid, String badge, int size, int verticalAlignment) {
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = ContextCompat.getDrawable(context, R.drawable.bg_fans_group_badge1);
            int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
            float factor = (float)size / iHeight; // 转换因子
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new OldFansBadgeImageSpan(img, context, sid, badge, verticalAlignment);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }
    
}
