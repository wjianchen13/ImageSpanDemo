package com.example.imagespandemo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SpanActivity1 extends AppCompatActivity {

    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span1);
        tvTest1 = findViewById(R.id.tv_test);
    }

    /**
     *
     */
    public void onTest1(View v) {
        String name = "abc";
        String car = "bcd";
        String str = getResources().getString(R.string.span_test, name, car);
        SpannableString spanText = new SpannableString(str);
        int start1 = str.indexOf(name);
        int len1 = name.length();
        int start2 = str.indexOf(car);
        int len2 = car.length();
        spanText.setSpan(new ForegroundColorSpan(Color.RED), start1, start1 + len1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.RED), start2, start2 + len2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        tvTest5.append("\n");
        tvTest1.append(spanText);
    }

    /**
     *
     */
    public void onTest2(View v) {

    }

}
