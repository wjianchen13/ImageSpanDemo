package com.example.imagespandemo.test1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;

import java.lang.ref.SoftReference;

/**
 * 用户等级图标
 */
public class UserLevelImageSpan extends ImageSpan {

    /**
     * 一位字体大小
     */
    private static final int DEFAULT_TEXT_SIZE_ONE = 25;


    /**
     * 2位字体大小
     */
    private static final int DEFAULT_TEXT_SIZE_TWO = 25;

    /**
     * 描边大小
     */
    private static final int DEFAULT_STROKE_WIDTH = 4;

    /**
     * context
     */
    private Context mContext;

    /**
     * 等级
     */
    private int mLevel;

    private Rect bLevel = new Rect();

    private Paint mStrokePaint;

    private Paint mTextPaint;

    /**
     * 字体大小
     */
    private int mTextSize = 0;

    /**
     * 描边大小
     */
    private int mStrokeWidth = 0;

    /**
     * 字体渐变
     */
    private LinearGradient mTextGradient = null;

    private SoftReference<Drawable> mDrawableRef;

    private Rect mRectBounds = null;

    /**
     * 描边渐变
     */
    private LinearGradient mStrokeGradient = null;

    /**
     * 字体颜色是否渐变
     */
    private boolean isTextColorGradient = false;

    /**
     * 描边颜色
     */
    private int[] mStrokeGradientColor;

    private int mTextHeight = 0;
    private int mTextWidth = 0;

    /**
     * Image 宽度
     */
    private int mWidth;

    /**
     * Image 高度
     */
    private int mHeight;

    /**
     * 绘制字体相对底部的偏移
     */
    private int mOffset = -2;

    /**
     * 字体颜色，不是渐变的时候时候
     */
    @ColorInt
    private int mTextColor = 0;

    /**
     * 等级显示距离图标右侧的边距
     */
    private int mMarginRight = 0;

    public UserLevelImageSpan(Drawable d, Context context, int level) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        this.mLevel = level;
        mStrokeWidth = DEFAULT_STROKE_WIDTH;
    }

    public UserLevelImageSpan(Drawable d, Context context, int level, @ColorInt int[] strokeGradientColor, @ColorInt int textColor) {
        super(d, ImageSpan.ALIGN_BASELINE);
        this.mContext = context;
        this.mLevel = level;
        mTextSize = level > 9 ? DEFAULT_TEXT_SIZE_TWO : DEFAULT_TEXT_SIZE_ONE;
        mStrokeWidth = DEFAULT_STROKE_WIDTH;
        mTextColor = textColor;
        if(d != null && d.getBounds() != null) {
            mWidth = d.getBounds().width();
            mHeight = d.getBounds().height();
        }
        mMarginRight = getRightMargin(level);
        initStrokePaint(strokeGradientColor);
        initTextPaint();
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
     * 获取各个等级距离右边的距离
     * @return
     */
    private int getRightMargin(int level) {
        if(level < 10) {
            return 13;
        } else if(level < 20) {
            return 11;
        } else if(level < 30) {
            return 9;
        } else if(level < 40) {
            return 9;
        } else if(level < 50) {
            return 7;
        } else if(level < 60) {
            return 5;
        } else if(level < 70) {
            return 5;
        }
        return 5;
    }

    private Drawable getCachedDrawable() {
        SoftReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new SoftReference<>(d);
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
        Drawable d = getCachedDrawable();
        if(d == null) {
            return ;
        }
        float transY = ((bottom - top) - d.getBounds().bottom) / 2.0f + top; // 居中显示
        canvas.save();
//            paint.setColor(Color.rgb(0, 0, 255)); // 模拟测试
//            canvas.drawRect(new Rect((int) x, 0, getDrawable().getBounds().width() + (int) x, bottom), paint);

        canvas.translate(x, transY);
        d.draw(canvas);
        drawStroke(canvas, d, mStrokePaint);
        drawText(canvas, d, mTextPaint);
        canvas.restore();
    }

    /**
     * 描边
     */
    private void drawStroke(Canvas canvas, Drawable b, Paint paint) {
        if(b != null && paint != null) {
            String content = String.valueOf(mLevel);
            if (!TextUtils.isEmpty(content)) {
                paint.getTextBounds(content, 0, content.length(), bLevel);
                int xl = mWidth - bLevel.width() - mMarginRight;
                float yl = mHeight - ScreenUtils.dip2px(mContext, 0f) + mOffset;
//            paint.setColor(Color.rgb(0, 0, 255));
//            canvas.drawRect(new Rect(xl, yt - bLevel.height(), xl + bLevel.width(), yt + bLevel.bottom), paint);
                canvas.drawText(content, xl, yl, paint);
            }
        }
    }

    /**
     * 描边
     */
    private void drawText(Canvas canvas, Drawable b, Paint paint) {
        if(b != null && paint != null) {
            String content = String.valueOf(mLevel);
            if (!TextUtils.isEmpty(content)) {
                paint.getTextBounds(content, 0, content.length(), bLevel);
                int xl = mWidth - bLevel.width() - mMarginRight;
                float yl = mHeight - ScreenUtils.dip2px(mContext, 0f) + mOffset;
//            paint.setColor(Color.rgb(0, 0, 255));
//            canvas.drawRect(new Rect(xl, yt - bLevel.height(), xl + bLevel.width(), yt + bLevel.bottom), paint);
                canvas.drawText(content, xl, yl, paint);
            }
        }
    }

    private void initStrokePaint(@ColorInt int[] strokeGradientColor) {
        mStrokePaint = new Paint();
        mStrokePaint.setColor(Color.BLACK);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mStrokeWidth);
        mStrokePaint.setFakeBoldText(true);
        mStrokePaint.setTextSize(mTextSize);
        if(strokeGradientColor != null && strokeGradientColor.length >= 2) {
            mStrokeGradientColor = strokeGradientColor;
        } else {
            mStrokeGradientColor = new int[] {ContextCompat.getColor(mContext, R.color.c_ll_ff00bfcb), ContextCompat.getColor(mContext, R.color.c_ll_ffffc735)};
        }
        initStrokeGradient();
    }

    private void initStrokeGradient() {
        getTextBounds();
        mStrokeGradient = new LinearGradient(0f, 0f + mHeight - mTextHeight, 0f, mHeight,
                mStrokeGradientColor, null, Shader.TileMode.CLAMP);
        mStrokePaint.setShader(mStrokeGradient);
    }

    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTextSize(mTextSize);
        initTextGradient();
    }

    private void initTextGradient() {
        if (isTextColorGradient) {
            mTextGradient = new LinearGradient(0f, 0f + 0, 0f, (mTextHeight - 0),
                    new int[] {ContextCompat.getColor(mContext, R.color.c_ll_ff00bfcb), ContextCompat.getColor(mContext, R.color.c_ll_ffffc735)},
                    null, Shader.TileMode.CLAMP);
            mTextPaint.setShader(mTextGradient);
        } else {
            mTextPaint.setShader(null);
            mTextPaint.setColor(mTextColor);
        }
        mStrokePaint.setShader(mStrokeGradient);
    }

    private void getTextBounds() {
        String mText = String.valueOf(mLevel);
        if (mText != null) {
            mRectBounds = new Rect();
            String text = mText.toString();
            mStrokePaint.getTextBounds(text, 0, mText.length(), mRectBounds);
            Paint.FontMetrics fm = mStrokePaint.getFontMetrics();
            if(fm != null) {
                int width = mRectBounds.width();
                int height = mRectBounds.height();
                mTextHeight = height;
                mTextWidth = (int)(Math.ceil(mStrokePaint.measureText(text)));
            }
        }
    }

}
