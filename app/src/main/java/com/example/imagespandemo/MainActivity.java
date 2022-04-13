package com.example.imagespandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest1(View v) {
        Intent it = new Intent();
        it.setClass(this, FansBadgeActivity.class);
        startActivity(it);
    }

    public void onTest2(View v) {
        Intent it = new Intent();
        it.setClass(this, FansBadgeActivity1.class);
        startActivity(it);
    }

    public void onTest3(View v) {
        Intent it = new Intent();
        it.setClass(this, PkBadgeActivity.class);
        startActivity(it);
    }
}
