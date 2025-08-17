package com.ttx.osagent.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ttx.osagent.AosAgentService;

/**
 * This class contains implementation of a custom BroadcastReceiver which is used to listen
 * to intents (events) broadcasted in Android System.
 * <p>
 * At the moment it only listens to Intent.ACTION_BOOT_COMPLETED intent which will be triggered when
 * Android is started up.
 */
public class BootUpReceiver extends BroadcastReceiver {

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast that we subscribed to through AndroidManifest.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            // This should never happen, but add this just in case
            return;
        }
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, AosAgentService.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(i);
        }
    }
}
