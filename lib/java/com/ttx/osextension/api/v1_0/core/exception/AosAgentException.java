package com.ttx.osextension.api.v1_0.core.exception;

public class AosAgentException extends Exception {

    private final int errorCode;

    public AosAgentException(int errorCode, String message) {
        this(errorCode, message, null);
    }

    public AosAgentException(int errorCode, Throwable cause) {
        this(errorCode, null, cause);
    }

    public AosAgentException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
