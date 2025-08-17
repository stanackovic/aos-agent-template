package com.ttx.osextension.api.v1_0.core.exception;

/**
 * Thrown for methods where the implementation is done.
 */
public class AosAgentUnsupportedManagerException extends AosAgentException {
    // Exception code marking that binder connection to remote service is not established
    public static final int CODE = 2000;
    // Exception description marking that binder connection to remote service is not established
    public static final String DESC = "No implementation found for the provided class";

    public AosAgentUnsupportedManagerException() {
        super(CODE, DESC);
    }
}
