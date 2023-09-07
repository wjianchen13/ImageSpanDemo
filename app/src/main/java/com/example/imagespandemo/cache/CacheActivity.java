package com.example.imagespandemo.cache;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;
import com.example.imagespandemo.gradient.GradientTextView;
import com.example.imagespandemo.gradient.LinearGradientFontSpan;
import com.example.imagespandemo.utils.LogUtils;
import com.example.imagespandemo.utils.ScreenUtils;

/**
 * 动态生成TextView的缩略图，解决阿语渐变错乱问题
 *
 * 当语言是阿拉伯语从右向左显示的时候，TextView需要滚动，设置了singleLine = true这个属性
 * 渐变就会出现问题
 * 现在解决的办法就是先实例化一个TextView，这个TextView是没有设置singleLine = true这个属性的
 * 然后从TextView生成图片，再把图片当做富文本设置到需要滚动的TextView中
 *
 */
public class CacheActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private GradientTextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;
    private ImageView imgvTest;
    private MarqueeTextView tvMarqueeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);
        btnTest3 = findViewById(R.id.btn_test3);
        imgvTest = findViewById(R.id.imgv_test);
        tvMarqueeTextView = findViewById(R.id.tv_user_name);
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

    private Bitmap bp;

    /**
     *
     * @param v
     */
    public void onTest4(View v) {
        String str = "hello11111111111hello11111111111bbbbbbbbbbbbb";
        SpannableString sb = SpannableUtils.getVipNameClick("", str, 0);
        bp = BitmapUtils.createGiftBitmap(this, sb, false,
                ScreenUtils.dip2px(this, 20), ContextCompat.getColor(this, R.color.c2176f5), Gravity.CENTER);
        int width = bp.getWidth();
        int height = bp.getHeight();
        LogUtils.log("width: " + width + "   height: " + height);
        imgvTest.setImageBitmap(bp);
    }

    /**
     *
     * @param v
     */
    public void onTest5(View v) {
        SpannableStringBuilder sb = SpannableUtils.getUserBadge(this, bp);
        tvMarqueeTextView.setContent(sb);
    }

}
