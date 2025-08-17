package com.ttx.osextension;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import java.util.Map;
import java.util.HashMap;

import com.ttx.osagent.IAosAgentService;

import com.ttx.osextension.api.v1_0.IAosExtension;
import com.ttx.osextension.api.v1_0.core.IManager;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentException;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentServiceNotBoundException;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentUnsupportedManagerException;
import com.ttx.osextension.api.v1_0.system.ISystemManager;

import com.ttx.osextension.impl.v1_0.system.SystemManager;

public class AosAgentManager implements IAosExtension, ServiceConnection {

    // Android log tag
    private static final String LOG_TAG = "AosAgentManager";

    // SystemAgent service identifiers
    private static final String SERVICE_PACKAGE = "com.ttx.systemagent";
    private static final String SERVICE_CLASS = "com.ttx.systemagent.SystemAgentService";

    // Application context
    private final Context mApplicationContext;

    // Android OS agent service handler object
    private IAosAgentService mService;

    // Managers
    private final Map<Class<? extends IManager>, IManager> mManagers;

    // AosAgentManager
    public AosAgentManager(Context context) {
        mApplicationContext = context;
        Intent intent = new Intent();
        intent.setClassName(SERVICE_PACKAGE, SERVICE_CLASS);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);

        // Start service if it is not running
        // If service is already running it will ignore this command
        context.startService(intent);

        mManagers = new HashMap<>();

        mManagers.put(ISystemManager.class, new SystemManager());
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder boundService ) {
        Log.d(LOG_TAG, "[onServiceConnected] connected to AosAgent");

        mService = IAosAgentService.Stub.asInterface((IBinder)boundService);
        setupRemoteManagers();
    }

    @Override
    public void onServiceDisconnected(ComponentName className)
    {
        Log.d(LOG_TAG, "[onServiceDisconnected] disconnected from AosAgent");

        mService = null;
        setupRemoteManagers();
    }

    @Override
    public <T extends IManager> T getManager(Class<T> clazz) throws AosAgentException {
        IManager manager = mManagers.get(clazz);
        if (manager != null) {
            return (T) manager;
        } else {
            // throw new AosAgentServiceNotBoundException()
            throw new AosAgentUnsupportedManagerException();
        }
    }

    /**
     * Setup remote manager counterparts for local managers.
     */
    private void setupRemoteManagers() {
        for (Class key : mManagers.keySet()) {
            IManager manager = mManagers.get(key);
            if (manager != null) {
                IInterface remoteManager = getRemoteManager(key);
                manager.setRemoteManager(remoteManager);
            }
        }
    }

    /**
     * Gets remote manager object.
     *
     * @param managerClass Class object used to distinguish target manager.
     *
     * @return Manager object if found, null otherwise.
     */
    private IInterface getRemoteManager(Class managerClass) {
        if (mService == null) {
            return null;
        }
        try {
            if (managerClass == ISystemManager.class) {
                return (IInterface)mService.getSystemManager();
            }
            // Add new managers here
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "[getRemoteManager] remote service not available");
        }
        return null;
    }
}
