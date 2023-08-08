package com.example.imagespandemo.ellipsize;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;
import com.example.imagespandemo.line.SpannableUtils;
import com.example.imagespandemo.line.VipManagerV2;
import com.example.imagespandemo.utils.ScreenUtils;

public class EllipsizeActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private TextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ellipsize);
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
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(SpannableUtils.getImageSpan(this, R.drawable.ic_chat_msg));
        sContent.append(" ");
        sContent.append(VipManagerV2.getInstance().getVipNameClick(this, "img", "abcdefghijklmnopqrstuvwxyz123456789", 5, null, null, R.color.ccbcbcb));
//        sContent.append(VipManagerV2.getInstance().getVipNameClick(this, "img", "abcdefghij", 5, null, null, R.color.ccbcbcb));
        sContent.append(" ");
        sContent.append(SpannableUtils.getColorAndSizeTestSpan(this, getString(R.string.enter_the_room), R.dimen.dp_13, R.color.cf4adff));
        tvTest1.setText(sContent);
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {
        getCount();
    }

    private String getCount() {
        // 判断TextView是否设置了省略号
        if (tvTest1.getEllipsize() == null) {
            return tvTest1.getText().toString();
        }

        // 获取TextView的Layout对象
        Layout layout = tvTest1.getLayout();

        // 获取TextView的文本
        CharSequence text = tvTest1.getText();

        // 判断文本是否超出了TextView的宽度
        int lineCount = layout.getLineCount();
        if (lineCount > 0 && layout.getEllipsisCount(lineCount - 1) > 0) {
            // 获取省略号的位置
            int ellipsisStart = layout.getEllipsisStart(lineCount - 1);
            int ellipsisEnd = layout.getEllipsisCount(lineCount - 1);

            // 获取省略号之前的文本
            CharSequence ellipsizedText = text.subSequence(0, ellipsisStart);

            // 拼接省略号和省略后缀
            String ellipsis = TextUtils.ellipsize("", tvTest1.getPaint(), ellipsisEnd, TextUtils.TruncateAt.END).toString();
            return ellipsizedText + ellipsis + tvTest1.getEllipsize().toString();
        } else {
            return tvTest1.getText().toString();
        }
    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {
        if(tvTest3 != null) {
            tvTest3.post(new Runnable() {
                @Override
                public void run() {
                    DynamicLayout dynamicLayout = new DynamicLayout(
                            "abcdefghijklmnopqrstuvwxyz123456789123456789123456789",
                            tvTest3.getPaint(),
                            520,
                            tvTest3.getLayout().getAlignment(),
                            tvTest3.getLineSpacingMultiplier(),
                            tvTest3.getLineSpacingExtra(),
                            true
                    );

                    int lineCount = dynamicLayout.getLineCount();
                    if(lineCount > 0) {
                            int ellipsisStart = dynamicLayout.getLineStart(0);
                            int ellipsisEnd = dynamicLayout.getLineEnd(0);
                            CharSequence lineText = dynamicLayout.getText().subSequence(ellipsisStart, ellipsisEnd);

                            float lineWidth = dynamicLayout.getLineWidth(0);
                            // 处理每一行的宽度
                            if (lineWidth > tvTest3.getWidth()) {
                                // 该行超出了TextView的宽度
                            } else {
                                // 该行未超出TextView的宽度
                            }
                    }
                    tvTest3.setText(dynamicLayout.getText());
                }
            });
        }
        
    }

}
