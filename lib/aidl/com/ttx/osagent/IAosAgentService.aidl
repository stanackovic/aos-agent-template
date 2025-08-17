package com.ttx.osagent;

import com.ttx.osagent.ISystemManager;

/**
 * Android OS Agent sevice Binder interface.
 * Allows clients to acquire manager objects used for:
 *    - System status and control
 */
interface IAosAgentService {
    /**
     * Gets system manager.
     *
     * @return ISystemManager object used to perform system related controls.
     */
    ISystemManager getSystemManager();
}
