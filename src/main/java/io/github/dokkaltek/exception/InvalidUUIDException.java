package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid UUID exception class.
 */
@Getter
public class InvalidUUIDException extends GenericException {
    /**
     * Default {@link InvalidUUIDException} constructor.
     * @param ex The underlying exception thrown.
     */
    public InvalidUUIDException(Throwable ex) {
        super("Invalid UUID", ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
