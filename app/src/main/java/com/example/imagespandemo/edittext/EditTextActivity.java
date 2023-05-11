package com.example.imagespandemo.edittext;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;
import com.example.imagespandemo.imagespan.ImageSpanAlign;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

public class EditTextActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private TextView tvTest1;
    private TextView tvTest2;
    private EditText edtvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);
        btnTest3 = findViewById(R.id.btn_test3);
        edtvTest = findViewById(R.id.edtv_test);
    }

    /**
     *
     * @param v
     */
    public void onTest1(View v) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("hello");
        sb.append(SpanUtils.getBadgeImageSpan(this, Integer.toString(1),  ScreenUtils.dip2px(this, 17), ImageSpanAlign.XIU_ALIGN_CENTER));
        sb.append("很好说");
        tvTest1.setText(sb);
        edtvTest.setText(sb);
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {

    }

}
