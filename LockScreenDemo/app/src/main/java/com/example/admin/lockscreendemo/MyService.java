package com.example.admin.lockscreendemo;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;


/**
 * Created by MacaoPark on 2016/10/21.
 */

public class MyService extends Service {
    public static final String LOCK_ACTION = "lock";
    public static final String UNLOCK_ACTION = "unlock";
    private Context mContext;
    private WindowManager mWinMng;
    private LockView screenView;
    private static String TAG = "ZdLockService";
    private Intent zdLockIntent = null ;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mWinMng = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);


        i = new Intent(mContext, MyService.class);
        i.setAction(MyService.LOCK_ACTION);
        zdLockIntent = new Intent(MyService.this , MyService.class);
        zdLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		/*注册广播*/
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        MyService.this.registerReceiver(mScreenOnReceiver, mScreenOnFilter);

		/*注册广播*/
        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        MyService.this.registerReceiver(mScreenOffReceiver, mScreenOffFilter);
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null){
            System.out.println("intent------------intent.getAction()-==null?????--"+(intent==null));
            String action = intent.getAction();
            if(TextUtils.equals(action, LOCK_ACTION))
                addView();
            else if(TextUtils.equals(action, UNLOCK_ACTION))
            {
                removeView();
//				stopSelf();
            }
        }
        return Service.START_STICKY;
    }

    public void addView()
    {
        if(screenView == null)
        {
            screenView = new LockView(mContext);

            LayoutParams param = new LayoutParams();
//            param.type = LayoutParams.TYPE_SYSTEM_ALERT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
                param.type = LayoutParams.TYPE_SYSTEM_ERROR;
            }else {
                param.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            param.format = PixelFormat.RGBA_8888;
            // mParam.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            param.width = LayoutParams.MATCH_PARENT;
            param.height = LayoutParams.MATCH_PARENT;

            mWinMng.addView(screenView, param);
        }
    }

    public void removeView()
    {
        if(screenView != null)
        {
            mWinMng.removeView(screenView);
            screenView = null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MyService.this.unregisterReceiver(mScreenOnReceiver);
        MyService.this.unregisterReceiver(mScreenOffReceiver);
        //在此重新启动
        startService(new Intent(MyService.this, MyService.class));
    }

    private KeyguardManager mKeyguardManager = null ;
    private KeyguardManager.KeyguardLock mKeyguardLock = null ;
    //屏幕变亮的广播,我们要隐藏默认的锁屏界面
    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context , Intent intent) {

            Log.i(TAG, intent.getAction());

            if(intent.getAction().equals("android.intent.action.SCREEN_ON")){
                Log.i(TAG, "----------------- android.intent.action.SCREEN_ON------");
                mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
                mKeyguardLock = mKeyguardManager.newKeyguardLock("zdLock 1");
                mKeyguardLock.disableKeyguard();

                //Intent.FLAG_ACTIVITY_NEW_TASK. This flag is generally used by activities that want to present a "launcher" style behavior
                startService(i);

            }
        }

    };

    //屏幕变暗/变亮的广播 ， 我们要调用KeyguardManager类相应方法去解除屏幕锁定
    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context , Intent intent) {
            String action = intent.getAction() ;

            Log.i(TAG, intent.toString());

            if(action.equals("android.intent.action.SCREEN_OFF")
                    || action.equals("android.intent.action.SCREEN_ON") ){
                mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
                mKeyguardLock = mKeyguardManager.newKeyguardLock("zdLock 1");
                mKeyguardLock.disableKeyguard();
//				startService(zdLockIntent);

                //Intent.FLAG_ACTIVITY_NEW_TASK. This flag is generally used by activities that want to present a "launcher" style behavior
                startService(i);
            }
        }

    };
    private Intent i;
}
