package com.example.imagespandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.at.AtActivity;
import com.example.imagespandemo.cache.CacheActivity;
import com.example.imagespandemo.ellipsize.EllipsizeActivity;
import com.example.imagespandemo.fans.ImageSpanActivity;
import com.example.imagespandemo.gradient.GradientActivity;
import com.example.imagespandemo.line.LineActivity;
import com.example.imagespandemo.normal_span.NormalSpanActivity;
import com.example.imagespandemo.shadow.ShadowActivity;
import com.example.imagespandemo.span.SpanActivity;
import com.example.imagespandemo.tag.TagActivity;
import com.example.imagespandemo.test1.TestActivity1;
import com.example.imagespandemo.test15.TestActivity15;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest1(View v) {
        Intent it = new Intent();
        it.setClass(this, FansBadgeActivity.class);
        startActivity(it);
    }

    public void onTest2(View v) {
        Intent it = new Intent();
        it.setClass(this, FansBadgeActivity1.class);
        startActivity(it);
    }

    public void onTest3(View v) {
        Intent it = new Intent();
        it.setClass(this, PkBadgeActivity.class);
        startActivity(it);
    }

    /**
     * 粉丝团徽章
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, ImageSpanActivity.class));
    }

    public void onTest5(View v) {
        startActivity(new Intent(this, SpanActivity.class));
    }

    /**
     * EditText显示Span效果，@用户
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, AtActivity.class));
    }

    /**
     * 普通Span
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, NormalSpanActivity.class));
    }

    /**
     * 富文本自动换行
     * @param v
     */
    public void onTest8(View v) {
        startActivity(new Intent(this, LineActivity.class));
    }

    /**
     * 富文本获取省略实际内容
     * @param v
     */
    public void onTest9(View v) {
        startActivity(new Intent(this, EllipsizeActivity.class));
    }

    /**
     * 渐变字体方案
     * @param v
     */
    public void onTest10(View v) {
        startActivity(new Intent(this, GradientActivity.class));
    }

    /**
     * 动态生成TextView的缩略图，解决阿语渐变错乱问题
     * @param v
     */
    public void onTest11(View v) {
        startActivity(new Intent(this, CacheActivity.class));
    }

    /**
     * 动态设置span，解决不同语言语序不一致的问题
     * @param v
     */
    public void onTest12(View v) {
        startActivity(new Intent(this, SpanActivity1.class));
    }

    /**
     * span 标签，按钮
     * @param v
     */
    public void onTest13(View v) {
        startActivity(new Intent(this, TagActivity.class));
    }

    /**
     * 用户等级徽章
     * @param v
     */
    public void onTest14(View v) {
        startActivity(new Intent(this, TestActivity1.class));
    }

    /**
     * Span根据宽度自动截取字符串
     * @param v
     */
    public void onTest15(View v) {
        startActivity(new Intent(this, TestActivity15.class));
    }




}
