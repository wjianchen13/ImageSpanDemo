package com.example.imagespandemo.imagespan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
 * Created by admin on 2016/8/ic_fans_level_9.
 */
public class GoodNumberImageSpan2 extends ImageSpan {

    private Context mContext;
    private String sid;
    private List<Integer> Ids;
    private Rect rect = new Rect();
    private int xiuAlign;


    public GoodNumberImageSpan2(Drawable d, Context context, String sid, int align) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiuAlign = align;
        if (!TextUtils.isEmpty(sid)) {
            controlNumber(sid);
        }
    }

    private SoftReference<Drawable> mDrawableRef;


    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
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

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        int transY = 0;

        Paint.FontMetrics fm = paint.getFontMetrics();
        paint.getTextBounds("我",0, 1, rect);
        if(xiuAlign == ImageSpanAlign.XIU_ALIGN_CENTER) { // 居中对齐
            transY = (int) ((y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2);
        } else { //  底部对齐
            transY = y - b.getBounds().bottom + rect.bottom -  b.getBounds().top;
        }

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);

        int leftLen = 0;
        int topLen = 0;
        if (Ids != null && Ids.size() > 0) {

            if (Ids.size() == 1) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                int desWidth = des.getWidth();
                int desHeight = des.getHeight();
                topLen = (height - desHeight) / 2;
                leftLen = (width - desWidth) / 2;
                canvas.drawBitmap(des, leftLen, topLen, paint);
            } else if (Ids.size() == 2) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
                int desHeight = des1.getHeight();
                leftLen = (width - (des1.getWidth() + des2.getWidth())) / 2;
                topLen = (height - desHeight) / 2;
                canvas.drawBitmap(des1, leftLen, topLen, paint);
                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);
            } else if(Ids.size() == 3) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
                Bitmap des3 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(2));
                int desHeight = des1.getHeight();
                leftLen = (width - (des1.getWidth() + des2.getWidth() + des3.getWidth())) / 2;
                topLen = (height - desHeight) / 2;
                canvas.drawBitmap(des1, leftLen, topLen, paint);
                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);
                canvas.drawBitmap(des3, leftLen + des1.getWidth() + des2.getWidth(), topLen, paint);
            } else if(Ids.size() == 4) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                Bitmap des1 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(0));
                Bitmap des2 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(1));
                Bitmap des3 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(2));
                Bitmap des4 = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(3));
                int desHeight = des1.getHeight();
                leftLen = (width - (des1.getWidth() + des2.getWidth() + des3.getWidth() + des4.getWidth())) / 2;
                topLen = (height - desHeight) / 2;
                canvas.drawBitmap(des1, leftLen, topLen, paint);
                canvas.drawBitmap(des2, leftLen + des1.getWidth(), topLen, paint);
                canvas.drawBitmap(des3, leftLen + des1.getWidth() + des2.getWidth(), topLen, paint);
                canvas.drawBitmap(des4, leftLen + des1.getWidth() + des2.getWidth() + des3.getWidth(), topLen, paint);
            } else {
                for (int i = 0; i < Ids.size(); i++) {
                    if (i == 0) {
                        leftLen = ScreenUtils.dip2px(mContext, 3);
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                        leftLen += des.getWidth();
                    } else if (i == Ids.size() - 1) {
//                        leftLen += ApplicationUtil.dip2px(2);
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                    } else {
//                        leftLen += ApplicationUtil.dip2px(2);
                        Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                        canvas.drawBitmap(des, leftLen, ScreenUtils.dip2px(mContext, 3), paint);
                        leftLen += des.getWidth();
                    }
                }
            }
        }
        canvas.restore();
    }

    public int getTotalWidth() {
        int len = 0;
        if (Ids != null && Ids.size() > 0) {
            for (int i = 0; i < Ids.size(); i++) {
                Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), Ids.get(i));
                len += des.getWidth();
            }
            len += ScreenUtils.dip2px(mContext, 10) + ScreenUtils.dip2px(mContext, 26) + ScreenUtils.dip2px(mContext, 10);
        }
        return len;
    }

    private void controlNumber(String sid) {
        String[] nums = sid.split("");
        Ids = new ArrayList<>();
        for (int i = 1; i < nums.length; i ++) {
            int num = Integer.valueOf(nums[i]);
            switch (num) {
                case 0:
                    Ids.add(R.drawable.live_zero_2);
                    break;
                case 1:
                    Ids.add(R.drawable.live_one_2);
                    break;
                case 2:
                    Ids.add(R.drawable.live_two_2);
                    break;
                case 3:
                    Ids.add(R.drawable.live_three_2);
                    break;
                case 4:
                    Ids.add(R.drawable.live_four_2);
                    break;
                case 5:
                    Ids.add(R.drawable.live_five_2);
                    break;
                case 6:
                    Ids.add(R.drawable.live_six_2);
                    break;
                case 7:
                    Ids.add(R.drawable.live_sevent_2);
                    break;
                case 8:
                    Ids.add(R.drawable.live_eight_2);
                    break;
                case 9:
                    Ids.add(R.drawable.live_night_2);
                    break;
            }
        }
    }
}
