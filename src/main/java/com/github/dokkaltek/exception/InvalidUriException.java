package com.github.dokkaltek.exception;

import lombok.Getter;

import static com.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid URI exception class.
 */
@Getter
public class InvalidUriException extends GenericException {
    /**
     * Default {@link InvalidUriException} constructor.
     * @param ex The underlying exception thrown.
     */
    public InvalidUriException(Throwable ex) {
        super("Invalid URI", ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
