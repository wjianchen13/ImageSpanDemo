package com.example.imagespandemo.gradient;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;

/**
 * 渐变TextView
 */
public class GradientActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private GradientTextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);
        btnTest3 = findViewById(R.id.btn_test3);
    }

    /**
     *
     * @param v
     */
    public void onTest1(View v) {
        tvTest1.setGradientText("helloasdfasdfasdfasdfasdfsadfaaaaa");
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {
        String s = "abcdefghijklmn";
//        SpannableString str = VipManagerV2.getInstance().getVipName(s, 5, R.color.ccbcbcb);
        tvTest2.setText(getGradientSpan(s, Color.BLUE, Color.RED, true));
//        tvTest2.invalidate();
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
     * @param v
     */
    public void onTest3(View v) {

    }

}
