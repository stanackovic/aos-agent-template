package com.ttx.osextension.api.v1_0;

import com.ttx.osextension.api.v1_0.core.IManager;
import com.ttx.osextension.api.v1_0.core.exception.AosAgentException;

public interface IAosExtension {

    public <T extends IManager> T getManager(Class<T> clazz) throws AosAgentException;
}
