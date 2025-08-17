package com.ttx.osextension.api.v1_0.system;

import com.ttx.osextension.api.v1_0.core.IManager;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentException;

public interface ISystemManager extends IManager {

   /**
    * Gets the device serial number.
    *
    * @param None.
    *
    * @return Device serial number.
    */
    public String getSerialNumber() throws AosAgentException;
}
