package com.example.imagespandemo.test1;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;

/**
 * 用户等级徽章
 */
public class TestActivity1 extends AppCompatActivity {

    private EditText edtvTest;
    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        edtvTest = findViewById(R.id.edtv_test);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * ImageSpan 测试
     * @param v
     */
    public void onTest2(View v) {
        try {
            int level = Integer.valueOf(edtvTest.getText().toString());
            SpannableString fansImageSpan1 = UserLevelSpanUtils.getUserLevelImageSpan1(this, level);
            tvTest1.setText(fansImageSpan1);
            System.out.println("=================> TextView width: " + tvTest1.getWidth() + "   height: " + tvTest1.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_SHORT).show();
        }

    }
    
}
