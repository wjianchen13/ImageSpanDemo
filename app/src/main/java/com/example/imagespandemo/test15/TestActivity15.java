package com.example.imagespandemo.test15;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.gradient.LinearGradientFontSpan;
import com.example.imagespandemo.span.ShadowSpan;

/**
 * Span根据宽度自动截取字符串
 */
public class TestActivity15 extends AppCompatActivity {

    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test15);
        tvTest1 = findViewById(R.id.tv_test);
    }

    /**
     *
     */
    public void onTest1(View v) {
//        tvTest1.setText(getShadowSpan("hello", Color.BLUE, Color.RED, true));
        tvTest1.setText(getGradientSpan("hello", Color.BLUE, Color.RED, true));
    }

    /**
     * 动态设置TextView文字的横向或纵向渐变色
     * @param string
     * @param startColor
     * @param endColor
     * @return
     */
    public SpannableStringBuilder getShadowSpan(String string, int startColor, int endColor, boolean isLeftToRight) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        ShadowSpan span = new ShadowSpan(ContextCompat.getColor(this, R.color.cf8d539), ContextCompat.getColor(this, R.color.cfe652c), Color.BLUE, 2, 2.0f, 2.0f);
//        GradientFontSpan span = new GradientFontSpan(startColor, endColor);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        // 若有需要可以在这里用SpanString系列的其他类，给文本添加下划线、超链接、删除线...等等效果
        return spannableStringBuilder;
    }

    /**
     * 动态设置TextView文字的横向或纵向渐变色
     * @param string
     * @param startColor
     * @param endColor
     * @return
     */
    public static SpannableStringBuilder getGradientSpan(String string, int startColor, int endColor, boolean isLeftToRight) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        LinearGradientFontSpan span = new LinearGradientFontSpan(startColor, endColor, isLeftToRight);
//        GradientFontSpan span = new GradientFontSpan(startColor, endColor);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        // 若有需要可以在这里用SpanString系列的其他类，给文本添加下划线、超链接、删除线...等等效果
        return spannableStringBuilder;
    }

    /**
     *
     */
    public void onTest2(View v) {

    }

}
