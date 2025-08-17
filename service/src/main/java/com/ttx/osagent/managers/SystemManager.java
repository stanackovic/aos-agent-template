package com.ttx.osagent.managers;

import android.provider.Settings;
import android.util.Log;

import com.ttx.osagent.ISystemManager;

/**
 * This class contains implementation of System Manager functionality exposed through
 * Android OS Agent service.
 * <p>
 * Purpose of this manager is to implement features related to system.
 */
public class SystemManager extends ISystemManager.Stub {

    // Logcat log tag
    private static final String LOG_TAG = "AosAgentService-SystemManager";

    /**
     * Creates a new {@link SystemManager} object.
     *
     * @param context Service context
     */
    public SystemManager() {
        Log.i(LOG_TAG, "SystemManager created");
    }

   /**
    * Gets the device serial number.
    *
    * @param None.
    *
    * @return Device serial number.
    */
    @Override
    public String getSerialNumber() {
        String serial = Settings.Secure.ANDROID_ID;
        Log.d(LOG_TAG, "[getSerialNumber] serial=[" + serial + "]");
        return serial;
    }
}
