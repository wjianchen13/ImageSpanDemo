package com.example.imagespandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.edittext.EditTextActivity;
import com.example.imagespandemo.fans.ImageSpanActivity;
import com.example.imagespandemo.normal_span.NormalSpanActivity;
import com.example.imagespandemo.span.SpanActivity;

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
        startActivity(new Intent(this, EditTextActivity.class));
    }

    /**
     * EditText显示Span效果，@用户
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, NormalSpanActivity.class));
    }

}
