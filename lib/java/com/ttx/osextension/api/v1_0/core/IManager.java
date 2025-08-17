package com.ttx.osextension.api.v1_0.core;

import android.os.IInterface;

/**
 * Indication for manager classes
 */
public interface IManager {
    /**
     * Sets remote manager.
     * <p>
     * Each manager has a remote counterpart that is used to implement the requested control.
     *
     * @param manager Remote manager object.
     */
    void setRemoteManager(IInterface manager);
}