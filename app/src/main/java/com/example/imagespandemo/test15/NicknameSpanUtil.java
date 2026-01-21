package com.example.imagespandemo.test15;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * 昵称内容Span工具类
 *
 * 使用示例：
 * // 方式1：最简单
 * SpannableStringBuilder span = NicknameSpanUtil.create(context, "昵称", "内容");
 * textView.setText(span);
 *
 * // 方式2：指定最大宽度
 * SpannableStringBuilder span = NicknameSpanUtil.create(context, "昵称", "内容", 100);
 *
 * // 方式3：完整配置
 * SpannableStringBuilder span = NicknameSpanUtil.create(
 *     context, "昵称", "内容", 100, 14f, Color.BLUE, Color.WHITE, true
 * );
 */
public class NicknameSpanUtil {

    /**
     * 创建昵称+内容Span（最简单，使用默认配置）
     *
     * @param context 上下文
     * @param nickname 昵称
     * @param content 内容
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content) {
        return create(context, nickname, content, 100, 14f, Color.BLUE, Color.BLACK, false);
    }

    /**
     * 创建昵称+内容Span（指定最大宽度）
     *
     * @param context 上下文
     * @param nickname 昵称
     * @param content 内容
     * @param maxNicknameWidthDp 昵称最大宽度（dp）
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp) {
        return create(context, nickname, content, maxNicknameWidthDp, 14f, Color.BLUE, Color.BLACK, false);
    }

    /**
     * 创建昵称+内容Span（指定宽度和字号）
     *
     * @param context 上下文
     * @param nickname 昵称
     * @param content 内容
     * @param maxNicknameWidthDp 昵称最大宽度（dp）
     * @param textSizeSp 文字大小（sp）
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp,
            float textSizeSp) {
        return create(context, nickname, content, maxNicknameWidthDp, textSizeSp, Color.BLUE, Color.BLACK, false);
    }

    /**
     * 创建昵称+内容Span（指定颜色）
     *
     * @param context 上下文
     * @param nickname 昵称
     * @param content 内容
     * @param maxNicknameWidthDp 昵称最大宽度（dp）
     * @param textSizeSp 文字大小（sp）
     * @param nicknameColor 昵称颜色
     * @param contentColor 内容颜色
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp,
            float textSizeSp,
            @ColorInt int nicknameColor,
            @ColorInt int contentColor) {
        return create(context, nickname, content, maxNicknameWidthDp, textSizeSp, nicknameColor, contentColor, false);
    }

    /**
     * 创建昵称+内容Span（完整配置）
     *
     * @param context 上下文
     * @param nickname 昵称
     * @param content 内容
     * @param maxNicknameWidthDp 昵称最大宽度（dp）
     * @param textSizeSp 文字大小（sp）
     * @param nicknameColor 昵称颜色
     * @param contentColor 内容颜色
     * @param nicknameBold 昵称是否加粗
     * @return SpannableStringBuilder
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp,
            float textSizeSp,
            @ColorInt int nicknameColor,
            @ColorInt int contentColor,
            boolean nicknameBold) {

        // 参数验证
        if (nickname.isEmpty() && content.isEmpty()) {
            return new SpannableStringBuilder();
        }

        // 创建Paint
        TextPaint paint = new TextPaint();
        paint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                textSizeSp,
                context.getResources().getDisplayMetrics()
        ));
        paint.setAntiAlias(true);

        if (nicknameBold) {
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        // 截断昵称
        String ellipsizedNickname = ellipsizeNickname(nickname, paint, maxNicknameWidthDp, context);

        // 构建Span
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // 添加昵称
        if (!ellipsizedNickname.isEmpty()) {
            builder.append(ellipsizedNickname);
        }

        // 添加空格分隔符
        if (!ellipsizedNickname.isEmpty() && !content.isEmpty()) {
            builder.append(" ");
        }

        // 添加内容
        if (!content.isEmpty()) {
            builder.append(content);
        }

        // 应用昵称样式
        if (!ellipsizedNickname.isEmpty()) {
            int nicknameEnd = ellipsizedNickname.length();

            // 昵称颜色
            builder.setSpan(
                    new ForegroundColorSpan(nicknameColor),
                    0,
                    nicknameEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            // 昵称加粗
            if (nicknameBold) {
                builder.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        0,
                        nicknameEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        // 应用内容样式
        if (!content.isEmpty()) {
            int contentStart = ellipsizedNickname.length() +
                    (!ellipsizedNickname.isEmpty() ? 1 : 0);  // +1是空格

            builder.setSpan(
                    new ForegroundColorSpan(contentColor),
                    contentStart,
                    builder.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        return builder;
    }

    /**
     * 截断昵称（核心方法）
     */
    @NonNull
    private static String ellipsizeNickname(
            @NonNull String nickname,
            @NonNull TextPaint paint,
            int maxNicknameWidthDp,
            @NonNull Context context) {

        if (nickname.isEmpty()) {
            return nickname;
        }

        // dp转px
        float maxWidthPx = maxNicknameWidthDp * context.getResources().getDisplayMetrics().density;

        // 测量原始宽度
        float originalWidth = paint.measureText(nickname);

        // 如果不需要截断，直接返回
        if (originalWidth <= maxWidthPx) {
            return nickname;
        }

        // 使用Android原生API截断
        CharSequence ellipsized = TextUtils.ellipsize(
                nickname,
                paint,
                maxWidthPx,
                TextUtils.TruncateAt.END
        );

        return ellipsized.toString();
    }
}