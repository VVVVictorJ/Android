package com.example.admin.lockscreendemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;
    private MyopenHelper myopenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;// 高度
        myopenHelper=new MyopenHelper(this);
        SharedPreferences sp = getSharedPreferences("phonesize", Context.MODE_PRIVATE);
        sp.edit().putInt("width", width).commit();
        System.out.println("nt width:"+width +"int height:"+height);
        Intent i = new Intent(MainActivity.this, MyService.class);
        i.setAction(MyService.LOCK_ACTION);
        startService(i);
        finish();

    }
}
