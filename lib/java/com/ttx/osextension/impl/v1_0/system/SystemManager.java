package com.ttx.osextension.impl.v1_0.system;

import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import com.ttx.osextension.api.v1_0.core.exception.AosAgentException;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentServiceNotBoundException;
import com.ttx.osextension.api.v1_0.system.ISystemManager;

public class SystemManager implements ISystemManager {

    // Android log tag
    private static final String LOG_TAG = "AosAgentLib-SystemManager";

    // Aos Agent System Manager object used to perform remote system operations
    private com.ttx.osagent.ISystemManager mRemoteManager;

    /**
     * Creates a new {@link SystemManager} object.
     */
    public SystemManager() {
        Log.d(LOG_TAG, "SystemManager created");
    }

    @Override
    public void setRemoteManager(IInterface manager) {
        Log.i(LOG_TAG, "[setRemoteManager]");

        if ((manager == null) || (manager instanceof com.ttx.osagent.ISystemManager)) {
            mRemoteManager = (com.ttx.osagent.ISystemManager) manager;
        } else {
            Log.e(LOG_TAG, "[setRemoteManager] wrong remote manager provided");
        }
    }

    /**
    * Gets the device serial number.
    *
    * @param None.
    *
    * @return Device serial number.
    */
    @Override
    public String getSerialNumber() throws AosAgentException {
        String serial = "abc";
        try {
            serial = mRemoteManager.getSerialNumber();
            Log.d(LOG_TAG, "[getSerialNumber] serial:" + serial);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "[getSerialNumber] failed, RemoteException");
            throw new AosAgentServiceNotBoundException();
        }
        return serial;
    }
}
