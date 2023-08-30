package com.example.imagespandemo.at;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sunhapper.x.spedit.mention.span.BreakableSpan;
import com.sunhapper.x.spedit.mention.span.IntegratedBgSpan;

////IntegratedSpan 完整性不会破坏，删除时整个内容全部删除
//public class AtUserSpan implements IntegratedSpan {
//
//    public String name;
//    public long id;
//
//    public AtUserSpan(String name) {
//        this.name = name;
//    }
//
//    public Spannable getSpannableString() {
//        SpannableString spannableString = new SpannableString(getDisplayText());
//        spannableString.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, spannableString.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(this, 0, spannableString.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
//        return stringBuilder.append(spannableString).append(" ");
//    }
//
//    private CharSequence getDisplayText() {
//        return "@" + name + " ";
//    }
//}

public class AtUserSpan implements BreakableSpan, DataSpan, IntegratedBgSpan {

    private long uid;
    private String name;
    private boolean isShow = false;
    private ForegroundColorSpan styleSpan = null;
    private BackgroundColorSpan bgSpan = null;

    public AtUserSpan(long uid, String name) {
        this.uid = uid;
        if(name != null)
            name = name.replace(" ", "");
        this.name = name + " ";
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public Spannable getSpannableString() {
        CharSequence displayText = getDisplayText();
        styleSpan = new ForegroundColorSpan(Color.GREEN);
        SpannableString spannableString = new SpannableString(displayText);
        spannableString.setSpan(styleSpan, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(this, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        return stringBuilder.append(spannableString);
    }

    private CharSequence getDisplayText() {
        return "\u200e" + "@" + name + "\u200e";
    }

    private CharSequence getDisplayText1() {
        StringBuilder sb = new StringBuilder(name);
        for(int i = sb.length() - 1; i >= 0; i --) {
            sb.insert(i, "\u200e");
        }
        return sb;
    }

//    private CharSequence getDisplayText() {
//        return "@" + name;
//    }

    @NonNull
    @Override
    public String toString() {
        return "MentionUser{name=}" + name;
    }

    @Override
    public boolean isBreak(@NonNull Spannable spannable) {
        if (spannable != null) {
            CharSequence displayText = getDisplayText();
            int spanStart = spannable.getSpanStart(this);
            int spanEnd = spannable.getSpanEnd(this);

            boolean isBreak = spanStart >= 0 && spanEnd >= 0 && !TextUtils.isEmpty(displayText) && !displayText.equals(spannable.subSequence(spanStart, spanEnd).toString());
            if (isBreak && styleSpan != null) {
                spannable.removeSpan(styleSpan);
                styleSpan = null;
            }
            return isBreak;
        }
        return false;
    }

    @Nullable
    @Override
    public BackgroundColorSpan getBgSpan() {
        return null;
    }

    @Override
    public boolean isShow() {
        return isShow;
    }

    @Override
    public void setShow(boolean b) {
        this.isShow = b;
    }

    @Override
    public void setBgSpan(@Nullable BackgroundColorSpan backgroundColorSpan) {
        this.bgSpan = backgroundColorSpan;
    }

    @NonNull
    @Override
    public BackgroundColorSpan generateBgSpan() {
        return new BackgroundColorSpan(Color.YELLOW);
    }

    @Override
    public void removeBg(@NonNull Spannable spannable) {
        isShow = false;
        if(bgSpan != null && spannable != null)
            spannable.removeSpan(bgSpan);
    }

    @NonNull
    @Override
    public BackgroundColorSpan createStoredBgSpan(@NonNull IntegratedBgSpan integratedBgSpan) {
        bgSpan = generateBgSpan();
        return bgSpan;
    }
}
