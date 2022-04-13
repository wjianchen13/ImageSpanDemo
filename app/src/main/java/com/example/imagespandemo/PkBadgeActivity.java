package com.example.imagespandemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagespandemo.bean.PkBean;
import com.example.imagespandemo.imagespan.PkLevelImageSpan;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PkBadgeActivity extends AppCompatActivity {

    private TextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;
    private ImageView imgvTest;

    private RecyclerView recyclerView;
    private List<Integer> datas = new ArrayList<>();
    private PkAdapter adapter;

    private String[] name = new String[]{"青铜", "白银", "黄金", "铂金", "钻石", "星耀", "王者"};
    private int[] grade = new int[]{1, 2, 3};
    private String[] names = new String[] {"青铜Ⅲ", "青铜Ⅱ", "青铜Ⅰ", "白银Ⅲ", "白银Ⅱ", "白银Ⅰ", "黄金Ⅲ", "黄金Ⅱ", "黄金Ⅰ", "铂金Ⅲ", "铂金Ⅱ", "铂金Ⅰ", "钻石Ⅲ", "钻石Ⅱ", "钻石Ⅰ", "星耀Ⅲ", "星耀Ⅱ", "星耀Ⅰ", "王者"};

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_badge);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        imgvTest = findViewById(R.id.imgv_test);
        recyclerView = findViewById(R.id.rv_test);
        initView();
    }

    public void onTest1(View v) {
        PkBean pkBean1 = new PkBean("钻石", 1);
        tvTest1.setText(SpanUtils.getPkImageSpan1(this,  pkBean1.name, pkBean1.segment, PkLevelImageSpan.MODE_NORMAL));
        PkBean pkBean2 = new PkBean("钻石", 1);
        tvTest2.setText(SpanUtils.getPkImageSpan1(this,  pkBean2.name, pkBean2.segment, PkLevelImageSpan.MODE_NORMAL));
        PkBean pkBean3 = new PkBean("钻石", 1);
        tvTest3.setText(SpanUtils.getPkImageSpan1(this,  pkBean3.name, pkBean3.segment, PkLevelImageSpan.MODE_NORMAL));
    }

    public void onTest2(View v) {
        Bitmap bitmap = drawTextToBitmap(this, "1你好");
        imgvTest.setImageBitmap(bitmap);
        Drawable drawable = new BitmapDrawable(getResources(), drawTextToBitmap(this, "1你好"));
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getMinimumHeight();
        int b;

    }

    public void onTest3(View v) {

        saveBitmap(drawTextToBitmap(this, "1你好"), "/mnt/sdcard/test/hello.png");
    }

    public void initView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<PkBean> pkBeans = new ArrayList<>();
        for(int i = 1; i <= names.length; i ++) {
            PkBean bean = new PkBean(names[i - 1], i);
            pkBeans.add(bean);
        }
        adapter = new PkAdapter(this, pkBeans);
        recyclerView.setAdapter(adapter);
    }

    /**
     *
     */
    private String getGrade(int grade) {
        String str = "";
        if(grade == 1) {
            str = "Ⅰ";
        } else if(grade == 2) {
            str = "Ⅱ";
        } else if(grade == 3) {
            str = "Ⅲ";
        }
        return str;
    }

    public Bitmap drawTextToBitmap(Context mContext, String gText) {
//        Bitmap crossImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_rank_pk_diamonds);

        int size = ScreenUtils.dip2px(mContext, 20);
        Drawable img = ContextCompat.getDrawable(mContext, R.drawable.ic_rank_pk_diamonds);
        int iHeight = img.getIntrinsicHeight(); // 获取图片的高度 px
        int iWidth = img.getIntrinsicWidth();
        float factor;

        Bitmap target = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas temp_canvas = new Canvas(target);
        if(iHeight > iWidth) {
            factor = (float)size / iHeight;
            int left = (size - (int) (iWidth * factor)) / 2;
            img.setBounds(left, 0, (int) (iWidth * factor) + left, size);
        } else {
            factor = (float)size / iWidth;
            int top = (size - (int) (iHeight * factor)) / 2;
            img.setBounds(0, top, size, (int) (iHeight * factor) + top);
        }
        img.draw(temp_canvas);
//        temp_canvas.drawBitmap(crossImage, null, new Rect(0, 0, target.getWidth(), target.getHeight()), null);
        return target;
    }


    /**
     * 保存bitmap
     *
     * @param bm
     * @param path
     */
    public void saveBitmap(Bitmap bm, String path) {
        if (bm == null || TextUtils.isEmpty(path)) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path));
            bm.compress(Bitmap.CompressFormat.PNG, 83, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
