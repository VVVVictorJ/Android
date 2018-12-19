package com.android.victor.poem_locksrceen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LockActivity extends AppCompatActivity {

    private DevicePolicyManager policyManager;
    private ComponentName   componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
