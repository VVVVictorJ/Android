package com.android.victor.aiwater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=(Button)findViewById(R.id.bbbutton);
        Button button2=(Button)findViewById(R.id.bbbutton1);
        button1.setOnClickListener(new Button1Listener());
        //button2.setOnClickListener(new Button2Listener());
    }
    //define a button listener
        class Button1Listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,Control.class);
            startActivity(intent);
        }
    }
}
