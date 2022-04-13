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
 * 95xiu绘制粉丝徽章
 */
public class FansBadgeImageSpan extends ImageSpan {

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

    /**
     * text bounds
     */
    private final Rect bounds;

    private String badge;

    private SoftReference<Drawable> mDrawableRef;

    public FansBadgeImageSpan(Drawable d, Context context, String sid, String badge, int align) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiuAlign = align;
        this.sid = sid;
        this.badge = badge;
        bounds = new Rect();
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
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
        int dis = b.getBounds().height();
        int transY = (bottom - top - dis) / 2;
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        // draw level
        int width = getDrawable().getBounds().width();
        int startWidth = (int)(width * 0.43);
        paint.setTextSize(ScreenUtils.dip2px(mContext, 10));
        paint.getTextBounds(sid, 0, sid.length(), bounds);
        int textHeight = bounds.bottom - bounds.top;
        int tx = (width - (width - startWidth) - bounds.width()) / 2 + ScreenUtils.dip2px(mContext, 0.0f);
        int ty = (bottom - textHeight) / 2 + textHeight - bounds.bottom - transY;
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(sid, tx , ty , paint);

        // draw badge
        paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
        paint.getTextBounds(badge, 0, badge.length(), bounds);
        textHeight = bounds.bottom - bounds.top;
        tx = (width - startWidth- bounds.width()) / 2 + startWidth;
        ty = (bottom - textHeight) / 2 + textHeight - bounds.bottom - transY;
        canvas.drawText(badge, tx , ty , paint);
        canvas.restore();
    }


    /**
     * 绘制文字区域
     * @param canvas
     * @param paint
     * @param tx
     * @param ty
     * @param color
     */
    private void drawRect(Canvas canvas, Paint paint, int tx, int ty, int color, int des) {
        paint.setColor(color);
        canvas.drawRect(new Rect(tx, ty - (bounds.bottom - bounds.top) + des, tx + bounds.width(), ty + des), paint);
        paint.setColor(Color.rgb(255, 255, 255));
    }
}
