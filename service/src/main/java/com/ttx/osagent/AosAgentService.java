package com.ttx.osagent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.util.Map;

import com.ttx.osagent.IAosAgentService;
import com.ttx.osagent.ISystemManager;

import com.ttx.osagent.managers.SystemManager;

/**
 * This class contains implementation of Android OS Agent as Android service.
 * <p>
 * Purpose of this service is to provide interface for functionality that requires
 * system privilege to be executed on Android system.
 */
public class AosAgentService extends Service {

    // Logcat log tag
    private static final String LOG_TAG = "AosAgentService";

    // Service started state
    private boolean isStarted;

    // Foreground service ID
    private static final int FOREGROUND_ID = 13005;

    // Foreground service channel ID
    private static final String FOREGROUND_CH_ID = "aos_agent_channel_id";

    // Managers
    private SystemManager systemManager;

    /**
     * Creates a new {@link AosAgentService}.
     */
    public AosAgentService() {
        Log.i(LOG_TAG, "Service created");
    }

    /**
     * Called from Service lifecycle handler to notify that service has been created.
     */
    @Override
    public void onCreate() {
        Log.i(LOG_TAG, "Service onCreate()");
        super.onCreate();

        systemManager = new SystemManager();
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * {@link android.content.Context#startService}, providing the arguments it supplied and a
     * unique integer token representing the start request. Do not call this method directly.
     *
     * @param intent The Intent supplied to {@link android.content.Context#startService},
     * as given.  This may be null if the service is being restarted after
     * its process has gone away, and it had previously returned anything
     * except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to
     * start.  Use with {@link #stopSelfResult(int)}.
     *
     * @return The return value indicates what semantics the system should
     * use for the service's current started state.  It may be one of the
     * constants associated with the {@link #START_CONTINUATION_MASK} bits.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // This service needs to run all the time as a foreground service in the system
        // We are not handling stopping because of this
        if (!isStarted) {
            startForegroundService();
            isStarted = true;
        }

        // Restart the service when it crashes or is force stopped
        return START_STICKY;
    }

    /**
     * Called from Service lifecycle handler to notify that service is being destroyed.
     */
    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "Service onDestroy()");
        super.onDestroy();
    }

    /**
     * Used to build and start foreground service.
     */
    private void startForegroundService()
    {
        Log.d(LOG_TAG, "Start foreground service.");

        NotificationChannel channel = new NotificationChannel(FOREGROUND_CH_ID,
            getText(R.string.app_name).toString(), NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(getText(R.string.app_name).toString());

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Notification notification =
            new Notification.Builder(this)
            .setPriority(Notification.PRIORITY_MAX)
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setChannelId(FOREGROUND_CH_ID)
            .build();

        // Start foreground service.
        startForeground(FOREGROUND_ID, notification);
    }

    /**
     * Return the communication channel to the service. May return null if
     * clients can not bind to the service.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link android.content.Context#bindService
     *               Context.bindService}.
     *
     * @return Return an IBinder through which clients can call on to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Implements IAosAgentService aidl interface.
     */
    private final IAosAgentService.Stub mBinder = new IAosAgentService.Stub() {
        /**
         * Gets Android OS Agent System Manager object.
         *
         * @return ISystemManager object used to perform system related controls
         *         in the remote service.
         */
        @Override
        public ISystemManager getSystemManager() {
            Log.i(LOG_TAG, "[getSystemManager] called from remote connection.");
            return systemManager;
        }
    };
}
