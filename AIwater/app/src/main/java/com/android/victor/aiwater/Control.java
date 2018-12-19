package com.android.victor.aiwater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Control extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.contorl);
        Button button1=(Button)findViewById(R.id.certt);
        Button button2=(Button)findViewById(R.id.rrturn);

    }
    class Button2Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Control.this,Main_table.class);
            startActivity(intent);
        }
    }

    class Button3Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Control.this,Main_table.class);
            startActivity(intent);
        }
    }
}
