package com.ttx.osagent;

/**
 * Binder interface for system control in Android OS sevice.
 */
interface ISystemManager {
    /**
     * Gets the device serial number.
     *
     * @param None.
     *
     * @return Device serial number.
     */
    String getSerialNumber();
}
