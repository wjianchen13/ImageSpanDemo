package com.example.imagespandemo.imagespan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 绘制粉丝等级
 */
public class FansImageSpan extends ImageSpan {

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
    private SoftReference<Drawable> mDrawableRef;

    public FansImageSpan(Drawable d, Context context, String sid, int align) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiuAlign = align;
        if (!TextUtils.isEmpty(sid)) {
            controlNumber(sid);
        }
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
        int leftLen = 0;
        int topLen = 0;
        if (Ids != null && Ids.size() > 0) {
            if (Ids.size() == 2) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                int startWidth = (int)(width * 0.3);
//                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
//                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
//                int desHeight = des1.getHeight();
//                leftLen = (width - startWidth - (des1.getWidth() + des2.getWidth())) / 2 + startWidth;
//                topLen = (height - desHeight) / 2;
//                canvas.drawBitmap(des1, leftLen, topLen, paint);
//                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);

//                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);



                Rect bounds = new Rect();
                String gText = "LV.1";
                paint.getTextBounds(gText, 0, gText.length(), bounds);
                int x1 = (width - startWidth- bounds.width()) / 2 + startWidth;
                int y1 = (height + bounds.height())/2 - 1;
                paint.setColor(Color.rgb(0, 0, 255));
                canvas.drawRect(new Rect(x1, 11, x1 + bounds.width(), y1 + bounds.bottom), paint);

                paint.setColor(Color.rgb(255, 255, 255));
                paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
                canvas.drawText(gText, x1 , y1 , paint);

            } else if (Ids.size() == 4) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                int startWidth = (int)(width * 0.318);
                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
                int desHeight = des1.getHeight();
                leftLen = (width - startWidth - (des1.getWidth() + des2.getWidth())) / 2 + startWidth;
                topLen = (height - desHeight) / 2;
                canvas.drawBitmap(des1, leftLen, topLen, paint);
                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);
            } else if(Ids.size() == 3) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                int startWidth = (int)(width * 0.318);
                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
                Bitmap des3 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(2));
                int desHeight = des1.getHeight();
                leftLen = (width - startWidth - (des1.getWidth() + des2.getWidth() + des3.getWidth())) / 2 + startWidth;
                topLen = (height - desHeight) / 2;
                canvas.drawBitmap(des1, leftLen, topLen, paint);
                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);
                canvas.drawBitmap(des3, leftLen + des1.getWidth() + des2.getWidth(), topLen, paint);
            } else {
                for (int i = 0; i < Ids.size(); i++) {
                    if (i == 0) {
                        leftLen = ScreenUtils.dip2px(mContext, 3);
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                        leftLen += des.getWidth();
                    } else if (i == Ids.size() - 1) {
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                    } else {
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                        leftLen += des.getWidth();
                    }
                }
            }
        }
        canvas.restore();
    }

    /**
     * 处理对应等级的图片加入图片列表
     */
    private void controlNumber(String sid) {
        String[] nums = sid.split("");
        Ids = new ArrayList<>();
        Ids.add(R.drawable.ic_fans_level_lv);
        for (int i = 1; i < nums.length; i ++) {
            int num = Integer.valueOf(nums[i]);
            switch (num) {
                case 0:
                    Ids.add(R.drawable.ic_fans_level_0);
                    break;
                case 1:
                    Ids.add(R.drawable.ic_fans_level_1);
                    break;
                case 2:
                    Ids.add(R.drawable.ic_fans_level_2);
                    break;
                case 3:
                    Ids.add(R.drawable.ic_fans_level_3);
                    break;
                case 4:
                    Ids.add(R.drawable.ic_fans_level_4);
                    break;
                case 5:
                    Ids.add(R.drawable.ic_fans_level_5);
                    break;
                case 6:
                    Ids.add(R.drawable.ic_fans_level_6);
                    break;
                case 7:
                    Ids.add(R.drawable.ic_fans_level_7);
                    break;
                case 8:
                    Ids.add(R.drawable.ic_fans_level_8);
                    break;
                case 9:
                    Ids.add(R.drawable.ic_fans_level_9);
                    break;
            }
        }
    }
}
