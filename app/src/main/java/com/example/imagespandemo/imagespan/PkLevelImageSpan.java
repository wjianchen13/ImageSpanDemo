package com.example.imagespandemo.imagespan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * 绘制粉丝等级
 */
public class PkLevelImageSpan extends ImageSpan {

    /**
     * 正常
     */
    public static final int MODE_NORMAL = 1;

    /**
     * 直播间段位等级
     */
    public static final int MODE_LIVE = 2;

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

    private String name;

    private int level;

    private int grade;

    /**
     * 区分不同的段位等级
     */
    private int mode = MODE_NORMAL;

    private SoftReference<Drawable> mDrawableRef;

    public PkLevelImageSpan(Drawable d, Context context, String name, int level, int grade, int align, int mode) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        xiuAlign = align;
        this.name = name;
        this.level = level;
        this.grade = grade;
        this.mode = mode;
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
        if(mode == MODE_NORMAL) {
            Drawable b = getCachedDrawable();
            float transY = ((bottom - top) - b.getBounds().bottom) / 2.0f + top; // 居中显示
            canvas.save();
//            paint.setColor(Color.rgb(0, 0, 255)); // 模拟测试
//            canvas.drawRect(new Rect((int) x, 0, getDrawable().getBounds().width() + (int) x, bottom), paint);

            canvas.translate(x, transY);
            b.draw(canvas);

            int width = getDrawable().getBounds().width();
            int height = getDrawable().getBounds().height();

            Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), getPkIcon(level));
            int desWidth = des.getWidth();
            int desHeight = des.getHeight();
            int topLen = (height - desHeight) / 2;
            int leftLen = (width - desWidth) / 2;
            paint.setAlpha(255);
            canvas.drawBitmap(des, 0, topLen, paint);

//        // draw level
            Rect bLevel = new Rect();
//            String str = "钻石 Ⅲ";
            String str = name;
            paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
            paint.getTextBounds(str, 0, str.length(), bLevel);
            int xl = ScreenUtils.dip2px(mContext, 30);
            float yl = (float)(height + bLevel.height()) / 2 - ScreenUtils.dip2px(mContext, 1.5f);
            int yt = (height + bLevel.height()) / 2;
//            paint.setColor(Color.rgb(0, 0, 255));
//            canvas.drawRect(new Rect(xl, yt - bLevel.height(), xl + bLevel.width(), yt + bLevel.bottom), paint);

            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawText(str, xl, yl, paint);
            float x2 = xl + (float)bLevel.width() * 7 / 6; // 添加空格
            drawGrade(canvas, grade, x2, height, paint);


//            Bitmap grade = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_rank_pk_grade);
//            int gWidth = grade.getWidth();
//            int gHeight = grade.getHeight();
//            float gLeft = x2;
//            float gTop = (height - gHeight) / 2;
//            canvas.drawBitmap(grade, gLeft, gTop, paint);

            canvas.restore();
        } else if(mode == MODE_LIVE) {
//            canvas.save();
            Drawable b = getCachedDrawable();
            float transY = ((bottom - top) - b.getBounds().bottom) / 2.0f + top; // 居中显示
            canvas.save();
//            paint.setColor(Color.rgb(0, 0, 255)); // 模拟测试
//            canvas.drawRect(new Rect((int) x, 0, getDrawable().getBounds().width() + (int) x, bottom), paint);

            canvas.translate(x, transY);
            b.draw(canvas);

            int width = getDrawable().getBounds().width();
            int height = getDrawable().getBounds().height();

            Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), getLivePkIcon(grade));
            int desWidth = des.getWidth();
            int desHeight = des.getHeight();
            int leftLen = width - desWidth;
            int topLen = height - desHeight;
            paint.setAlpha(255);
            canvas.drawBitmap(des, leftLen, topLen, paint);
            canvas.restore();
        }
    }

    private static int getPkIcon(int level) {
        int res = 0;
        if(level == 0)
            res = R.drawable.ic_rank_pk_bronze;
        if(level == 1)
            res = R.drawable.ic_rank_pk_silver;
        if(level == 2)
            res = R.drawable.ic_rank_pk_gold;
        if(level == 3)
            res = R.drawable.ic_rank_pk_platinum;
        if(level == 4)
            res = R.drawable.ic_rank_pk_diamonds;
        if(level == 5)
            res = R.drawable.ic_rank_pk_star;
        if(level == 6)
            res = R.drawable.ic_rank_pk_king;
        return res;
    }

    private static int getLivePkIcon(int grade) {
        int res = 0;
        if(grade == 1)
            res = R.drawable.ic_rank_pk_grade_1;
        if(grade == 2)
            res = R.drawable.ic_rank_pk_grade_2;
        if(grade == 3)
            res = R.drawable.ic_rank_pk_grade_3;
        return res;
    }

    /**
     * 绘制等级
     * @param canvas
     * @param grade 等级
     * @param x 距离左侧距离
     * @param height 绘制区域总高度，用来计算绘制y坐标
     * @param paint
     */
    private void drawGrade(Canvas canvas, int grade, float x, int height, Paint paint) {
        float newX = x;
        if(grade <= 0)
            return ;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_rank_pk_grade);
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        float gTop = (height - bHeight) / 2;
        for(int i = 0; i < grade; i ++) {
            canvas.drawBitmap(bitmap, newX, gTop, paint);
            newX += bWidth;
        }
    }
}


//    int xl = (width - ScreenUtils.dip2px(mContext, 30) - bLevel.width()) / 2 + ScreenUtils.dip2px(mContext, 30);