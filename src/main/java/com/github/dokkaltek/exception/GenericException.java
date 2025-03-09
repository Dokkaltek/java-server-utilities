package com.github.dokkaltek.exception;

import lombok.Getter;

/**
 * General use exception class.
 */
@Getter
public class GenericException extends RuntimeException {
    /**
     * The error summary or error code.
     */
    private final String error;

    /**
     * The detailed error message.
     */
    private final String message;

    /**
     * The HTTP status code of the error.
     */
    private final int status;

    /**
     * Default {@link GenericException} constructor.
     * @param error The error summary or error code.
     * @param message The detailed error message.
     * @param status The HTTP status code of the error.
     */
    public GenericException(String error, String message, int status) {
        super(message);
        this.error = error;
        this.message = message;
        this.status = status;
    }

    /**
     * Default {@link GenericException} constructor.
     * @param error The error summary or error code.
     * @param ex The exception.
     * @param status The HTTP status code of the error.
     */
    public GenericException(String error, Throwable ex, int status) {
        super(ex);
        this.error = error;
        this.message = ex.getMessage();
        this.status = status;
    }
}
