package com.example.admin.lockscreendemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MacaoPark on 2016/10/22.
 */

public class BootCompletedReciever extends BroadcastReceiver {
    private final String action = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action)){
            Intent i = new Intent(context, MyService.class);
            i.setAction(MyService.LOCK_ACTION);
            context.startService(i);
        }
    }
}
