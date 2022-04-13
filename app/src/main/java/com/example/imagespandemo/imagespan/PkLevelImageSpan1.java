package com.example.imagespandemo.imagespan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.SoftReference;

/**
 * 绘制粉丝等级
 */
public class PkLevelImageSpan1 extends ImageSpan {

    /**
     * 王者
     */
    public static final int LEVEL_KING = 6;

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

    /**
     * segment://段位 1.青铜Ⅲ 2.青铜Ⅱ 3.青铜Ⅰ 4.白银Ⅲ 5.白银Ⅱ 6.白银Ⅰ 7.黄金Ⅲ 8.黄金Ⅱ 9.黄金Ⅰ 10.铂金Ⅲ 11.铂金Ⅱ 12.铂金Ⅰ 13.钻石Ⅲ 14.钻石Ⅱ 15.钻石Ⅰ 16.星耀Ⅲ 17.星耀Ⅱ 18.星耀Ⅰ 19.王者
     */
    public static SpannableString getPkImageSpan(Context context, String name, int segment, int mode) {
        if(segment < 0)
            return new SpannableString("img ");
        int level = (segment - 1) / 3;
        int grade = (segment - 1) / 3;
        int size = ScreenUtils.dip2px(context, mode == PkLevelImageSpan.MODE_NORMAL ? 29 : 20);
        SpannableString spanStr = null;
        if (context != null) {
            spanStr = new SpannableString("img ");
            Drawable img = mode == PkLevelImageSpan.MODE_NORMAL
                    ? ContextCompat.getDrawable(context, getPkBackground(level))
                    : createDrawable(context, getPkLevelIcon(level));
            int iHeight = img.getIntrinsicHeight();
            float factor = (float)size / iHeight;
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth() * factor), size);
            ImageSpan fansImageSpan = new PkLevelImageSpan1(img, context, name, level, grade, mode);
            spanStr.setSpan(fansImageSpan, 0, spanStr.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spanStr == null ? new SpannableString("") : spanStr;
    }

    public PkLevelImageSpan1(Drawable d, Context context, String name, int level, int grade, int mode) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
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

            int height = getDrawable().getBounds().height();

            Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), getPkLevelIcon(level));
            int desHeight = des.getHeight();
            int topLen = (height - desHeight) / 2;
            paint.setAlpha(255);
            canvas.drawBitmap(des, 0, topLen, paint);

//        // draw level
            if(!TextUtils.isEmpty(name)) {
                Rect bLevel = new Rect();
//            String str = "钻石Ⅲ";
//            String str = name + getGrade(grade);
                paint.setTextSize(ScreenUtils.dip2px(mContext, 12));
                paint.getTextBounds(name, 0, name.length(), bLevel);
                int xl = ScreenUtils.dip2px(mContext, level == LEVEL_KING ? 36 : 30);
                float yl = (float) (height + bLevel.height()) / 2 - ScreenUtils.dip2px(mContext, 1.5f);
                int yt = (height + bLevel.height()) / 2;
//            paint.setColor(Color.rgb(0, 0, 255));
//            canvas.drawRect(new Rect(xl, yt - bLevel.height(), xl + bLevel.width(), yt + bLevel.bottom), paint);

                paint.setColor(Color.rgb(255, 255, 255));
                canvas.drawText(name, xl, yl, paint);
            }
//            float x2 = xl + (float)bLevel.width() * 11 / 10; // 添加空格
//            drawGrade(canvas, grade, x2, height, paint);


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

            if(level < LEVEL_KING) {
                Bitmap des = BitmapFactory.decodeResource(mContext.getResources(), getLivePkGradeIcon(grade));
                int desWidth = des.getWidth();
                int desHeight = des.getHeight();
                int leftLen = width - desWidth;
                int topLen = height - desHeight;
                paint.setAlpha(255);
                canvas.drawBitmap(des, leftLen, topLen, paint);
                canvas.restore();
            }
        }
    }

    /**
     * 段位背景
     */
    private static int getPkBackground(int level) {
        int res = R.drawable.bg_rank_pk_bronze;
        if(level <= 0)
            res = R.drawable.bg_rank_pk_bronze;
        else if(level == 1)
            res = R.drawable.bg_rank_pk_silver;
        else if(level == 2)
            res = R.drawable.bg_rank_pk_gold;
        else if(level == 3)
            res = R.drawable.bg_rank_pk_platinum;
        else if(level == 4)
            res = R.drawable.bg_rank_pk_diamonds;
        else if(level == 5)
            res = R.drawable.bg_rank_pk_star;
        else if(level == 6)
            res = R.drawable.bg_rank_pk_king;
        else if(level > 6)
            res = R.drawable.bg_rank_pk_king;
        return res;
    }

    /**
     * 段位图标
     */
    private static int getPkLevelIcon(int level) {
        int res = R.drawable.ic_rank_pk_bronze;
        if(level <= 0)
            res = R.drawable.ic_rank_pk_bronze;
        else if(level == 1)
            res = R.drawable.ic_rank_pk_silver;
        else if(level == 2)
            res = R.drawable.ic_rank_pk_gold;
        else if(level == 3)
            res = R.drawable.ic_rank_pk_platinum;
        else if(level == 4)
            res = R.drawable.ic_rank_pk_diamonds;
        else if(level == 5)
            res = R.drawable.ic_rank_pk_star;
        else if(level == 6)
            res = R.drawable.ic_rank_pk_king;
        else if(level > 6)
            res = R.drawable.ic_rank_pk_king;
        return res;
    }

    /**
     * 直播间等级Icon
     */
    private static int getLivePkGradeIcon(int grade) {
        int res = R.drawable.ic_rank_pk_grade_3;
        if(grade == 0)
            res = R.drawable.ic_rank_pk_grade_3;
        if(grade == 1)
            res = R.drawable.ic_rank_pk_grade_2;
        if(grade == 2)
            res = R.drawable.ic_rank_pk_grade_1;
        return res;
    }

    /**
     * 生成新的drawable
     */
    private static Drawable createDrawable(Context mContext, int id) {
        int size = ScreenUtils.dip2px(mContext, 20);
        Drawable img = ContextCompat.getDrawable(mContext, id);
        int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
        int iWidth = img.getIntrinsicWidth();
        float factor;

        Bitmap target = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas temp_canvas = new Canvas(target);
        if(iHeight > iWidth) {
            factor = (float)size / iHeight;
            int left = (size - (int) (iWidth * factor)) / 2;
            img.setBounds(left, 0, (int) (iWidth * factor) + left, size);
        } else {
            factor = (float)size / iWidth;
            int top = (size - (int) (iHeight * factor)) / 2;
            img.setBounds(0, top, size, (int) (iHeight * factor) + top);
        }
        img.draw(temp_canvas);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), target);
        return drawable;
    }
}
