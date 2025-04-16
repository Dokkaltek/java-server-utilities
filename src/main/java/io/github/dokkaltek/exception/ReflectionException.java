package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Reflection exception class.
 */
@Getter
public class ReflectionException extends GenericException {
    private static final String DEFAULT_ERROR = "Reflection error";
    /**
     * Default {@link ReflectionException} constructor.
     * @param ex The underlying exception thrown.
     */
    public ReflectionException(Throwable ex) {
        super(DEFAULT_ERROR, ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }

    /**
     * Default {@link ReflectionException} constructor.
     * @param message The message to show.
     */
    public ReflectionException(String message) {
        super(DEFAULT_ERROR, message, INTERNAL_SERVER_ERROR.code());
    }
}
