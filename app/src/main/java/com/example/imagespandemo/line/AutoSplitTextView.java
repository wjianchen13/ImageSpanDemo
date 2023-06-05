package com.example.imagespandemo.line;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AutoSplitTextView extends androidx.appcompat.widget.AppCompatTextView {

    public AutoSplitTextView(Context context) {
        super(context);
    }

    public AutoSplitTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSplitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final CharSequence newText = autoSplitText(this);
        if (!TextUtils.isEmpty(newText)) {
            setText(newText);
        }
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    //返回CharSequence对象
    private CharSequence autoSplitText(final TextView tv) {
        //tv.getText(), 原始的CharSequence内容.
        CharSequence charSequence = tv.getText();
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
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
}
