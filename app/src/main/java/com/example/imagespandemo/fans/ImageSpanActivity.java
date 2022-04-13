package com.example.imagespandemo.fans;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

/**
 * ImageSpan 测试
 * 测试字符串含有系统表情时，部分能显示的手机，粉丝团徽章内容往下移动的问题
 * 只需要修正KiwiiFansBadgeImageSpan
 *         ty = (height + bounds.height()) / 2 - ScreenUtils.dip2px(3f);
 * ScreenUtils.dip2px 里面的值就可以了，原来是1.2f
 */
public class ImageSpanActivity extends AppCompatActivity {

    private EditText edtvTest;
    private TextView tvTest;
    private TextView tvTest1;
    private TextView tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagespan);
        edtvTest = findViewById(R.id.edtv_test);
        tvTest = findViewById(R.id.tv_test);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest3 = findViewById(R.id.tv_test3);
    }

    /**
     * ImageSpan 测试
     * @param v
     */
    public void onTest1(View v) {
        SpannableString fansImageSpan = SpanUtils.getInterFansBadgeImageSpan(this
                , "1"
                , edtvTest.getText().toString()
                , ScreenUtils.dip2px(this, ScreenUtils.INTER_IMG_CHAT_NORMAL_HEIGHT), ScreenUtils.KIWII_ALIGN_CENTER);
        tvTest.setText(fansImageSpan);
    }

    /**
     * ImageSpan 测试
     * @param v
     */
    public void onTest2(View v) {
        SpannableString fansImageSpan = SpanUtils.getTestImageSpan(this
                , "1"
                , edtvTest.getText().toString()
                , ScreenUtils.dip2px(this, ScreenUtils.INTER_IMG_CHAT_NORMAL_HEIGHT), ScreenUtils.KIWII_ALIGN_CENTER);
        tvTest1.setText(fansImageSpan);
        System.out.println("=================> TextView width: " + tvTest1.getWidth() + "   height: " + tvTest1.getHeight());

    }


    /**
     * 95xiu 粉丝团徽章相关测试
     * @param v
     */
    public void onTest3(View v) {
        SpannableString fansImageSpan = SpanUtils.getFansBadgeImageSpan(this
                , "9"
                , edtvTest.getText().toString()
                , ScreenUtils.dip2px(this, ScreenUtils.INTER_IMG_CHAT_NORMAL_HEIGHT), ScreenUtils.KIWII_ALIGN_CENTER);
        tvTest3.setText(fansImageSpan);
        System.out.println("=================> TextView width: " + tvTest3.getWidth() + "   height: " + tvTest3.getHeight());

    }
    
}
