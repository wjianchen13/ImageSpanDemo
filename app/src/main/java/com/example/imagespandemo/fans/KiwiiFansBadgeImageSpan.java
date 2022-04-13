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

/**
 * 绘制粉丝徽章
 */
public class KiwiiFansBadgeImageSpan extends ImageSpan {
    
    private Context mContext;
    
    /**
     * 右边距离不写文字,dp
     */
    private static final int INTER_MARGIN_RIGHT = 8;
    /**
     * 徽章宽度，dp
     */
    private static final int INTER_BADGE_WIDTH = 25;

    /**
     * background引用
     */
    private SoftReference<Drawable> drawable_ref;

    /**
     * badge des
     */
    private final String badge;
    /**
     * 背景图宽度
     */
    private final int background_width;
    /**
     * sid
     */
    private final String sid;
    /**
     * text bounds
     */
    private final Rect bounds;
    /**
     * 背景图高度
     */
    private final int background_height;
    /**
     * 对齐方式，目前只有居中对齐
     */
    private final int xiu_align;

    private Drawable getCachedDrawable() {
        SoftReference<Drawable> wr = drawable_ref;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            drawable_ref = new SoftReference<>(d);  // 1 + 1 = 2  1 - 0.25 = 0.75  1+0.25 =  -1.25
        }
        return d;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 12));
        d.setBounds(0, 0, background_width + bounds.width(), background_height);
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

    public KiwiiFansBadgeImageSpan(Context context, Drawable d, String sid, String badge, int align, int width, int height) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiu_align = align;
        this.sid = sid;
        this.badge = badge;
        this.background_width = width;
        this.background_height = height;
        bounds = new Rect();
    }

    /**
     * 绘制span到画布上
     *
     * @param canvas 将被渲染的span canvas
     * @param text 当前文本
     * @param start span开始字符索引
     * @param end span结束字符索引
     * @param x 要绘制的image的左边框到textview左边框的距离
     * @param top 替换行的最顶部位置.
     * @param y 要替换的文字的基线坐标，即基线到textview上边框的距离
     * @param bottom 替换行的最底部位置.
     * @param paint Paint instance.
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if(sid == null || badge == null)
            return ;
        Drawable b = getCachedDrawable();
        int transY = 0;
        Paint.FontMetrics fm = paint.getFontMetrics();
        if(xiu_align == ScreenUtils.KIWII_ALIGN_CENTER) { // 居中对齐
            transY = (int) ((y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2) ;
        }
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();

        // draw level
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 10));
        paint.getTextBounds(sid, 0, sid.length(), bounds);
        int tx = (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) - bounds.width()) / 2 - ScreenUtils.dip2px(mContext, 0.5f);
        int ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 0.0f);
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(sid, tx , ty , paint);

        // draw badge
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 12));
        tx = ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + (width - (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + ScreenUtils.dip2px(mContext, INTER_MARGIN_RIGHT) + bounds.width())) / 2;
        ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 3f);
//        drawRect(canvas, paint, tx, ty);
        canvas.drawText(badge, tx , ty , paint);

        canvas.restore();
    }
    
    private void drawRect(Canvas canvas, Paint paint, int tx, int ty) {
        paint.setColor(Color.rgb(255, 0, 0));
        canvas.drawRect(new Rect(tx, ty - bounds.height(), tx + bounds.width(), ty), paint);
        paint.setColor(Color.rgb(255, 255, 255));
    }

    private void getTextBounds(Paint paint, int textSize) {
        if(paint != null && bounds != null) {
            paint.setTextSize(textSize);
            paint.getTextBounds(badge, 0, badge.length(), bounds);
        }
    }
}

