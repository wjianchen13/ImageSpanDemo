package com.example.imagespandemo.cache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class BitmapUtils {

    public static Bitmap createGiftBitmap(Activity activity, CharSequence content, boolean isBold, int textSize, int textColor, int textGravity) {
        TextView bitmapFactoryView = new TextView(activity.getApplicationContext());
        if(isBold) {
            bitmapFactoryView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
//        if (SwitchLanguageUtils.isArabLanguage()) {
//            bitmapFactoryView.setTextDirection(View.TEXT_DIRECTION_RTL);
//        } else {
            bitmapFactoryView.setTextDirection(View.TEXT_DIRECTION_LTR);
//        }
        bitmapFactoryView.setIncludeFontPadding(false);
        bitmapFactoryView.setVisibility(View.VISIBLE);
        bitmapFactoryView.setDrawingCacheEnabled(true);
        bitmapFactoryView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        bitmapFactoryView.setTextColor(textColor);
        bitmapFactoryView.setGravity(textGravity);
        bitmapFactoryView.setText(content);
        switch (textGravity) {
            case Gravity.START:
                bitmapFactoryView.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case Gravity.END:
                bitmapFactoryView.setEllipsize(TextUtils.TruncateAt.START);
                break;
        }
        bitmapFactoryView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        bitmapFactoryView.layout(0, 0, bitmapFactoryView.getMeasuredWidth(), bitmapFactoryView.getMeasuredHeight());
        bitmapFactoryView.buildDrawingCache();
        return ImageUtils.view2Bitmap(bitmapFactoryView);
    }

}
