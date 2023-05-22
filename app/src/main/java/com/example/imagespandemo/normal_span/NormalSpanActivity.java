package com.example.imagespandemo.normal_span;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagespandemo.R;

/**
 * 富文本相关
 */
public class NormalSpanActivity extends Activity implements View.OnClickListener {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_span);
        tvTest = findViewById(R.id.tv_test);
        testClickSpan();
        initVertical();
    }

    private void testClickSpan() {
//        tvTest.setHighlightColor(Color.TRANSPARENT);
//        tvTest.setMovementMethod(LinkMovementMethod.getInstance());
//        tvTest.setText(SpanUtils.getClickSpanText(this,
//                "ClickableSpan点击测试",
//                R.color.colorAccent,
//                this,
//                new UserInfo(1, "test", 1),
//                false));

//        tvTest.setText(SpanUtils.getLinearGradientSpanText("ClickableSpan  渐变测试",
//                ContextCompat.getColor(this, R.color.colorAccent),
//                ContextCompat.getColor(this, R.color.red)));

        tvTest.setHighlightColor(Color.TRANSPARENT);
        tvTest.setMovementMethod(LinkMovementMethod.getInstance());
        tvTest.setText(SpanUtils.getGradientClickSpanText("ClickableSpan 渐变点击测试",
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.black),
                this,
                new UserInfo(1, "test", 1)));

    }

    @Override
    public void onClick(View v) {
        if(v != null) {
            if(v.getTag() != null && v.getTag() instanceof UserInfo) {
                UserInfo info = (UserInfo)v.getTag();
                Toast.makeText(this, "name: " + info.getName() + "  sex: " + info.getSex(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initVertical(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_vertical);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++){
            dataset[i] = "item" + i;
        }
        MyAdapter adapter = new MyAdapter(this, dataset, this);
        recyclerView.setAdapter(adapter);
    }

}
