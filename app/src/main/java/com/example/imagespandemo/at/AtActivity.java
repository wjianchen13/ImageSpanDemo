package com.example.imagespandemo.at;

import static com.sunhapper.x.spedit.SpUtilKt.insertSpannableString;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @某人功能
 */
public class AtActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private TextView tvTest1;
    private TextView tvTest2;
    private EditText edtvTest;
    private TextView tvTest;
    private String atUser;
    private String content;
    private List<MentionUserBean> mentionList;
    private RoomMentionManager mMentionManager;

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
        tvTest = findViewById(R.id.tv_test);
        if (mentionList == null) {
            mentionList = new ArrayList<>();
        }
        mMentionManager = new RoomMentionManager();
    }

    /**
     *
     * @param v
     */
    public void onTest1(View v) {
//        SpannableStringBuilder sb = new SpannableStringBuilder();
//        sb.append("hello");
//        sb.append(SpanUtils.getBadgeImageSpan(this, Integer.toString(1),  ScreenUtils.dip2px(this, 17), ImageSpanAlign.XIU_ALIGN_CENTER));
//        sb.append("很好说");
//        tvTest1.setText(sb);
//        edtvTest.setText(sb);
//        replace(new AtUserSpan("Ismail ShaOJ5753").getSpannableString());

//        replace(new AtUserSpan(1, "العشب1252").getSpannableString());
//        replace(new AtUserSpan(2, "abc").getSpannableString());
        replace(new AtUserSpan(3, "\uD83D\uDC3B\uD83D\uDC36اعتنق اتعامل 56wwwwwtest11").getSpannableString());
    }

    private void replace(CharSequence charSequence) {
        Editable editable = edtvTest.getText();
        insertSpannableString(editable, charSequence);
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {
        Editable editable = edtvTest.getText();
        mMentionManager.initSendMsg(editable);
        content = mMentionManager.getContent();
        log("content: " + content);
        atUser = mMentionManager.getAtUser();
        log("atUser: " + atUser);
    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {
        String a = "@" + "abc" + "\u200E";
        log("a: " + a);

        String b = a.replace("\u200E", "");
        log("b: " + b);
    }

    /**
     *
     * @param v
     */
    public void onTest4(View v) {
        mMentionManager.initReceiveMsg(content, atUser);
        String rContent = mMentionManager.getrContent();
        tvTest.setText(rContent);
    }

    /**
     *
     * @param v
     */
    public void onTest5(View v) {

    }

    public static void log(String str) {
        System.out.println("=======================> " + str);
    }

}
