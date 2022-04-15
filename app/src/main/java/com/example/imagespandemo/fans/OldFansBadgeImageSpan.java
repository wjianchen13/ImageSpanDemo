package com.example.imagespandemo.fans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;


import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * 绘制粉丝徽章
 */
public class OldFansBadgeImageSpan extends ImageSpan {

    /**
     * context
     */
    private Context mContext;

    /**
     * 粉丝等级
     */
    private List<Integer> Ids;

    /**
     * 对齐方式，目前只有居中对齐
     */
    private int xiuAlign;

    /**
     * drawable reference
     */

    private String sid;

    private String badge;

    private SoftReference<Drawable> mDrawableRef;

    public OldFansBadgeImageSpan(Drawable d, Context context, String sid, String badge, int align) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiuAlign = align;
        this.sid = sid;
        this.badge = badge;
    }

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

    private Drawable getCachedDrawable() {
        SoftReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new SoftReference<>(d);  // 1 + 1 = 2  1 - 0.25 = 0.75  1+0.25 =  -1.25
        }
        return d;
    }

    /**
     * 绘制徽章
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if(sid == null || badge == null)
            return ;
        Drawable b = getCachedDrawable();
        int transY = top + (bottom - top) / 2 - b.getBounds().height() / 2;
        /*Paint.FontMetrics fm = paint.getFontMetrics();
        if(xiuAlign == ImageSpanAlign.XIU_ALIGN_CENTER) { // 居中对齐
            transY = (int) ((y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2);
        }*/
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();
        int startWidth = (int)(width * 0.43);

        // draw level
        Rect bounds = new Rect();
        paint.setTextSize(ScreenUtils.dip2px(mContext, 10));
        paint.getTextBounds(sid, 0, sid.length(), bounds);
        int tx = (width - (width - startWidth) - bounds.width()) / 2 + ScreenUtils.dip2px(mContext, 0.0f);
        int ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 0.0f);

//        paint.setColor(Color.rgb(0, 0, 255));
//        canvas.drawRect(new Rect(tx, ty - bounds.height(), tx + bounds.width(), ty + bounds.bottom), paint);

        paint.setColor(Color.rgb(255, 255, 255));

        canvas.drawText(sid, tx , ty , paint);

        // draw badge
        paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
        paint.getTextBounds(badge, 0, badge.length(), bounds);
        tx = (width - startWidth- bounds.width()) / 2 + startWidth;
        ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 1.2f);
        canvas.drawText(badge, tx , ty , paint);

        canvas.restore();
    }
}
