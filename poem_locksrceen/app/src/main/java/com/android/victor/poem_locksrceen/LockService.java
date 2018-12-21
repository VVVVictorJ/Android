package com.android.victor.poem_locksrceen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;


public class LockService extends Service {

    private ScreenOffReceiver screenOffReceiver;
    private ScreenOnReceiver screenOnReceiver;
//constructive function
    public LockService() {
    }
//不知道是什么
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter screenOnFilter = new IntentFilter();                               //filter
        screenOnFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenOnReceiver, screenOnFilter);
        screenOffReceiver = new ScreenOffReceiver();
        IntentFilter screenOffFilter = new IntentFilter();
        screenOffFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(screenOffReceiver, screenOffFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    private class ScreenOnReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {
                Log.d("--------", "SCREEN_ON");
                Intent intent1 = new Intent(context, LockActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        }
    }

    //    @Override
//    public void onReceive(Context context, Intent intent) {
//        String action=intent.getAction();
//        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
//            Intent mLockIntent=new Intent(context,LockActivity.class);
//            mLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mLockIntent);
//        }
//    }
    private class ScreenOffReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                Log.d("--------", "SCREEN_OFF");
                Intent intent1 = new Intent(context, LockActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        }
    }
}
