package com.android.victor.poem_locksrceen;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class LockActivity extends AppCompatActivity {
//    private static final String TAG ="2" ;
//    public Context mContext;
//    public WindowManager mWinMng;
//    public Intent i;
//    public Intent zdLockIntent;
    private View lockView;
    private LockUtil lockLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = getApplicationContext();
////        mWinMng = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        i=new Intent(mContext,LockService.class);
//        i.setAction(LockService)
        Log.d("---------", "LockActivity");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        lockView = View.inflate(this, R.layout.lock_show, null);
        lockLayer = new LockUtil(this);
        lockView.findViewById(R.id.unlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockLayer.unlock();
            }
        });
//        zdLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
//
//        LockActivity.this.registerReceiver(mScreenOnReceiver,mScreenOnFilter);
//        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
//        LockActivity.this.registerReceiver(mScreenOnReceiver,mScreenOnFilter);
//        IntentFilter mScreenOffFilter = new IntentFilter();
//        mScreenOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(mScreenOffReceiver, mScreenOffFilter);
//        startService(this,LockService.this);
    }
//    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver(){
//
//        @Override
//        public void onReceive(Context context , Intent intent) {
//
//            Log.i(TAG, intent.getAction());
//
//            if(intent.getAction().equals("android.intent.action.SCREEN_ON")){
//                startService(i);
//            }
//        }
//
//    };
    private BroadcastReceiver mScreenOnReceiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Intent mLockIntent=new Intent(context,LockActivity.class);
            mLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mLockIntent);
        }
    }
};

}
