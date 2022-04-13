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
public class TestFansBadgeImageSpan extends ImageSpan {
    
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
        System.out.println("=================> background_width: " + background_width + "   background_height: " + background_height);
        System.out.println("=================> bounds.left111111: " + bounds.left + "   bounds.top:" + bounds.top + "   bounds.right:" + bounds.right + "   bounds.bottom:" + bounds.bottom);
        Rect rect = d.getBounds();
        return rect.right;
    }

    public TestFansBadgeImageSpan(Context context, Drawable d, String sid, String badge, int align, int width, int height) {
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
     * @param bottom 替换行的最底部位置.如果TextView是单行的话，这个bottom-top实际等于TextView的高度
     * @param paint Paint instance.
     * canvas.restore();这句代码放置到绘制完背景之后，在这之前的表示背景需要居中显示
     *              
     * paint.getTextBounds()方法可以获取实际字体的尺寸，启动0就对应基线y的位置。
     * top:基线到字体上边的距离
     * bottom：基线到字体下边的距离
     * canvas.drawText()方法是以文字的基准绘制文字的 
     * canvas.drawText(badge, tx , 45 , paint); 他是在坐标(tx, 45)这个点上开始绘制文字，对应的是文字的基线
     * 例如绘制 Fans，这个的效果是45的位置就是F的位置， 绘制 yyyg ，效果就是45的位置就只yyyg中间的位置。
     * Fans的bounds的bottom = 0  yyyg的bounds的bottom = -8（或其它数值，反正比0小，表示基线到字体底部的距离，应该和画笔大小这些有关系）              
     *              
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if(sid == null || badge == null)
            return ;
        Drawable b = getCachedDrawable();
        int transY = 0;
        Paint.FontMetrics fm = paint.getFontMetrics();
        if(xiu_align == ScreenUtils.KIWII_ALIGN_CENTER) { // 居中对齐
            transY = (bottom - background_height) / 2;
        }
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
        
        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();

        // draw level
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 10));
        paint.getTextBounds(sid, 0, sid.length(), bounds);
        int textHeight = bounds.bottom - bounds.top;
        int tx = (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) - bounds.width()) / 2 - ScreenUtils.dip2px(mContext, 0.5f);
        int ty = (bottom - textHeight) / 2 + textHeight - bounds.bottom;
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(sid, tx , ty , paint);

        // draw badge
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 12));
        tx = ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + (width - (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + ScreenUtils.dip2px(mContext, INTER_MARGIN_RIGHT) + bounds.width())) / 2;
        textHeight = bounds.bottom - bounds.top;
        ty = (bottom - textHeight) / 2 + textHeight - bounds.bottom;
        drawRect(canvas, paint, tx, ty, Color.rgb(0, 255, 0), fm, bounds.bottom);
        canvas.drawText(badge, tx , ty , paint);
        
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
//    @Override
    public void draw1(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if(sid == null || badge == null)
            return ;
        Drawable b = getCachedDrawable();
        int transY = 0;
        paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
        Paint.FontMetrics fm = paint.getFontMetrics();
        System.out.println("=================> start: " + start + "   end:" + end + "   x:" + x + "   top:" + top + "   y:" + y + "   bottom:" + bottom);
        if(xiu_align == ScreenUtils.KIWII_ALIGN_CENTER) { // 居中对齐
            System.out.println("=================> fm.ascent222: " + fm.ascent + "   fm.top:" + fm.top + "   fm.bottom:" + fm.bottom + "   fm.descent:" + fm.descent);
            transY = (bottom - background_height) / 2;
        }
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
        
//        System.out.println("=================> start: " + start + "   end:" + end + "   x:" + x + "   top:" + top + "   y:" + y + "   bottom:" + bottom);
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        System.out.println("=================> fm.ascent222: " + fm.ascent + "   fm.top:" + fm.top + "   fm.bottom:" + fm.bottom + "   fm.descent:" + fm.descent);
        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();

        // draw badge
        getTextBounds(paint, ScreenUtils.dip2px(mContext, 12));
        fm = paint.getFontMetrics();
        int tx = ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + (width - (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + ScreenUtils.dip2px(mContext, INTER_MARGIN_RIGHT) + bounds.width())) / 2;
        System.out.println("=================> bounds.left: " + bounds.left + "   bounds.top:" + bounds.top + "   bounds.right:" + bounds.right + "   bounds.bottom:" + bounds.bottom);
        System.out.println("=================> height: " + height);
        int textHeight = bounds.bottom - bounds.top;
        int ty = (bottom - textHeight) / 2 + textHeight - bounds.bottom; // 
//        drawRect(canvas, paint, tx, ty, Color.rgb(0, 255, 0), fm, bounds.bottom);
        canvas.drawText(badge, tx , ty , paint);
        

//        int width = getDrawable().getBounds().width();
//        int height = getDrawable().getBounds().height();
//
//        // draw level
//        getTextBounds(paint, ScreenUtils.dip2px(mContext, 10));
//        paint.getTextBounds(sid, 0, sid.length(), bounds);
//        int tx = (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) - bounds.width()) / 2 - ScreenUtils.dip2px(mContext, 0.5f);
//        int ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 0.0f);
//        paint.setColor(Color.rgb(255, 255, 255));
//        canvas.drawText(sid, tx , ty , paint);
//
//        // draw badge
//        getTextBounds(paint, ScreenUtils.dip2px(mContext, 12));
//        tx = ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + (width - (ScreenUtils.dip2px(mContext, INTER_BADGE_WIDTH) + ScreenUtils.dip2px(mContext, INTER_MARGIN_RIGHT) + bounds.width())) / 2;
//        System.out.println("=================> bounds.left: " + bounds.left + "   bounds.top:" + bounds.top + "   bounds.right:" + bounds.right + "   bounds.bottom:" + bounds.bottom);
//        System.out.println("=================> height: " + height);
//        ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(mContext, 2.0f);
//        drawRect(canvas, paint, tx, ty, Color.rgb(0, 255, 0));
//        canvas.drawText(badge, tx , ty , paint);


    }

    /**
     * 绘制文字区域
     * @param canvas
     * @param paint
     * @param tx
     * @param ty
     * @param color
     */
    private void drawRect(Canvas canvas, Paint paint, int tx, int ty, int color, Paint.FontMetrics fm, int des) {
        paint.setColor(color);
        canvas.drawRect(new Rect(tx, ty - (bounds.bottom - bounds.top) + des, tx + bounds.width(), ty + des), paint);
        paint.setColor(Color.rgb(255, 255, 255));
    }
    

    private void getTextBounds(Paint paint, int textSize) {
        if(paint != null && bounds != null) {
            paint.setTextSize(textSize);
            paint.getTextBounds(badge, 0, badge.length(), bounds);
        }
    }
}

