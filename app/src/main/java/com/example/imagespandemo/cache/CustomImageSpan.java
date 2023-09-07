package com.example.imagespandemo.cache;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;

import com.example.imagespandemo.BaseApp;
import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.WeakReference;

/**
 * 自定义ImageSpan
 * 用户等级，明星等级
 */
public class CustomImageSpan extends ImageSpan {

    /**
     * 财富等级类型
     */
    public static final int TYPE_WEALTH = 1;

    /**
     * 明星等级类型
     */
    public static final int TYPE_CHARM = 2;

    private final WeakReference<Context> mContext;

    private final String content;

    public CustomImageSpan(Drawable d, Context context, String content/*, int level*/) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = new WeakReference<>(context);
        this.content = content;
    }

//    /**
//     * 获取房主span
//     *
//     */
//    public static SpannableString getOwnerImageSpan (Context context) {
//        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
//        SpannableString spanStr = null;
//        if (context != null) {
//            Drawable img = ContextCompat.getDrawable(BaseApp.getInstance(), R.drawable.ic_tag_owner);
//            if (img != null) {
//                spanStr = new SpannableString("img ");
//                int iHeight = img.getIntrinsicHeight();
//                float factor = (float) size / iHeight;
//                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
//                ImageSpan fansImageSpan = new CustomImageSpan(img, context, "");
//                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spanStr == null ? new SpannableString("") : spanStr;
//    }
//
//
//    /**
//     * 获取超管span
//     *
//     */
//    public static SpannableString getSuperAdminImageSpan (Context context) {
//        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
//        SpannableString spanStr = null;
//        if (context != null) {
//            Drawable img = ContextCompat.getDrawable(BaseApp.getInstance(), R.drawable.ic_tag_super_admin);
//            if (img != null) {
//                spanStr = new SpannableString("img ");
//                int iHeight = img.getIntrinsicHeight();
//                float factor = (float) size / iHeight;
//                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
//                ImageSpan fansImageSpan = new CustomImageSpan(img, context, "");
//                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spanStr == null ? new SpannableString("") : spanStr;
//    }
//
//
//    /**
//     * 获取房管span
//     *
//     */
//    public static SpannableString getRoomAdminImageSpan (Context context) {
//        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
//        SpannableString spanStr = null;
//        if (context != null) {
//            Drawable img = ContextCompat.getDrawable(BaseApp.getInstance(), R.drawable.ic_tag_room_admin);
//            if (img != null) {
//                spanStr = new SpannableString("img ");
//                int iHeight = img.getIntrinsicHeight();
//                float factor = (float) size / iHeight;
//                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
//                ImageSpan fansImageSpan = new CustomImageSpan(img, context, "");
//                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spanStr == null ? new SpannableString("") : spanStr;
//    }
//
//
//    /**
//     * 获取对应等级的span
//     *
//     * @paran type 1 财富等级 2 明星等级
//     * @paran level 对应等级
//     */
//    public static SpannableString getLevelImageSpan(int type, Context context, int level) {
//        if (level < 0)
//            return new SpannableString("");
//        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
//        SpannableString spanStr = null;
//        if (context != null) {
//            Drawable img = type == TYPE_WEALTH ? VipManagerV2.getInstance().getVipWealthDrawable(level) : VipManagerV2.getInstance().getVipStarDrawable(level);
//            if (img != null) {
//                spanStr = new SpannableString("img ");
//                int iHeight = img.getIntrinsicHeight();
//                float factor = (float) size / iHeight;
//                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
//                ImageSpan fansImageSpan = new CustomImageSpan(img, context, String.valueOf(level));
//                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spanStr == null ? new SpannableString("") : spanStr;
//    }
//
//    /**
//     * 获取对应等级的span
//     *
//     * @paran nobleId vip等级
//     */
//    public static SpannableString getImageSpan(Context context, int nobleId) {
//        if (nobleId < 0)
//            return new SpannableString("");
//        int size = SpannableUtils.BADGE_NORMAL_HEIGHT;
//        SpannableString spanStr = null;
//        if (context != null) {
//            Drawable img = VipManagerV2.getInstance().getVipDrawable(nobleId);
//            if (img != null) {
//                spanStr = new SpannableString("img ");
//                int iHeight = img.getIntrinsicHeight();
//                float factor = (float) size / iHeight;
//                img.setBounds(0, 0, (int) (img.getIntrinsicWidth() * factor), size);
//                ImageSpan fansImageSpan = new CustomImageSpan(img, context, "");
//                spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spanStr == null ? new SpannableString("") : spanStr;
//    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;  // 字体高度
            int drHeight = rect.bottom - rect.top; // 图片高度
            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 3;
            fm.ascent = -bottom; // 字体的高度 descent - leading
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }

    /**
     * 绘制span到画布上
     *
     * @param canvas 将被渲染的span canvas
     * @param text   当前文本
     * @param start  span开始字符索引
     * @param end    span结束字符索引
     * @param x      要绘制的image的左边框到textview左边框的距离
     * @param top    替换行的最顶部位置.
     * @param y      要替换的文字的基线坐标，即基线到textview上边框的距离
     * @param bottom 替换行的最底部位置.
     * @param paint  Paint instance.
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        Drawable b = getDrawable();
        Context c = mContext.get();

        if (c == null)
            c = BaseApp.getInstance();

        if (b == null || c == null) {
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            return;
        }

        float transY = ((bottom - top) - b.getBounds().bottom) / 2.0f + top; // 居中显示
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();
        if (!TextUtils.isEmpty(content)) {
            Rect rect = new Rect();
            paint.setTextSize(ScreenUtils.dip2px(mContext.get(), 12f));

            paint.getTextBounds(content, 0, content.length(), rect);
            int xl = (width - ScreenUtils.dip2px(mContext.get(), 16) - rect.width()) / 2 + ScreenUtils.dip2px(mContext.get(), 16) - ScreenUtils.dip2px(mContext.get(), 2);
            float yl = (float) (height + rect.height()) / 2 - ScreenUtils.dip2px(mContext.get(), 0.5f);
            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawText(content, xl, yl, paint);
        }
        canvas.restore();
    }

}