package com.example.imagespandemo.tag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.WeakReference;

/**
 * Tag ImageSpan
 */
public class CustomTagSpan extends ImageSpan {

    private final WeakReference<Context> mContext;

    private final String content;

    /**
     * 垂直方向的偏移
     */
    private int mVerticalOffset;

    private Rect rect;

    public CustomTagSpan(Drawable d, Context context, String content/*, int level*/) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = new WeakReference<>(context);
        this.content = content;
        rect = new Rect();
    }

    public CustomTagSpan(Drawable d, Context context, String content, int verticalOffset) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = new WeakReference<>(context);
        this.content = content;
        this.mVerticalOffset = verticalOffset;
        rect = new Rect();
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

        if (b == null || c == null) {
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            return;
        }

        float transY = ((bottom - top) - b.getBounds().bottom) / 2.0f + top + mVerticalOffset; // 居中显示
        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int width = getDrawable().getBounds().width();
        int height = getDrawable().getBounds().height();
        if (!TextUtils.isEmpty(content)) {
            if(rect == null)
                rect = new Rect();
            paint.setTextSize(ScreenUtils.dip2px(c, 12));

            paint.getTextBounds(content, 0, content.length(), rect);
            int xl = (width - rect.width()) / 2 - ScreenUtils.dip2px(c, 2);
            float yl = (float) (height + rect.height()) / 2 - ScreenUtils.dip2px(c, 0.5f);
            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawText(content, xl, yl, paint);
        }
        canvas.restore();
    }

}