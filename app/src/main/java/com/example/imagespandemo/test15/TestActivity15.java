package com.example.imagespandemo.test15;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
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

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test15);
        tvTest = findViewById(R.id.tv_test);
    }

    /**
     *
     */
    public void onTest1(View v) {
        // 开启调试
        NicknameContentSpan.enableDebug(true);
        
        tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
//        tvTest.setBackgroundColor(Color.BLACK);
//        tvTest.setPadding(20, 20, 20, 20);

        // 测试各种场景
        SpannableStringBuilder result = new SpannableStringBuilder();

        // 测试1: 短昵称
        result.append(NicknameContentSpan.create(this, "短", "内容"));
        result.append("\n\n");

        // 测试2: 中等昵称
        result.append(NicknameContentSpan.create(this, "中等长度昵称", "消息内容"));
        result.append("\n\n");

        // 测试3: 超长昵称
        result.append(NicknameContentSpan.create(this, "超级无敌霹雳长的昵称一定会被截断", "消息"));
        result.append("\n\n");

        // 测试4: Emoji
        result.append(NicknameContentSpan.create(this, "😀😀😀😀😀😀😀😀😀😀", "emoji测试"));
        result.append("\n\n");

        // 测试5: 自定义配置
        NicknameContentSpan.Config config = new NicknameContentSpan.Config();
        config.maxNicknameWidthDp = 150;
        config.nicknameBold = true;
        config.separator = ": ";
        result.append(NicknameContentSpan.create(this, "自定义配置", "加粗+冒号", config));

        tvTest.setText(result);

        // 延迟查看缓存信息
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Log.d("Cache", NicknameContentSpan.getCacheInfo());
        }, 1000);

    }

    /**
     *
     */
    public void onTest2(View v) {
        // 默认配置：昵称最大100dp，14sp，蓝色昵称，白色内容
        SpannableStringBuilder span = NicknameSpanUtil.create(
                this,
                "超长的昵称会被截断超长的昵称会被截断",
                "这是消息内容"
        );
        tvTest.setText(span);
    }



}
