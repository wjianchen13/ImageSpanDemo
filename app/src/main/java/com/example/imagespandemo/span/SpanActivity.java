package com.example.imagespandemo.span;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;

public class SpanActivity extends AppCompatActivity {

    private TextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;
    private TextView tvTest4;
    private TextView tvTest5;
    private TextView tvTest6;
    private TextView tvTest7;
    private TextView tvTest8;
    private TextView tvTest9;
    private TextView tvTest10;
    private TextView tvTest11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        tvTest4 = findViewById(R.id.tv_test4);
        tvTest5 = findViewById(R.id.tv_test5);
        tvTest6 = findViewById(R.id.tv_test6);
        tvTest7 = findViewById(R.id.tv_test7);
        tvTest8 = findViewById(R.id.tv_test8);
        tvTest9 = findViewById(R.id.tv_test9);
        tvTest10 = findViewById(R.id.tv_test10);
        tvTest11 = findViewById(R.id.tv_test11);
        test1();
        test5();
        test6();
        test7();
    }

    /**
     *
     */
    private void test1() {
        SpannableString spanText = new SpannableString("背景文字");
        spanText.setSpan(new BackgroundColorSpan(Color.GREEN), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTest1.append("\n");
        tvTest1.append(spanText);
    }

    /**
     *
     */
    private void test2() {
//        SpannableString spStr = new SpannableString("点击的文字");
//        ClickSpan clickSpan = new NoLineClickSpan(vo); //设置超链接
//        spStr.setSpan(clickSpan, 0, spStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        tvTest2.append(spStr);
//        tvTest2.setMovementMethod(LinkMovementMethod.getInstance());
//        //设置超链接为可点击状态
//        tvTest2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * RasterizerSpan 光栅效果
     */
    private void test5() {
//        SpannableString spanText = new SpannableString("StrikethroughSpan");
//        spanText.setSpan(new RasterizerSpan(), 0, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        tvTest5.append("\n");
//        tvTest5.append(spanText);
    }

    private void test6() {
        String content="MaskFilterSpan";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);


        MaskFilter filter = new BlurMaskFilter(4.0f,BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan=new MaskFilterSpan(filter);

        AbsoluteSizeSpan span = new AbsoluteSizeSpan(30);
        stringBuilder.setSpan(span, 0, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE), 0, stringBuilder.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(maskFilterSpan,0,content.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTest6.setText(stringBuilder);
    }

    private void test7() {
        SpannableString spanText = new SpannableString("MaskFilterSpan ");
        int length = spanText.length();
        //模糊(BlurMaskFilter)
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER));
        spanText.setSpan(maskFilterSpan, 0, length - 10, Spannable.
                SPAN_INCLUSIVE_EXCLUSIVE);
        //浮雕(EmbossMaskFilter)
        maskFilterSpan = new MaskFilterSpan(new EmbossMaskFilter(new float[]{1,1,3}, 1.5f, 8, 3));
        spanText.setSpan(maskFilterSpan, length - 10, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTest7.append("\n");
        tvTest7.append(spanText);
    }
}
