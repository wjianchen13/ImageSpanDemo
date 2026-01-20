package com.example.imagespandemo.test15;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.LruCache;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 昵称内容混排工具类（纯Java、无循环、高性能）
 *
 * 使用示例：
 * <pre>
 * SpannableStringBuilder span = NicknameContentSpan.create(
 *     context,
 *     "超长昵称",
 *     "消息内容",
 *     100  // 昵称最大100dp
 * );
 * textView.setText(span);
 * </pre>
 */
public class NicknameContentSpan {

    private static final String TAG = "NicknameContentSpan";
    private static final int CACHE_SIZE = 200;

    // 缓存：昵称 -> 截断后的昵称
    private static final LruCache<String, String> sCache = new LruCache<>(CACHE_SIZE);

    private static boolean sDebugMode = false;

    /**
     * 配置类
     */
    public static class Config {
        public int maxNicknameWidthDp = 120;
        public float textSizeSp = 14f;
        @ColorInt public int nicknameColor = Color.BLUE;
        @ColorInt public int contentColor = Color.BLACK;
        public boolean nicknameBold = false;
        public boolean contentBold = false;
        @Nullable public Typeface typeface = null;
        @NonNull public String ellipsis = "...";
        @NonNull public String separator = " ";
        public boolean enableCache = true;

        public Config() {}

        public Config(int maxNicknameWidthDp, float textSizeSp) {
            this.maxNicknameWidthDp = maxNicknameWidthDp;
            this.textSizeSp = textSizeSp;
        }
    }

    /**
     * 创建昵称+内容的Span（简化版）
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content) {
        return create(context, nickname, content, new Config());
    }

    /**
     * 创建昵称+内容的Span（指定宽度）
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp) {
        return create(context, nickname, content, maxNicknameWidthDp, 14f);
    }

    /**
     * 创建昵称+内容的Span（指定宽度和字号）
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            int maxNicknameWidthDp,
            float textSizeSp) {
        Config config = new Config(maxNicknameWidthDp, textSizeSp);
        return create(context, nickname, content, config);
    }

    /**
     * 创建昵称+内容的Span（完整配置）
     */
    @NonNull
    public static SpannableStringBuilder create(
            @NonNull Context context,
            @NonNull String nickname,
            @NonNull String content,
            @NonNull Config config) {

        try {
            // 1. 参数验证
            if (nickname.isEmpty() && content.isEmpty()) {
                logDebug("昵称和内容都为空");
                return new SpannableStringBuilder();
            }

            // 2. 创建Paint
            TextPaint paint = createPaint(context, config);

            // 3. 截断昵称（核心逻辑，无循环）
            String ellipsizedNickname = ellipsizeNickname(
                    nickname, paint, config, context);

            // 4. 构建Span
            return buildSpan(ellipsizedNickname, content, config);

        } catch (Exception e) {
            Log.e(TAG, "创建Span失败", e);
            // 降级方案
            return new SpannableStringBuilder(nickname + config.separator + content);
        }
    }

    /**
     * 只截断昵称，不添加样式
     */
    @NonNull
    public static String ellipsizeNicknameOnly(
            @NonNull Context context,
            @NonNull String nickname,
            int maxNicknameWidthDp,
            float textSizeSp) {

        Config config = new Config(maxNicknameWidthDp, textSizeSp);
        TextPaint paint = createPaint(context, config);
        return ellipsizeNickname(nickname, paint, config, context);
    }

    /**
     * 创建Paint对象
     */
    @NonNull
    private static TextPaint createPaint(@NonNull Context context, @NonNull Config config) {
        TextPaint paint = new TextPaint();

        // 设置字体大小（sp转px）
        paint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                config.textSizeSp,
                context.getResources().getDisplayMetrics()
        ));

        // 设置字体
        if (config.typeface != null) {
            paint.setTypeface(config.typeface);
        }

        // 如果昵称加粗，Paint也要加粗
        if (config.nicknameBold) {
            paint.setTypeface(Typeface.create(paint.getTypeface(), Typeface.BOLD));
        }

        // 抗锯齿
        paint.setAntiAlias(true);

        return paint;
    }

    /**
     * 截断昵称（核心方法，完全无循环）
     */
    @NonNull
    private static String ellipsizeNickname(
            @NonNull String nickname,
            @NonNull TextPaint paint,
            @NonNull Config config,
            @NonNull Context context) {

        if (nickname.isEmpty()) {
            return nickname;
        }

        // 检查缓存
        if (config.enableCache) {
            String cacheKey = buildCacheKey(nickname, paint, config, context);
            String cached = sCache.get(cacheKey);
            if (cached != null) {
                logDebug("命中缓存: " + nickname);
                return cached;
            }
        }

        // dp转px
        float maxWidthPx = config.maxNicknameWidthDp *
                context.getResources().getDisplayMetrics().density;

        // 测量原始宽度
        float originalWidth = paint.measureText(nickname);

        logDebug(String.format(
                "截断昵称: %s, 原始宽度: %.2fpx, 限制: %.2fpx",
                nickname, originalWidth, maxWidthPx));

        // 如果不需要截断，直接返回
        if (originalWidth <= maxWidthPx) {
            logDebug("不需要截断");
            cacheResult(nickname, nickname, paint, config, context);
            return nickname;
        }

        // ✅ 核心：使用Android原生API，完全无循环
        CharSequence ellipsized = TextUtils.ellipsize(
                nickname,
                paint,
                maxWidthPx,
                TextUtils.TruncateAt.END
        );

        String result = ellipsized.toString();

        // 验证结果
        float ellipsizedWidth = paint.measureText(result);
        logDebug(String.format(
                "截断结果: %s, 宽度: %.2fpx",
                result, ellipsizedWidth));

        // 存入缓存
        cacheResult(nickname, result, paint, config, context);

        return result;
    }

    /**
     * 构建缓存Key
     */
    @NonNull
    private static String buildCacheKey(
            @NonNull String nickname,
            @NonNull Paint paint,
            @NonNull Config config,
            @NonNull Context context) {

        float widthPx = config.maxNicknameWidthDp *
                context.getResources().getDisplayMetrics().density;
        int typefaceHash = config.typeface != null ? config.typeface.hashCode() : 0;

        return String.format("%s-%d-%d-%d",
                nickname,
                (int) widthPx,
                (int) paint.getTextSize(),
                typefaceHash);
    }

    /**
     * 缓存结果
     */
    private static void cacheResult(
            @NonNull String nickname,
            @NonNull String result,
            @NonNull Paint paint,
            @NonNull Config config,
            @NonNull Context context) {

        if (config.enableCache) {
            String cacheKey = buildCacheKey(nickname, paint, config, context);
            sCache.put(cacheKey, result);
        }
    }

    /**
     * 构建SpannableStringBuilder
     */
    @NonNull
    private static SpannableStringBuilder buildSpan(
            @NonNull String ellipsizedNickname,
            @NonNull String content,
            @NonNull Config config) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        // 添加昵称
        if (!ellipsizedNickname.isEmpty()) {
            builder.append(ellipsizedNickname);
        }

        // 添加分隔符
        if (!ellipsizedNickname.isEmpty() && !content.isEmpty()) {
            builder.append(config.separator);
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
                    new ForegroundColorSpan(config.nicknameColor),
                    0,
                    nicknameEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            // 昵称加粗
            if (config.nicknameBold) {
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
                    (!ellipsizedNickname.isEmpty() ? config.separator.length() : 0);
            int contentEnd = builder.length();

            // 内容颜色
            builder.setSpan(
                    new ForegroundColorSpan(config.contentColor),
                    contentStart,
                    contentEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            // 内容加粗
            if (config.contentBold) {
                builder.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        contentStart,
                        contentEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return builder;
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        sCache.evictAll();
        logDebug("缓存已清空");
    }

    /**
     * 获取缓存信息
     */
    @NonNull
    public static String getCacheInfo() {
        return String.format("缓存大小: %d, 命中: %d, 未命中: %d",
                sCache.size(),
                sCache.hitCount(),
                sCache.missCount());
    }

    /**
     * 开启调试模式
     */
    public static void enableDebug(boolean enable) {
        sDebugMode = enable;
    }

    /**
     * 调试日志
     */
    private static void logDebug(String message) {
        if (sDebugMode) {
            Log.d(TAG, message);
        }
    }
}