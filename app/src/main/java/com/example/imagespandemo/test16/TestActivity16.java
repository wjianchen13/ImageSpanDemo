package com.example.imagespandemo.test16;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;
import com.example.imagespandemo.test15.NicknameContentSpan;
import com.example.imagespandemo.test15.NicknameSpanUtil;

/**
 * 测试实现动画图文混排的情况，AI处理
 */
public class TestActivity16 extends AppCompatActivity {

    private TextView tvTest;
    private String mUrl1 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private String mUrl2 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";
    private String mUrl3 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/1a421e749f86e4d77e1553586b94c68a.webp";
    private String mUrl4 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private String mUrl5 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test16);
        tvTest = findViewById(R.id.tv_test);
    }

    /**
     *
     */
    public void onTest1(View v) {

    }

    /**
     *
     */
    public void onTest2(View v) {

    }



}
