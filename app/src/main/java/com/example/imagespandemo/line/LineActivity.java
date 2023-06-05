package com.example.imagespandemo.line;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imagespandemo.R;
import com.example.imagespandemo.imagespan.ImageSpanAlign;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

public class LineActivity extends AppCompatActivity {

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private TextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
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
        sContent.append(VipManagerV2.getInstance().getVipNameClick(this, "img", "wwwwwwwwwwwwwwawwwwwwwwww\nwwwwm", 5, null, null, R.color.ccbcbcb));
        sContent.append(" ");
        sContent.append(SpannableUtils.getColorAndSizeTestSpan(this, getString(R.string.enter_the_room), R.dimen.dp_13, R.color.cf4adff));
        tvTest1.setText(sContent);
    }



    /**
     *
     * @param v
     */
    public void onTest2(View v) {
        initAutoSplitTextView();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(SpannableUtils.getImageSpan(this, R.drawable.ic_chat_msg));
        sContent.append(" ");
        sContent.append(VipManagerV2.getInstance().getVipNameClick(this, "img", "wwwwwwwwwwwwwwawwwwwwwwwwwwwwm", 5, null, null, R.color.ccbcbcb));
        sContent.append(" ");
        sContent.append(SpannableUtils.getColorAndSizeTestSpan(this, getString(R.string.enter_the_room), R.dimen.dp_13, R.color.cf4adff));
        tvTest2.setText(sContent);
    }

    private void initAutoSplitTextView() {
        tvTest2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvTest2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final CharSequence newText = autoSplitText(tvTest2);
                if (!TextUtils.isEmpty(newText)) {
                    tvTest2.setText(newText);
                }
            }
        });
    }

    //返回CharSequence对象
    private CharSequence autoSplitText(final TextView tv) {
        //tv.getText(), 原始的CharSequence内容.
        CharSequence charSequence = tv.getText();
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
//        tvPaint.setTextSize(24);

        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 13;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        //对于有富文本的情况
        if (charSequence instanceof Spanned) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
            if (sbNewText.toString().contains("\n")) {
                String[] split = sbNewText.toString().split("\n");
                int tempIndex = 0;
                for (int i = 0; i < split.length; i++) {
                    if (i != split.length - 1) {
                        String s = split[i];
                        tempIndex = tempIndex + s.length() + i;
                        spannableStringBuilder.insert(tempIndex, "\n");
                    }
                }
            }
            return spannableStringBuilder;
        } else {
            return sbNewText;
        }

    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(SpannableUtils.getImageSpan(this, R.drawable.ic_chat_msg));
        sContent.append(" ");
        sContent.append(VipManagerV2.getInstance().getVipNameClick(this, "img ", "wawwwwwwwwwwwwwawwwwwwwwwwwwwwm", 5, null, null, R.color.ccbcbcb));
        sContent.append(" ");
        sContent.append(SpannableUtils.getColorAndSizeTestSpan(this, getString(R.string.enter_the_room), R.dimen.dp_13, R.color.cf4adff));
        CharSequence tb = sContent.subSequence("img ".length(), sContent.length());
        CharSequence cs = autoSplitText1(sContent, 520);
        tvTest3.setText(cs);


    }

    //返回CharSequence对象
    private CharSequence autoSplitText1(CharSequence txt, int width) {
        if(width == 0)
            return txt;
        TextView tv = new TextView(this);
        tv.setDrawingCacheEnabled(true);
        float sp = getResources().getDimension(R.dimen.dp_13);
        float textSize = sp / ScreenUtils.getDensity(this);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        tv.setGravity(Gravity.CENTER);
        tv.setIncludeFontPadding(false);
//        emTextView.setText(name);
        final Paint tvPaint1 = tv.getPaint(); //paint，包含字体等信息
//        tvPaint1.setTextSize(24);


        //tv.getText(), 原始的CharSequence内容.
        CharSequence charSequence = txt;
        final String rawText = charSequence.toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        tvPaint.setTextSize(24);

        final float tvWidth = width; //控件可用宽度

        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        //对于有富文本的情况
        if (charSequence instanceof Spanned) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
            if (sbNewText.toString().contains("\n")) {
                String[] split = sbNewText.toString().split("\n");
                int tempIndex = 0;
                for (int i = 0; i < split.length; i++) {
                    if (i != split.length - 1) {
                        String s = split[i];
                        tempIndex = tempIndex + s.length() + i;
                        spannableStringBuilder.insert(tempIndex, "\n");
                    }
                }
            }
            return spannableStringBuilder;
        } else {
            return sbNewText;
        }

    }



}
