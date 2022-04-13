package com.example.imagespandemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagespandemo.imagespan.ImageSpanAlign;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;

public class FansBadgeActivity extends AppCompatActivity {

    private TextView tvTest1;
    private ImageView imgvTest;

    private RecyclerView recyclerView;
    private List<Integer> datas = new ArrayList<>();
    private MyAdapter adapter;


    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_badge);
        tvTest1 = findViewById(R.id.tv_test1);
        imgvTest = findViewById(R.id.imgv_test);
        recyclerView = findViewById(R.id.rv_test);
        initView();
    }

    public void onTest1(View v) {
        SpannableString goodNumberImageSpan = SpanUtils.getGoodNumberImageSpan(this, "1128",  ScreenUtils.dip2px(this, 17), ImageSpanAlign.XIU_ALIGN_CENTER);
        tvTest1.setText(goodNumberImageSpan);
    }

    public void onTest2(View v) {
        imgvTest.setImageBitmap(drawTextToBitmap(this, "LV.1"));
    }

    public void initView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        for(int i = 0; i < 20; i ++) {
            datas.add(new Integer(i));
        }

        adapter = new MyAdapter(this, datas);
        recyclerView.setAdapter(adapter);
    }

    public Bitmap drawTextToBitmap(Context gContext, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_fans_level);
        int width = bitmap.getWidth();
        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(255, 255, 255));
        // text size in pixels
        paint.setTextSize((int) (ScreenUtils.dip2px(this, 12)));
        // text shadow
//        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
//	    paint.setTextAlign(Align.CENTER);

        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2 - 1;

//	    canvas.drawText(gText, x * scale, y * scale, paint);
        canvas.drawText(gText, x , y , paint);

        return bitmap;
    }

}
