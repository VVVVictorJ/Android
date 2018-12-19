package com.android.victor.poem_locksrceen;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LockService extends BroadcastReceiver {

    public void onCreate(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Intent mLockIntent=new Intent(context,LockActivity.class);
            mLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mLockIntent);
        }
    }
}
