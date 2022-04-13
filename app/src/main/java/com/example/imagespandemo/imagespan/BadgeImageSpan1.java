package com.example.imagespandemo.imagespan;

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
 * 绘制粉丝等级
 */
public class BadgeImageSpan1 extends ImageSpan {

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

    public BadgeImageSpan1(Drawable d, Context context, String sid, String badge, int align) {
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
     * 绘制等级
     * 只有1位和2位等级，处理ids size()=2 和 3的情况，因为前面还添加了LV.
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        int transY = 0;
        Paint.FontMetrics fm = paint.getFontMetrics();
        if(xiuAlign == ImageSpanAlign.XIU_ALIGN_CENTER) { // 居中对齐
            transY = (int) ((y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2);
        }
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();
        int startWidth = (int)(width * 0.3);

        // draw level
        Rect bLevel = new Rect();
        paint.getTextBounds(sid, 0, sid.length(), bLevel);
        int xl = (width - (width - startWidth) - bLevel.width()) / 2;
        int yl = (height + bLevel.height()) / 2 - 1;
        paint.setColor(Color.rgb(0, 0, 255));
        canvas.drawRect(new Rect(xl, yl - bLevel.height(), xl + bLevel.width(), yl + bLevel.bottom), paint);

        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
        canvas.drawText(sid, xl , yl , paint);

        // draw badge
        Rect bounds = new Rect();
        paint.getTextBounds(badge, 0, badge.length(), bounds);
        int x1 = (width - startWidth- bounds.width()) / 2 + startWidth;
        int y1 = (height + bounds.height()) / 2 - 1;
        paint.setColor(Color.rgb(0, 0, 255));
        canvas.drawRect(new Rect(x1, y1 - bLevel.height(), x1 + bounds.width(), y1 + bounds.bottom), paint);

        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
        canvas.drawText(badge, x1 , y1 , paint);

        canvas.restore();
    }
}
