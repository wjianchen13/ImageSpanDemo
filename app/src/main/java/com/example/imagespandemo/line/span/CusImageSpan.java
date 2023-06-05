package com.example.imagespandemo.line.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class CusImageSpan extends ImageSpan {

    private Rect rect = new Rect();
    private int xiuAlign;

    public CusImageSpan(Context context, Bitmap b, int align) {
        super(context, b, ImageSpan.ALIGN_BASELINE);
        xiuAlign = align;
    }

    public CusImageSpan(Drawable d, int align) {
        super(d, ImageSpan.ALIGN_BASELINE);
        xiuAlign = align;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        if (d != null) {
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;  //字体高度
                int drHeight = rect.bottom - rect.top; //图片高度
                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 3;
                fm.ascent = -bottom; //字体的高度 descent - leading
                fm.top = -bottom;  //
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }
        return super.getSize(paint, text, start, end, fm);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            return;
        }
        int transY = 0;
        Paint.FontMetrics fm = paint.getFontMetrics();
        paint.getTextBounds("我", 0, 1, rect);
        if (xiuAlign == ImageSpanConstants.IMAGE_SPAN_ALIGN_CENTER) { // 居中对齐
            transY = (int) ((y + fm.descent + y + fm.ascent) / 2 - drawable.getBounds().bottom / 2);
        } else if (xiuAlign == ImageSpanConstants.IMAGE_SPAN_ALIGN_BOTTOM) { //  底部对齐
            transY = y - drawable.getBounds().bottom + rect.bottom - drawable.getBounds().top;
        }

        canvas.save();  //
        canvas.translate(x, transY);

        drawable.draw(canvas);
        canvas.restore();
    }

}