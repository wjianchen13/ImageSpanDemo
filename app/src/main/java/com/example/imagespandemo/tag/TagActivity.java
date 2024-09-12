package com.example.imagespandemo.tag;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;

public class TagActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imgvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        textView = findViewById(R.id.tv_test);
        imgvTest  = findViewById(R.id.imgv_test1);

    }

    /**
     *
     */
    public void onTest1(View v) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("Android是一种基于Linux的自由及开放源代码的操作作码");
        sb.append(SpanUtils.getTagSpan(this, "置顶置顶", false, R.color.c2176f5, new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(TagActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        }, 1));

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(sb);
    }

    /**
     *
     */
    public void onTest2(View v) {
//        ((TextView)textView).setHighlightColor(getResources().getColor(android.R.color.transparent));
//        SpannableStringBuilder sb = new SpannableStringBuilder();
//        sb.append("Android是一种基于Linux的自由及开放源代码的操作作码");
//        sb.append(SpanUtils.getJoinImageSpan(this, "置顶", new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(TagActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        }, 1));
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setText(sb);

        ((TextView)textView).setHighlightColor(getResources().getColor(android.R.color.transparent));
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("Android是一种基于Linux的自由及开放源代码的操作作码");
        sb.append(SpanUtils.getSizeColorClick_Txt(this, "Join now >>",
                R.dimen.frame_sp_13, R.color.c_ll_fffff72b, false, new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TagActivity.this, "click", Toast.LENGTH_SHORT).show();
                    }
                }, 1));
//        sb.append(SpanUtils.getJoinImageSpan(this, "置顶", new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(TagActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        }, 1));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(sb);

    }

}
