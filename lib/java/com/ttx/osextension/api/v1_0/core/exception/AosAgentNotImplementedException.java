package com.ttx.osextension.api.v1_0.core.exception;

/**
 * Thrown for methods where the implementation is done.
 */
public class AosAgentNotImplementedException extends AosAgentException {
    // Exception code marking that binder connection to remote service is not established
    public static final int CODE = 404;
    // Exception description marking that binder connection to remote service is not established
    public static final String DESC = "Stub not implemented";

    public AosAgentNotImplementedException() {
        super(CODE, DESC);
    }
}
